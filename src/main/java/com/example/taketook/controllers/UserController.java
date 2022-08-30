package com.example.taketook.controllers;

import com.example.taketook.entity.Comment;
import com.example.taketook.entity.Role;
import com.example.taketook.entity.User;
import com.example.taketook.payload.request.UserController.*;
import com.example.taketook.payload.response.BytehandResponse;
import com.example.taketook.payload.response.JwtResponse;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.CommentRepository;
import com.example.taketook.repository.RoleRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.FileStorageService;
import com.example.taketook.service.RestService;
import com.example.taketook.service.UserDetailsImpl;
import com.example.taketook.utils.Constants;
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.RoleEnum;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.taketook.utils.Constants.DEFAULT_RATING;
import static com.example.taketook.utils.Support.getExtensionFromFile;


@RestController
@RequestMapping("/user")
public class UserController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;
    private final CommentRepository commentRepository;
    private final RestService restService;

    public UserController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, FileStorageService fileStorageService, CommentRepository commentRepository, RestService restService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fileStorageService = fileStorageService;
        this.commentRepository = commentRepository;
        this.restService = restService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestPart SignUpRequest signUpRequest, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPhone(), signUpRequest.getAddress(), signUpRequest.getCity(), passwordEncoder.encode(signUpRequest.getPassword()), null, DEFAULT_RATING, new HashSet<>(), new ArrayList<>(), null, null);
        Set<Role> roles = new HashSet<>();
        Role basicRole = roleRepository.findByRole(RoleEnum.BASIC_USER).orElseThrow(RuntimeException::new);
        roles.add(basicRole);
        user.setRoles(roles);
        User result = userRepository.save(user);
        if (file != null) {
            String fileName = result.getId() + "." + getExtensionFromFile(file);
            String avaUrl = uploadFile(Constants.USER_IMAGE_FOLDER, file, fileName);
            result.setAvaUrl(avaUrl);
            result = userRepository.save(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getPhone(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> strRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Set<Role> roles = new HashSet<>();
        for (String strRole : strRoles) {
            Role role = roleRepository.findByRole(RoleEnum.valueOf(strRole)).orElseThrow(RuntimeException::new);
            roles.add(role);
        }
        User user = new User(userDetails.getName(), userDetails.getSurname(), userDetails.getEmail(), userDetails.getPhone(), userDetails.getAddress(), userDetails.getCity(), userDetails.getPassword(), userDetails.getAvaUrl(), userDetails.getRating(), userDetails.getUserRatings(), userDetails.getCommentIds(), null, null);
        user.setId(userDetails.getId());
        user.setRoles(roles);

        String jwt = jwtUtils.generateJwtToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, user));
    }

    @PostMapping("/loginPhone")
    public ResponseEntity<?> singInWithPhone(@RequestBody SignInWithPhoneRequest signInWithPhoneRequest) {
        User user = userRepository.findByPhone(signInWithPhoneRequest.getPhone()).orElseThrow(RuntimeException::new);
        String generatedCode = generateCode();
        sendSms(generatedCode, signInWithPhoneRequest.getPhone());

        user.setVerifyCode(generatedCode);
        user.setVerifyExpireDate(System.currentTimeMillis() + 1000 * 60 * 5);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Code was sent"));
    }

    @PostMapping("/checkCode")
    public ResponseEntity<?> checkPhoneCode(@RequestBody CheckPhoneCodeRequest checkPhoneCodeRequest) {
        User user = userRepository.findByPhone(checkPhoneCodeRequest.getPhone()).orElseThrow(RuntimeException::new);

        if (Objects.equals(user.getVerifyCode(), checkPhoneCodeRequest.getCode()) && user.getVerifyExpireDate() >= System.currentTimeMillis()) {
            user.setVerifyCode(null);
            user.setVerifyExpireDate(null);

            userRepository.save(user);

            String jwt = jwtUtils.generateJwtToken(user.getId());
            return ResponseEntity.ok(new JwtResponse(jwt, user));
        }

        return ResponseEntity.ok(new MessageResponse("Code is incorrect or expired"));
    }

    @PostMapping("/comment")
    public ResponseEntity<?> commentUser(@RequestHeader("jwt") String jwt, @RequestBody CreateCommentOnUserRequest createCommentRequest) {
        try {
            String id = jwtUtils.getIdFromToken(jwt);

            if (!userRepository.existsById(id)) {
                return ResponseEntity.badRequest().body(new MessageResponse("User doesnt exist"));
            }

            User user = userRepository.findById(createCommentRequest.getUserToRateId()).orElseThrow(RuntimeException::new);

            Comment comment = new Comment(createCommentRequest.getText(), id, System.currentTimeMillis());
            String newCommentId = commentRepository.save(comment).getId();
            List<String> commentIds = user.getCommentIds();

            commentIds.add(newCommentId);
            user.setCommentIds(commentIds);

            if (!user.rate(createCommentRequest.getRating(), id)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Rating is incorrect or user has already left rating"));
            }
            user.setRating(createCommentRequest.getRating());
            return ResponseEntity.ok(userRepository.save(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // just rate, without comment
    @PostMapping("/rate")
    public ResponseEntity<?> justRateUser(@RequestHeader("jwt") String jwt, @RequestBody RateUserRequest rateUserRequest) {
        try {
            String id = jwtUtils.getIdFromToken(jwt);

            if (!userRepository.existsById(id)) {
                return ResponseEntity.badRequest().body(new MessageResponse("User with this id doesnt exist"));
            }

            User user = userRepository.findById(rateUserRequest.getUserToRateId()).orElseThrow(RuntimeException::new);

            if (!user.rate(rateUserRequest.getRating(), id)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Rating is incorrect or user has already left rating"));
            }
            return ResponseEntity.ok(userRepository.save(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    public String uploadFile(Path path, MultipartFile file, String fileName) {
        try {
            fileStorageService.save(file, path, fileName);
            return Constants.SITE_URI + Constants.GET_FILE_SUB_URL + "users/" + fileName;
        } catch (Exception e) {
            return null;
        }
    }

    private String generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    private ResponseEntity<?> sendSms(String generatedCode, String phone) {
        String url = "https://api.bytehand.com/v2/sms/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Service-Key", "");

        Map<String, Object> map = new HashMap<>();
        map.put("sender", "Sell Cell");
        map.put("receiver", phone);
        map.put("text", "\nВаш код доступа в приложении: " + generatedCode + ".\nДействителен в течение 5 минут");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<BytehandResponse> response = restService.restTemplate.postForEntity(url, entity, BytehandResponse.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.ok(new MessageResponse(response.getStatusCode().name()));
        }
    }
}


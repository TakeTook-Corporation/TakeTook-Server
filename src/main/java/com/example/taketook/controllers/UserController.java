package com.example.taketook.controllers;

import com.example.taketook.entity.Role;
import com.example.taketook.entity.User;
import com.example.taketook.payload.request.SignInRequest;
import com.example.taketook.payload.request.SignUpRequest;
import com.example.taketook.payload.response.JwtResponse;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.RoleRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.UserDetailsImpl;
import com.example.taketook.service.storage.StorageService;
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.RoleEnum;
import com.example.taketook.utils.Support;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.taketook.utils.ErrorMessages.*;
import static com.example.taketook.utils.Support.uploadAvatar;


@RestController
@RequestMapping("/user")
public class UserController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StorageService storageService;

    public UserController(JwtUtils jwtUtils, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, UserRepository userRepository,
                          RoleRepository roleRepository, StorageService storageService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.storageService = storageService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestPart("user_data") SignUpRequest signUpRequest,
                                        @RequestParam(required = false) MultipartFile image) {
        // Check if email is not taken
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(EMAIL_IN_USE));
        }

        // Create user with given credentials
        User user = new User(
                             signUpRequest.getName(), signUpRequest.getSurname(),
                             signUpRequest.getEmail(), signUpRequest.getPhone(),
                             signUpRequest.getAddress(), signUpRequest.getCity(),
                             passwordEncoder.encode(signUpRequest.getPassword()),
                             BASE_IMAGE_LINK
                            );

        // Setting roles
        Set<Role> roles = new HashSet<>();
        Role basicRole = roleRepository.findByRole(RoleEnum.BASIC_USER).orElseThrow(RuntimeException::new);
        roles.add(basicRole);
        user.setRoles(roles);

        userRepository.save(user);

        if (image != null) {
            user.setAvatarUrl(uploadAvatar(image, user.getId(), storageService, Support.ImageType.USER));
        }

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse(SUCCESS));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                                                        signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Getting user data
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> strRoles = userDetails.getAuthorities()
                                           .stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.toList());
        // Getting user roles
        Set<Role> roles = new HashSet<>();
        for (String strRole : strRoles) {
            // Searching role in database
            Role role = roleRepository.findByRole(RoleEnum.valueOf(strRole)).orElseThrow(RuntimeException::new);
            roles.add(role);
        }

        User user = new User(userDetails.getName(), userDetails.getSurname(),
                             userDetails.getEmail(), userDetails.getPhone(),
                             userDetails.getAddress(), userDetails.getCity(),
                             userDetails.getPassword(), userDetails.getAvatarUrl());
        user.setId(userDetails.getId());
        user.setRoles(roles);

        String token = jwtUtils.generateJwtToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token, user));
    }
}


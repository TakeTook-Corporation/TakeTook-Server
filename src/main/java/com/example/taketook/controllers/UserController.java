package com.example.taketook.controllers;

import com.example.taketook.config.AuthTokenFilter;
import com.example.taketook.entity.Role;
import com.example.taketook.entity.User;
import com.example.taketook.payload.request.SignInRequest;
import com.example.taketook.payload.request.SignUpRequest;
import com.example.taketook.payload.response.JwtResponse;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.RoleRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.FileStorageService;
import com.example.taketook.service.UserDetailsImpl;
import com.example.taketook.utils.Constants;
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("/user")
public class UserController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;

    public UserController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, FileStorageService fileStorageService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestPart SignUpRequest signUpRequest, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPhone(), signUpRequest.getAddress(), signUpRequest.getCity(), passwordEncoder.encode(signUpRequest.getPassword()), null);
        Set<Role> roles = new HashSet<>();
        Role basicRole = roleRepository.findByRole(RoleEnum.BASIC_USER).orElseThrow(RuntimeException::new);
        roles.add(basicRole);
        user.setRoles(roles);
        User result = userRepository.save(user);
        if (file != null) {
            String fileName = result.getId() + "." + getExtensionFromFile(file);
            String avaUrl = uploadFile(Constants.USER_IMAGE_FOLDER, new MultipartFile[]{file}, fileName);
            result.setAvaUrl(avaUrl);
            result = userRepository.save(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> strRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Set<Role> roles = new HashSet<>();
        for(String strRole : strRoles) {
            Role role = roleRepository.findByRole(RoleEnum.valueOf(strRole)).orElseThrow(RuntimeException::new);
            roles.add(role);
        }
        User user = new User(userDetails.getName(), userDetails.getSurname(), userDetails.getEmail(), userDetails.getPhone(), userDetails.getAddress(), userDetails.getCity(), userDetails.getPassword(), userDetails.getAvaUrl());
        user.setId(userDetails.getId());
        user.setRoles(roles);
        return ResponseEntity.ok(new JwtResponse(jwt, user));
    }

    private String getExtensionFromFile(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    }

    public String uploadFile(Path path, MultipartFile[] files, String fileName) {
        try {
            Arrays.stream(files).forEach(file -> {
                fileStorageService.save(file, path, fileName);
            });
            return Constants.SITE_URI + Constants.GET_FILE_SUB_URL + "users/" + fileName;
        } catch (Exception e) {
            return null;
        }
    }
}


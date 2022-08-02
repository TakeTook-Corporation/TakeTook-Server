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
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPhone(), signUpRequest.getAddress(), signUpRequest.getCity(), passwordEncoder.encode(signUpRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role basicRole = roleRepository.findByRole(RoleEnum.BASIC_USER).orElseThrow(RuntimeException::new);
        roles.add(basicRole);
        user.setRoles(roles);
        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = new User(userDetails.getName(), userDetails.getSurname(), userDetails.getEmail(), userDetails.getPhone(), userDetails.getAddress(), userDetails.getCity(), userDetails.getPassword());
        user.setId(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, user));
    }
}


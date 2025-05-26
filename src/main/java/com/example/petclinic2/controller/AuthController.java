package com.example.petclinic2.controller;

import com.example.petclinic2.model.Role;
import com.example.petclinic2.model.User;
import com.example.petclinic2.repository.RoleRepository;
import com.example.petclinic2.repository.UserRepository;
import com.example.petclinic2.security.AuthRequest;
import com.example.petclinic2.security.JwtAuthFilter;
import com.example.petclinic2.security.JwtTokenProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
@Getter
@Setter
@RestController
@RequestMapping("/auth")
public class  AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthFilter jwtAuthFilter;
    private final RoleRepository roleRepository;

    public AuthController(JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtAuthFilter jwtAuthFilter,
                            RoleRepository roleRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtAuthFilter = jwtAuthFilter;
        this.roleRepository = roleRepository;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){

        if(userRepository.existsByLogin(request.getLogin())){
            return ResponseEntity.badRequest().body("Username is already taken");
        }


        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(()-> new RuntimeException("ROLE_USER not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);


        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate (@RequestBody  AuthRequest request){
        System.out.println(">>> LOGIN ATTEMPT:");
        System.out.println(">>> AuthController /auth/login called for:  " + request.getLogin());
        System.out.println("Password: " + request.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(Map.of("token", token));

    }


}
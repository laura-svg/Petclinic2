package com.example.petclinic2.service;

import com.example.petclinic2.model.Role;
import com.example.petclinic2.model.User;
import com.example.petclinic2.repository.RoleRepository;
import com.example.petclinic2.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean usernameExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public void registerUser(String login, String password) {
        if (usernameExists(login)) {
            logger.warn("Спроба зареєструвати існуючий логін: {}", login);
            throw new IllegalArgumentException("Username already exists");
        }


        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        Role roleUser = roleRepository.findByRole("ROLE_USER")
                        .orElseThrow(()-> new RuntimeException("ROLE_USER not found"));
        user.setRoles(Set.of(roleUser));
        userRepository.save(user);
    }
}

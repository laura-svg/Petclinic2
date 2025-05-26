package com.example.petclinic2;
import com.example.petclinic2.model.Role;
import com.example.petclinic2.model.User;
import com.example.petclinic2.repository.RoleRepository;
import com.example.petclinic2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {

        Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));
        Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));

        User user = new User();
        user.setLogin("PetClinikOwner");
        user.setEmail("petclinikowner@gmail.com");
        user.setPassword(passwordEncoder.encode("ownerpassword"));
        user.setEnabled(true);
        user.setRoles(Set.of(userRole));

        User admin = new User();
        admin.setLogin("Lora");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole, userRole));

        userRepository.save(user);
        userRepository.save(admin);
    }
}

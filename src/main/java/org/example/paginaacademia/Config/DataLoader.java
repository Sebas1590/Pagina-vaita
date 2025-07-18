package org.example.paginaacademia.Config;

import jakarta.annotation.PostConstruct;
import org.example.paginaacademia.Entity.Role;
import org.example.paginaacademia.Entity.User;
import org.example.paginaacademia.Repository.RoleRepository;
import org.example.paginaacademia.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadData() {
        // Verificar y crear rol ADMIN si no existe
        Role adminRole = roleRepository.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Role role = Role.builder().nombre("ADMIN").build();
                    return roleRepository.save(role);
                });

        // Verificar y crear usuario admin si no existe
        userRepository.findByEmail("admin@academia.com").orElseGet(() -> {
            User adminUser = User.builder()
                    .email("admin@academia.com")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(adminRole)
                    .build();
            return userRepository.save(adminUser);
        });
    }
}

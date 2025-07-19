package org.example.paginaacademia.config;

import org.example.paginaacademia.entity.User;
import org.example.paginaacademia.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(IUserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(new BCryptPasswordEncoder().encode("1234"))
                        .roles(Set.of("ADMIN"))
                        .build();
                userRepository.save(user);
                System.out.println("🟢 Usuario 'admin' creado");
            }
        };
    }
}

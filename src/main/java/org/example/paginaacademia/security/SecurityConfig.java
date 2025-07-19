package org.example.paginaacademia.security;

import lombok.RequiredArgsConstructor;
import org.example.paginaacademia.filter.JwtFilter;
import org.example.paginaacademia.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/","/logout", "/login","/register", "/css/**", "/style.css").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login -> login.disable())
                .addFilterBefore(jwtFilter,   UsernamePasswordAuthenticationFilter.class)
                .logout(logout-> logout.disable())
                .exceptionHandling(ex->ex
                        .accessDeniedPage("/access-denied")
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return authentication -> {
            UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
            if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
                throw new BadCredentialsException("Credenciales inválidas");
            }
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

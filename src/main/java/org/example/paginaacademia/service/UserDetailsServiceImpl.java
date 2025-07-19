package org.example.paginaacademia.service;

import org.example.paginaacademia.entity.User;
import org.example.paginaacademia.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository repo;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User usuario = repo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles().stream()
                        .map(role-> new SimpleGrantedAuthority("ROLE_" +role))
                        .collect(Collectors.toSet())
        );

    }
}


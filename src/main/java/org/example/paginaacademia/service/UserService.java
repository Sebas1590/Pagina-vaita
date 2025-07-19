package org.example.paginaacademia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paginaacademia.dto.UserDTO;
import org.example.paginaacademia.entity.User;
import org.example.paginaacademia.repository.IUserRepository;
import org.example.paginaacademia.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements IUserService {

    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void saveUser(UserDTO userDTO){
        log.info("datos recibidos: {} ", userDTO.getUsername());
        log.info("password: {}" , userDTO.getPassword());
        if(userDTO == null)  new RuntimeException("usuario no puede ser nulo");

        User userEntity = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(userDTO.getRoles())
                .build();


        iUserRepository.save(userEntity);
        log.info("usuario guardado");
    }
}

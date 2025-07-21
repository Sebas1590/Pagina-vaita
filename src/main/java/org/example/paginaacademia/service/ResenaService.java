package org.example.paginaacademia.service;

import org.example.paginaacademia.repository.IResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resenaService")
public class ResenaService {

    @Autowired
    private IResenaRepository resenaRepository;

    public boolean esAutor(Long id, String username) {
        return resenaRepository.findById(id)
                .map(resena -> resena.getUsuario().getUsername().equals(username))
                .orElse(false);
    }
}


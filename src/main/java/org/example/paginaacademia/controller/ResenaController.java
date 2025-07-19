package org.example.paginaacademia.controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.entity.Resena;
import org.example.paginaacademia.entity.User;
import org.example.paginaacademia.repository.IResenaRepository;
import org.example.paginaacademia.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    private IUserRepository userRepo;

    private final IResenaRepository IResenaRepository;

    public ResenaController(IResenaRepository resenaRepo) {
        this.IResenaRepository = resenaRepo;
    }

    @GetMapping
    public String mostrarResenas(Model model) {
        model.addAttribute("resenas", IResenaRepository.findAll());
        model.addAttribute("resena", new Resena());
        return "resenas";
    }

    @PostMapping
    public String guardarResena(@Valid @ModelAttribute Resena resena, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("resenas", IResenaRepository.findAll());
            return "resenas";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        resena.setUsuario(user);

        IResenaRepository.save(resena);
        return "redirect:/resenas";
    }

    @PreAuthorize("hasRole('ADMIN') or @resenaService.esAutor(#id, authentication.name)")
    @GetMapping("/eliminar/{id}")
    public String eliminarResena(@PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        Resena resena = IResenaRepository.findById(id).orElse(null);
        if (resena != null && (esAdmin || resena.getUsuario().getUsername().equals(username))) {
            IResenaRepository.delete(resena);
        }
        return "redirect:/resenas";
    }
}


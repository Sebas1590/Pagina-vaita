package org.example.paginaacademia.Controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.Entity.Resena;
import org.example.paginaacademia.Repository.ResenaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resenas")
public class ResenaController {

    private final ResenaRepository resenaRepository;

    public ResenaController(ResenaRepository resenaRepo) {
        this.resenaRepository = resenaRepo;
    }

    @GetMapping
    public String mostrarResenas(Model model) {
        model.addAttribute("resenas", resenaRepository.findAll());
        model.addAttribute("resena", new Resena());
        return "resenas";
    }

    @PostMapping
    public String guardarResena(@Valid @ModelAttribute Resena resena, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("resenas", resenaRepository.findAll());
            return "resenas";
        }
        resenaRepository.save(resena);
        return "redirect:/resenas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarResena(@PathVariable Long id) {
        resenaRepository.deleteById(id);
        return "redirect:/resenas";
    }
}


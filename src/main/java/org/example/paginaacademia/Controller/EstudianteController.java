package org.example.paginaacademia.Controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.Model.Estudiante;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final List<Estudiante> listaEstudiantes = new ArrayList<>();

    @GetMapping
    public String mostrarEstudiantes(Model model) {
        model.addAttribute("estudiantes", listaEstudiantes);
        model.addAttribute("nuevoEstudiante", new Estudiante());
        return "Estudiantes";
    }

    @PostMapping
    public String registrarEstudiante(@Valid @ModelAttribute("nuevoEstudiante") Estudiante estudiante,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", listaEstudiantes);
            return "Estudiantes";
        }

        listaEstudiantes.add(estudiante);
        return "redirect:/estudiantes";
    }
}

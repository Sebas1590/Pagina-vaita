package org.example.paginaacademia.Controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.Model.Estudiante;
import org.example.paginaacademia.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping
    public String mostrarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("nuevoEstudiante", new Estudiante());
        return "Estudiantes";
    }

    @PostMapping
    public String registrarEstudiante(@Valid @ModelAttribute("nuevoEstudiante") Estudiante estudiante,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteRepository.findAll());
            return "Estudiantes";
        }

        estudianteRepository.save(estudiante);
        return "redirect:/estudiantes";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{dni}")
    public String mostrarFormularioEdicion(@PathVariable String dni, Model model) {
        Estudiante estudiante = estudianteRepository.findById(dni).orElse(null);
        if (estudiante == null) {
            return "redirect:/estudiantes";
        }
        model.addAttribute("nuevoEstudiante", estudiante);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("editando", true);
        return "Estudiantes";
    }

    // Guardar cambios del estudiante
    @PostMapping("/actualizar")
    public String actualizarEstudiante(@Valid @ModelAttribute("nuevoEstudiante") Estudiante estudiante,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteRepository.findAll());
            model.addAttribute("editando", true);
            return "Estudiantes";
        }

        estudianteRepository.save(estudiante);
        return "redirect:/estudiantes";
    }

    // Eliminar estudiante
    @GetMapping("/eliminar/{dni}")
    public String eliminarEstudiante(@PathVariable String dni) {
        estudianteRepository.deleteById(dni);
        return "redirect:/estudiantes";
    }

}

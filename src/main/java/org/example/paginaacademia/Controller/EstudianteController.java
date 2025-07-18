package org.example.paginaacademia.Controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.Entity.Estudiante;
import org.example.paginaacademia.Repository.EstudianteRepository;
import org.example.paginaacademia.Repository.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @GetMapping
    public String mostrarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("nuevoEstudiante", new Estudiante());
        model.addAttribute("niveles", nivelRepository.findAll());
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
        model.addAttribute("niveles", nivelRepository.findAll());
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

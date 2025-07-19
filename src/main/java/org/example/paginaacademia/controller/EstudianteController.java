package org.example.paginaacademia.controller;

import jakarta.validation.Valid;
import org.example.paginaacademia.entity.Estudiante;
import org.example.paginaacademia.repository.IEstudianteRepository;
import org.example.paginaacademia.repository.INivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private IEstudianteRepository IEstudianteRepository;

    @Autowired
    private INivelRepository INivelRepository;

    @GetMapping
    public String mostrarEstudiantes(Model model) {
        model.addAttribute("estudiantes", IEstudianteRepository.findAll());
        model.addAttribute("nuevoEstudiante", new Estudiante());
        model.addAttribute("niveles", INivelRepository.findAll());
        return "Estudiantes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String registrarEstudiante(@Valid @ModelAttribute("nuevoEstudiante") Estudiante estudiante,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("niveles", INivelRepository.findAll());
            model.addAttribute("estudiantes", IEstudianteRepository.findAll());
            return "Estudiantes";
        }

        IEstudianteRepository.save(estudiante);
        return "redirect:/estudiantes";
    }

    // Mostrar formulario de edición
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{dni}")
    public String mostrarFormularioEdicion(@PathVariable String dni, Model model) {
        Estudiante estudiante = IEstudianteRepository.findById(dni).orElse(null);
        if (estudiante == null) {
            return "redirect:/estudiantes";
        }
        model.addAttribute("nuevoEstudiante", estudiante);
        model.addAttribute("estudiantes", IEstudianteRepository.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("niveles", INivelRepository.findAll());
        return "Estudiantes";
    }

    // Guardar cambios del estudiante
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public String actualizarEstudiante(@Valid @ModelAttribute("nuevoEstudiante") Estudiante estudiante,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", IEstudianteRepository.findAll());
            model.addAttribute("editando", true);
            model.addAttribute("niveles", INivelRepository.findAll());
            return "Estudiantes";
        }

        IEstudianteRepository.save(estudiante);
        return "redirect:/estudiantes";
    }

    // Eliminar estudiante
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{dni}")
    public String eliminarEstudiante(@PathVariable String dni) {
        IEstudianteRepository.deleteById(dni);
        return "redirect:/estudiantes";
    }

    @GetMapping("filtrar")
    public String filtrarPorNivel(@RequestParam("nivelId") Long nivelId, Model model) {
        var estudiantesFiltrados = IEstudianteRepository.findByNivelId(nivelId);
        model.addAttribute("estudiantes", estudiantesFiltrados);
        model.addAttribute("niveles", INivelRepository.findAll());
        model.addAttribute("nuevoEstudiante", new Estudiante());
        return "Estudiantes";
    }

}

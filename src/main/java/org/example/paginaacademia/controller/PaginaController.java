package org.example.paginaacademia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "index";
    }

    @GetMapping("/acerca")
    public String mostrarAcerca() {
        return "Acerca";
    }

    @GetMapping("/eventos")
    public String mostrarEventos() {
        return "Eventos";
    }

    @GetMapping("/horarios")
    public String mostrarHorarios() {
        return "Horarios";
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "Contacto";
    }
}


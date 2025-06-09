package org.example.paginaacademia.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;

@Entity
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El contenido de la reseña es obligatorio.")
    @Size(max = 500, message = "Máximo 500 caracteres.")
    private String contenido;

    @Min(1)
    @Max(5)
    private int puntuacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre es obligatorio.") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio.") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El contenido de la reseña es obligatorio.") @Size(max = 500, message = "Máximo 500 caracteres.") String getContenido() {
        return contenido;
    }

    public void setContenido(@NotBlank(message = "El contenido de la reseña es obligatorio.") @Size(max = 500, message = "Máximo 500 caracteres.") String contenido) {
        this.contenido = contenido;
    }

    @Min(1)
    @Max(5)
    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(@Min(1) @Max(5) int puntuacion) {
        this.puntuacion = puntuacion;
    }
}


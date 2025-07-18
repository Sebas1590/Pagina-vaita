package org.example.paginaacademia.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Estudiante")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "El apellido solo debe contener letras")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "El apellido solo debe contener letras")
    private String apellidoMaterno;

    @Id
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos y solo debe contener números")
    private String dni;

    @NotBlank(message = "El número telefónico es obligatorio")
    @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe empezar con 9 y tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$", message = "El email debe terminar en .com")
    private String email;


    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "nivel_id", nullable = false)
    private Nivel nivel;

    public String getApellidosCompletos() {
        return apellidoPaterno + " " + apellidoMaterno;
    }
}

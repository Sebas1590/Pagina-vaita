package org.example.paginaacademia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Estudiante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "nivel")
public class Estudiante {

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?:\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Solo se permiten letras y espacios, sin números ni caracteres especiales")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?:\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Solo se permiten letras y espacios, sin números ni caracteres especiales")
    private String apellidoPaterno;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?:\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Solo se permiten letras y espacios, sin números ni caracteres especiales")
    private String apellidoMaterno;

    @Id
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos y solo debe contener números")
    @Column(unique = true)
    private String dni;

    @NotBlank(message = "El número telefónico es obligatorio")
    @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe empezar con 9 y tener 9 dígitos")
    @Column(unique = true)
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$", message = "El email debe terminar en .com")
    @Column(unique = true)
    private String email;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "nivel_id", nullable = false)
    private Nivel nivel;

    public String getApellidosCompletos() {
        return apellidoMaterno != null && !apellidoMaterno.isBlank()
                ? apellidoPaterno + " " + apellidoMaterno
                : apellidoPaterno;
    }
}
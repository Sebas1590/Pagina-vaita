package org.example.paginaacademia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Nivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "estudiantes")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "nivel", cascade = CascadeType.ALL)
    private List<Estudiante> estudiantes;

    @Override
    public String toString() {
        return nombre;
    }
}

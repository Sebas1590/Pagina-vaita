package org.example.paginaacademia.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Nivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

package com.sanosysalvos.reportes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fechaNacimiento;
    private String sexo;
    private String color;
    private String marcaDistintiva;
    private String detallesAdicionales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id")
    private Especie especie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raza_id")
    private Raza raza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;
}
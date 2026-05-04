package com.sanosysalvos.reportes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "razas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Raza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;
}
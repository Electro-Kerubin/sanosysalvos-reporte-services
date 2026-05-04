package com.sanosysalvos.reportes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
}
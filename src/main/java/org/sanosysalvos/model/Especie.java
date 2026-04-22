package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especie {

    @Id
    @Column(name = "id_especie")
    private Integer idEspecie;

    @Column(name = "descripcion")
    private String descripcion;
}


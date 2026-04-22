package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "raza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raza {

    @Id
    @Column(name = "id_raza")
    private Integer idRaza;

    @Column(name = "descripcion")
    private String descripcion;
}


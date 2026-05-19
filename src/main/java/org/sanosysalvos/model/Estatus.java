package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estatus {

    @Id
    @Column(name = "id_estatus")
    private Integer idEstatus;

    @Column(name = "descripcion_estatus")
    private String descripcionEstatus;
}


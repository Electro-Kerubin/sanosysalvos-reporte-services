package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canal_preferencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanalPreferencia {

    @Id
    @Column(name = "id_canal_preferencia")
    private Integer idCanalPreferencia;

    @Column(name = "descripcion")
    private String descripcion;
}


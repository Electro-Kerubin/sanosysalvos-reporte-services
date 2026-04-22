package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca_distintiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaDistintiva {

    @Id
    @Column(name = "id_marca_distintiva")
    private Integer idMarcaDistintiva;

    @Column(name = "descripcion")
    private String descripcion;
}


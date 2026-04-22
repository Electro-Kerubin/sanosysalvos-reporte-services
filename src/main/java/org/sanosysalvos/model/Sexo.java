package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sexo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sexo {

    @Id
    @Column(name = "id_sexo")
    private Integer idSexo;

    @Column(name = "descripcion")
    private String descripcion;
}


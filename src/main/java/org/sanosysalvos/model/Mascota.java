package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    @Id
    @Column(name = "id_mascota")
    private Integer idMascota;

    @Column(name = "nombre_mascota")
    private String nombreMascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_raza")
    private Raza raza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especie")
    private Especie especie;

    @Column(name = "color_primario")
    private String colorPrimario;

    @Column(name = "color_secundario")
    private String colorSecundario;

    @Column(name = "tamano")
    private String tamano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sexo")
    private Sexo sexo;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "detalles_extra")
    private String detallesExtra;

    @Column(name = "id_chip")
    private String idChip;
}


package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer idContacto;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private Long telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canal_preferencia")
    private CanalPreferencia canalPreferencia;

    @Column(name = "es_temporal")
    private Boolean esTemporal;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
}


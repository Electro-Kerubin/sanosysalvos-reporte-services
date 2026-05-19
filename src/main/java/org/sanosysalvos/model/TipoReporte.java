package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoReporte {

    @Id
    @Column(name = "id_tipo_reporte")
    private Integer idTipoReporte;

    @Column(name = "descripcion_tipo_reporte")
    private String descripcionTipoReporte;
}


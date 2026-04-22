package org.sanosysalvos.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMascotaRequestDTO {
    private Integer idReporteMascota;
    private Integer idTipoReporte;
    private Integer idEstatus;
    private LocalDate fechaExtravio;
    private Integer idContacto;
    private LocalDate fechaAvistamiento;
    private LocalDateTime fechaReporte;
    private Integer idMarcaDistintiva;
    private Integer idMascota;
}


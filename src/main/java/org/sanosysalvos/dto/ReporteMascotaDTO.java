package org.sanosysalvos.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMascotaDTO {
    private Integer idReporteMascota;
    private Integer idTipoReporte;
    private String descripcionTipoReporte;
    private Integer idEstatus;
    private String descripcionEstatus;
    private LocalDate fechaExtravio;
    private Integer idContacto;
    private String nombresContacto;
    private String correoContacto;
    private Long telefonoContacto;
    private Integer idCanalPreferencia;
    private String descripcionCanalPreferencia;
    private LocalDate fechaAvistamiento;
    private LocalDateTime fechaReporte;
    private Integer idMarcaDistintiva;
    private String descripcionMarcaDistintiva;
    private Integer idMascota;
    private String nombreMascota;
    // ── Campos de mascota para edición ──
    private Integer idEspecie;
    private String descripcionEspecie;
    private Integer idRaza;
    private String descripcionRaza;
    private Integer idSexo;
    private String descripcionSexo;
    private String colorPrimario;
    private String tamano;
    private Integer edad;
    private String detallesExtra;
}


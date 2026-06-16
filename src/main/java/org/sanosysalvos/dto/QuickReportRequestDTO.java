package org.sanosysalvos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuickReportRequestDTO {
    // Detalles de la mascota avistada
    private Integer idEspecie;
    private Integer idRaza;
    private String tamano;
    private String colorPrimario;
    private String fotoUrl;
    private String descripcion;

    // Detalles del avistamiento
    private LocalDate fechaAvistamiento;
    private String latitud;
    private String longitud;

    // Información de contacto de quien reporta (temporal)
    private String nombreContacto;
    private String correoContacto;
    private Long telefonoContacto;
}
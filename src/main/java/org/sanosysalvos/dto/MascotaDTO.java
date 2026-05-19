package org.sanosysalvos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {
    private Integer idMascota;
    private String nombreMascota;
    private Integer idRaza;
    private String descripcionRaza;
    private Integer idEspecie;
    private String descripcionEspecie;
    private String colorPrimario;
    private String colorSecundario;
    private String tamano;
    private Integer idSexo;
    private String descripcionSexo;
    private Integer edad;
    private String detallesExtra;
    private String idChip;
}


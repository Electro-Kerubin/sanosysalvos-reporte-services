package org.sanosysalvos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaPublicDTO {
    private String nombreMascota;
    private String colorPrimario;
    private String detallesExtra;
    private String nombreContacto;
    private String telefonoContacto;
    private String correoContacto;
}
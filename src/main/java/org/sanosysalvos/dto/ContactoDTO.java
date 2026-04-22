package org.sanosysalvos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoDTO {
    private Integer idContacto;
    private String nombres;
    private String correo;
    private Long telefono;
    private Integer idCanalPreferencia;
    private String descripcionCanalPreferencia;
}


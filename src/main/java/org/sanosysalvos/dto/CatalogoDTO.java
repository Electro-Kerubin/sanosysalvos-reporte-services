package org.sanosysalvos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoDTO {
    private Integer id;
    private String descripcion;
    private Integer idEspecie;

    public CatalogoDTO(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.idEspecie = null;
    }
}
package org.sanosysalvos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuickReportRequestDTO {
    private UUID qrUuid;
    private String latitud;
    private String longitud;
    private String descripcion;
}
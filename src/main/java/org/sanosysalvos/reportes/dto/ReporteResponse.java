package org.sanosysalvos.reportes.dto;

import java.time.LocalDateTime;

public record ReporteResponse(
        Long id,
        Long mascotaId,
        Long contactoId,
        String ubicacionExtravio,
        String descripcion,
        LocalDateTime fechaReporte,
        String estado,
        String imagenUrl
) {
}
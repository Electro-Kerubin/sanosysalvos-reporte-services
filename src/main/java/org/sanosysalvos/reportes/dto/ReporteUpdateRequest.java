package org.sanosysalvos.reportes.dto;

import jakarta.validation.constraints.Size;

public record ReporteUpdateRequest(
        Long contactoId,
        @Size(max = 255) String ubicacionExtravio,
        @Size(max = 1000) String descripcion,
        @Size(max = 50) String estado,
        String imagenUrl
) {
}
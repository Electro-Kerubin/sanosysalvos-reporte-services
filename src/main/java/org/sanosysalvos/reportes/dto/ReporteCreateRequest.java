package org.sanosysalvos.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReporteCreateRequest(
        @NotNull(message = "mascotaId es obligatorio") Long mascotaId,
        Long contactoId,
        @NotBlank(message = "ubicacionExtravio es obligatoria") @Size(max = 255) String ubicacionExtravio,
        @NotBlank(message = "descripcion es obligatoria") @Size(max = 1000) String descripcion,
        @Size(max = 50) String estado,
        String imagenUrl
) {
}
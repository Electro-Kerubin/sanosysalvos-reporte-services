package org.sanosysalvos.reportes.mapper;

import org.sanosysalvos.reportes.domain.ReporteMascota;
import org.sanosysalvos.reportes.dto.ReporteCreateRequest;
import org.sanosysalvos.reportes.dto.ReporteResponse;

public final class ReporteMapper {

    private ReporteMapper() {
    }

    public static ReporteMascota toEntity(ReporteCreateRequest request) {
        return ReporteMascota.builder()
                .mascotaId(request.mascotaId())
                .contactoId(request.contactoId())
                .ubicacionExtravio(request.ubicacionExtravio())
                .descripcion(request.descripcion())
                .estado(request.estado())
                .imagenUrl(request.imagenUrl())
                .build();
    }

    public static ReporteResponse toResponse(ReporteMascota reporte) {
        return new ReporteResponse(
                reporte.getId(),
                reporte.getMascotaId(),
                reporte.getContactoId(),
                reporte.getUbicacionExtravio(),
                reporte.getDescripcion(),
                reporte.getFechaReporte(),
                reporte.getEstado(),
                reporte.getImagenUrl()
        );
    }
}
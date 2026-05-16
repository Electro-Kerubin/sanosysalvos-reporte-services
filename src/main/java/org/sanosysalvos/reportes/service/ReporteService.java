package org.sanosysalvos.reportes.service;

import org.sanosysalvos.reportes.dto.ReporteCreateRequest;
import org.sanosysalvos.reportes.dto.ReporteResponse;
import org.sanosysalvos.reportes.dto.ReporteUpdateRequest;

import java.util.List;

public interface ReporteService {

    ReporteResponse crear(ReporteCreateRequest request);

    List<ReporteResponse> listar(String estado, Long mascotaId);

    ReporteResponse obtenerPorId(Long id);

    ReporteResponse actualizar(Long id, ReporteUpdateRequest request);

    ReporteResponse actualizarEstado(Long id, String estado);
}
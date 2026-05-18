package org.sanosysalvos.reportes.service.impl;

import org.sanosysalvos.reportes.dto.ReporteCreateRequest;
import org.sanosysalvos.reportes.dto.ReporteResponse;
import org.sanosysalvos.reportes.dto.ReporteUpdateRequest;
import org.sanosysalvos.reportes.exception.ResourceNotFoundException;
import org.sanosysalvos.reportes.domain.ReporteMascota;
import org.sanosysalvos.reportes.mapper.ReporteMapper;
import org.sanosysalvos.reportes.repository.ReporteRepository;
import org.sanosysalvos.reportes.service.ReporteService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@SuppressWarnings("null")
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @Override
    public ReporteResponse crear(ReporteCreateRequest request) {
        ReporteMascota reporte = ReporteMapper.toEntity(request);
        if (!StringUtils.hasText(reporte.getEstado())) {
            reporte.setEstado("reportado");
        }

        return ReporteMapper.toResponse(reporteRepository.save(reporte));
    }

    @Override
    public List<ReporteResponse> listar(String estado, Long mascotaId) {
        List<ReporteMascota> reportes;
        if (StringUtils.hasText(estado) && mascotaId != null) {
            reportes = reporteRepository.findByEstadoIgnoreCaseAndMascotaId(estado, mascotaId);
        } else if (StringUtils.hasText(estado)) {
            reportes = reporteRepository.findByEstadoIgnoreCase(estado);
        } else if (mascotaId != null) {
            reportes = reporteRepository.findByMascotaId(mascotaId);
        } else {
            reportes = reporteRepository.findAll();
        }

        return reportes.stream().map(ReporteMapper::toResponse).toList();
    }

    @Override
    public ReporteResponse obtenerPorId(Long id) {
        return ReporteMapper.toResponse(buscarReporte(id));
    }

    @Override
    public ReporteResponse actualizar(Long id, ReporteUpdateRequest request) {
        ReporteMascota reporte = buscarReporte(id);

        if (request.contactoId() != null) {
            reporte.setContactoId(request.contactoId());
        }
        if (StringUtils.hasText(request.ubicacionExtravio())) {
            reporte.setUbicacionExtravio(request.ubicacionExtravio());
        }
        if (StringUtils.hasText(request.descripcion())) {
            reporte.setDescripcion(request.descripcion());
        }
        if (StringUtils.hasText(request.estado())) {
            reporte.setEstado(request.estado());
        }
        if (request.imagenUrl() != null) {
            reporte.setImagenUrl(request.imagenUrl());
        }

        return ReporteMapper.toResponse(reporteRepository.save(reporte));
    }

    @Override
    public ReporteResponse actualizarEstado(Long id, String estado) {
        if (!StringUtils.hasText(estado)) {
            throw new IllegalArgumentException("estado es obligatorio");
        }

        ReporteMascota reporte = buscarReporte(id);
        reporte.setEstado(estado);

        return ReporteMapper.toResponse(reporteRepository.save(reporte));
    }

    private ReporteMascota buscarReporte(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el reporte con id " + id));
    }

}
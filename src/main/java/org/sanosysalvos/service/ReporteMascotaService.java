package org.sanosysalvos.service;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.ReporteMascotaDTO;
import org.sanosysalvos.dto.ReporteMascotaRequestDTO;
import org.sanosysalvos.model.*;
import org.sanosysalvos.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteMascotaService {

    private final ReporteMascotaRepository reporteRepo;
    private final TipoReporteRepository tipoReporteRepo;
    private final EstatusRepository estatusRepo;
    private final ContactoRepository contactoRepo;
    private final MarcaDistintivaRepository marcaRepo;
    private final MascotaRepository mascotaRepo;

    public List<ReporteMascotaDTO> findAll() {
        return reporteRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReporteMascotaDTO findById(Integer id) {
        return reporteRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
    }

    public List<ReporteMascotaDTO> findByTipoReporte(Integer idTipoReporte) {
        return reporteRepo.findByTipoReporte_IdTipoReporte(idTipoReporte)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findByEstatus(Integer idEstatus) {
        return reporteRepo.findByEstatus_IdEstatus(idEstatus)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findByMascota(Integer idMascota) {
        return reporteRepo.findByMascota_IdMascota(idMascota)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findByContacto(Integer idContacto) {
        return reporteRepo.findByContacto_IdContacto(idContacto)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findByFechas(LocalDateTime desde, LocalDateTime hasta) {
        return reporteRepo.findByFechaReporteBetween(desde, hasta)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findMascotasPerdidas() {
        return reporteRepo.findMascotasPerdidas()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteMascotaDTO> findMascotasEncontradas() {
        return reporteRepo.findMascotasEncontradas()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReporteMascotaDTO create(ReporteMascotaRequestDTO request) {
        ReporteMascota entity = toEntity(request);
        return toDTO(reporteRepo.save(entity));
    }

    public ReporteMascotaDTO update(Integer id, ReporteMascotaRequestDTO request) {
        reporteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        request.setIdReporteMascota(id);
        return toDTO(reporteRepo.save(toEntity(request)));
    }

    public void delete(Integer id) {
        reporteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        reporteRepo.deleteById(id);
    }

    // ---- Mappers ----

    private ReporteMascotaDTO toDTO(ReporteMascota r) {
        ReporteMascotaDTO dto = new ReporteMascotaDTO();
        dto.setIdReporteMascota(r.getIdReporteMascota());
        dto.setFechaExtravio(r.getFechaExtravio());
        dto.setFechaAvistamiento(r.getFechaAvistamiento());
        dto.setFechaReporte(r.getFechaReporte());

        if (r.getTipoReporte() != null) {
            dto.setIdTipoReporte(r.getTipoReporte().getIdTipoReporte());
            dto.setDescripcionTipoReporte(r.getTipoReporte().getDescripcionTipoReporte());
        }
        if (r.getEstatus() != null) {
            dto.setIdEstatus(r.getEstatus().getIdEstatus());
            dto.setDescripcionEstatus(r.getEstatus().getDescripcionEstatus());
        }
        if (r.getContacto() != null) {
            dto.setIdContacto(r.getContacto().getIdContacto());
            dto.setNombresContacto(r.getContacto().getNombres());
        }
        if (r.getMarcaDistintiva() != null) {
            dto.setIdMarcaDistintiva(r.getMarcaDistintiva().getIdMarcaDistintiva());
            dto.setDescripcionMarcaDistintiva(r.getMarcaDistintiva().getDescripcion());
        }
        if (r.getMascota() != null) {
            dto.setIdMascota(r.getMascota().getIdMascota());
            dto.setNombreMascota(r.getMascota().getNombreMascota());
        }
        return dto;
    }

    private ReporteMascota toEntity(ReporteMascotaRequestDTO req) {
        ReporteMascota entity = new ReporteMascota();
        entity.setIdReporteMascota(req.getIdReporteMascota());
        entity.setFechaExtravio(req.getFechaExtravio());
        entity.setFechaAvistamiento(req.getFechaAvistamiento());
        entity.setFechaReporte(req.getFechaReporte());

        if (req.getIdTipoReporte() != null)
            entity.setTipoReporte(tipoReporteRepo.findById(req.getIdTipoReporte())
                    .orElseThrow(() -> new RuntimeException("TipoReporte no encontrado")));
        if (req.getIdEstatus() != null)
            entity.setEstatus(estatusRepo.findById(req.getIdEstatus())
                    .orElseThrow(() -> new RuntimeException("Estatus no encontrado")));
        if (req.getIdContacto() != null)
            entity.setContacto(contactoRepo.findById(req.getIdContacto())
                    .orElseThrow(() -> new RuntimeException("Contacto no encontrado")));
        if (req.getIdMarcaDistintiva() != null)
            entity.setMarcaDistintiva(marcaRepo.findById(req.getIdMarcaDistintiva())
                    .orElseThrow(() -> new RuntimeException("MarcaDistintiva no encontrada")));
        if (req.getIdMascota() != null)
            entity.setMascota(mascotaRepo.findById(req.getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada")));

        return entity;
    }
}


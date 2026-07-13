package org.sanosysalvos.service;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.ReporteMascotaDTO;
import org.sanosysalvos.dto.ReporteMascotaRequestDTO;
import org.sanosysalvos.model.*;
import org.sanosysalvos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    private final RazaRepository razaRepo;
    private final EspecieRepository especieRepo;

    // ── Outbox: persistencia desacoplada del evento ──────────────────────────
    private final OutboxService outboxService;

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

    @Transactional
    public ReporteMascotaDTO create(ReporteMascotaRequestDTO request) {
        ReporteMascota entity = toEntity(request);

    // Generador QR
    if (entity.getMascota() != null && entity.getMascota().getQrUuid() == null) {
        entity.getMascota().setQrUuid(UUID.randomUUID());
        mascotaRepo.save(entity.getMascota());
    }

    // ── Desnormalización para el motor de coincidencias ──────────────────
    if (entity.getMascota() != null) {
        Mascota m = entity.getMascota();
        // color
        entity.setColor(m.getColorPrimario());
        // tamano
        entity.setTamano(m.getTamano());
        // raza (descripcion del catálogo)
        if (m.getRaza() != null) {
            entity.setRaza(m.getRaza().getDescripcion());
        }
    }
    // ─────────────────────────────────────────────────────────────────────

    ReporteMascotaDTO dto = toDTO(reporteRepo.save(entity));
    outboxService.registrar("REPORTE_CREADO", dto.getIdReporteMascota(), dto);
    return dto;
}

    @Transactional
    public ReporteMascotaDTO update(Integer id, ReporteMascotaRequestDTO request) {
        reporteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        request.setIdReporteMascota(id);
        ReporteMascotaDTO dto = toDTO(reporteRepo.save(toEntity(request)));

        // Patrón Outbox: notifica que el reporte fue actualizado
        outboxService.registrar("REPORTE_ACTUALIZADO", dto.getIdReporteMascota(), dto);

        return dto;
    }

    public void delete(Integer id) {
        reporteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        reporteRepo.deleteById(id);
    }

    public org.sanosysalvos.dto.MascotaPublicDTO getMascotaPublicInfoByQr(UUID qrUuid) {
        ReporteMascota reporte = reporteRepo.findFirstByMascotaQrUuidOrderByFechaReporteDesc(qrUuid)
                .orElseThrow(() -> new RuntimeException("No se encontró información pública para este código QR"));

        Mascota mascota = reporte.getMascota();
        Contacto contacto = reporte.getContacto();

        return org.sanosysalvos.dto.MascotaPublicDTO.builder()
                .nombreMascota(mascota.getNombreMascota())
                .detallesExtra(mascota.getDetallesExtra())
                .colorPrimario(mascota.getColorPrimario())
                .nombreContacto(contacto != null ? contacto.getNombres() : "No disponible")
                .telefonoContacto(contacto != null ? String.valueOf(contacto.getTelefono()) : "No disponible")
                .correoContacto(contacto != null ? contacto.getCorreo() : "No disponible")
                .build();
    }

    @Transactional
    public ReporteMascotaDTO createQuickReport(org.sanosysalvos.dto.QuickReportRequestDTO request) {
        // 1. Crear un contacto temporal para la persona que reporta.
        // Este contacto no requiere cuenta y se podrá purgar automáticamente.
        Contacto contactoTemporal = new Contacto();
        contactoTemporal.setNombres(request.getNombreContacto());
        contactoTemporal.setCorreo(request.getCorreoContacto());
        contactoTemporal.setTelefono(request.getTelefonoContacto());
        contactoTemporal.setEsTemporal(true);
        contactoTemporal.setCreadoEn(LocalDateTime.now());
        Contacto contactoGuardado = contactoRepo.save(contactoTemporal);

        // 2. Crear una mascota temporal con los datos del avistamiento.
        // No se le asigna dueño ni QR, es solo para el registro del avistamiento.
        Mascota mascotaAvistada = new Mascota();
        if (request.getIdEspecie() != null) {
            mascotaAvistada.setEspecie(especieRepo.findById(request.getIdEspecie())
                    .orElseThrow(() -> new RuntimeException("Especie no encontrada con id: " + request.getIdEspecie())));
        }
        if (request.getIdRaza() != null) {
            mascotaAvistada.setRaza(razaRepo.findById(request.getIdRaza())
                    .orElseThrow(() -> new RuntimeException("Raza no encontrada con id: " + request.getIdRaza())));
        }
        mascotaAvistada.setTamano(request.getTamano());
        mascotaAvistada.setColorPrimario(request.getColorPrimario());
        mascotaAvistada.setDetallesExtra(request.getDescripcion());
        Mascota mascotaGuardada = mascotaRepo.save(mascotaAvistada);

        // 3. Crear el reporte de tipo "Avistamiento".
        ReporteMascota reporte = new ReporteMascota();
        reporte.setTipoReporte(tipoReporteRepo.findById(3) // 3 = Avistamiento
                .orElseThrow(() -> new RuntimeException("Tipo de reporte 'Avistamiento' no encontrado")));
        reporte.setEstatus(estatusRepo.findById(1) // 1 = Activo
                .orElseThrow(() -> new RuntimeException("Estatus 'Activo' no encontrado")));
        reporte.setFechaAvistamiento(request.getFechaAvistamiento() != null ? request.getFechaAvistamiento() : LocalDate.now());
        reporte.setFechaReporte(LocalDateTime.now());
        reporte.setMascota(mascotaGuardada);
        reporte.setContacto(contactoGuardado);
        // TODO: Guardar coordenadas (request.getLatitud(), request.getLongitud()) en la tabla/servicio correspondiente.

        ReporteMascota reporteGuardado = reporteRepo.save(reporte);
        ReporteMascotaDTO dto = toDTO(reporteGuardado);

        // 4. Registrar evento para notificaciones (ej. motor de coincidencias).
        outboxService.registrar("AVISTAMIENTO_EXPRESS_CREADO", dto.getIdReporteMascota(), dto);

        return dto;
    }

    // ---- Mappers ----

    private ReporteMascotaDTO toDTO(ReporteMascota r) {
        ReporteMascotaDTO dto = new ReporteMascotaDTO();
        dto.setIdReporteMascota(r.getIdReporteMascota());
        dto.setIdTipoReporte(r.getTipoReporte() != null ? r.getTipoReporte().getIdTipoReporte() : null);
        dto.setDescripcionTipoReporte(r.getTipoReporte() != null ? r.getTipoReporte().getDescripcionTipoReporte() : null);
        dto.setIdEstatus(r.getEstatus() != null ? r.getEstatus().getIdEstatus() : null);
        dto.setDescripcionEstatus(r.getEstatus() != null ? r.getEstatus().getDescripcionEstatus() : null);
        dto.setFechaExtravio(r.getFechaExtravio());
        dto.setFechaAvistamiento(r.getFechaAvistamiento());
        dto.setFechaReporte(r.getFechaReporte());
        dto.setIdMarcaDistintiva(r.getMarcaDistintiva() != null ? r.getMarcaDistintiva().getIdMarcaDistintiva() : null);
        dto.setDescripcionMarcaDistintiva(r.getMarcaDistintiva() != null ? r.getMarcaDistintiva().getDescripcion() : null);

        if (r.getContacto() != null) {
            dto.setIdContacto(r.getContacto().getIdContacto());
            dto.setNombresContacto(r.getContacto().getNombres());
            dto.setCorreoContacto(r.getContacto().getCorreo());
            dto.setTelefonoContacto(r.getContacto().getTelefono());
            if (r.getContacto().getCanalPreferencia() != null) {
                dto.setIdCanalPreferencia(r.getContacto().getCanalPreferencia().getIdCanalPreferencia());
                dto.setDescripcionCanalPreferencia(r.getContacto().getCanalPreferencia().getDescripcion());
            }
        }

        if (r.getMascota() != null) {
            Mascota m = r.getMascota();
            dto.setIdMascota(m.getIdMascota());
            dto.setNombreMascota(m.getNombreMascota());
            dto.setColorPrimario(m.getColorPrimario());
            dto.setTamano(m.getTamano());
            dto.setEdad(m.getEdad());
            dto.setDetallesExtra(m.getDetallesExtra());
            if (m.getEspecie() != null) {
                dto.setIdEspecie(m.getEspecie().getIdEspecie());
                dto.setDescripcionEspecie(m.getEspecie().getDescripcion());
            }
            if (m.getRaza() != null) {
                dto.setIdRaza(m.getRaza().getIdRaza());
                dto.setDescripcionRaza(m.getRaza().getDescripcion());
            }
            if (m.getSexo() != null) {
                dto.setIdSexo(m.getSexo().getIdSexo());
                dto.setDescripcionSexo(m.getSexo().getDescripcion());
            }
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

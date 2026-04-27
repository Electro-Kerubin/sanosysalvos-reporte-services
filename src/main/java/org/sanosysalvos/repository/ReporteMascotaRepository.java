package org.sanosysalvos.repository;

import org.sanosysalvos.model.ReporteMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteMascotaRepository extends JpaRepository<ReporteMascota, Integer> {

    List<ReporteMascota> findByTipoReporte_IdTipoReporte(Integer idTipoReporte);

    List<ReporteMascota> findByEstatus_IdEstatus(Integer idEstatus);

    List<ReporteMascota> findByMascota_IdMascota(Integer idMascota);

    List<ReporteMascota> findByContacto_IdContacto(Integer idContacto);

    List<ReporteMascota> findByFechaReporteBetween(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT r FROM ReporteMascota r WHERE r.tipoReporte.idTipoReporte = 1 AND r.estatus.idEstatus = 1")
    List<ReporteMascota> findMascotasPerdidas();

    @Query("SELECT r FROM ReporteMascota r WHERE r.tipoReporte.idTipoReporte = 2 AND r.estatus.idEstatus = 1")
    List<ReporteMascota> findMascotasEncontradas();
}

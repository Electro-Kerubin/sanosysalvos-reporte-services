package org.sanosysalvos.reportes.repository;

import org.sanosysalvos.reportes.domain.ReporteMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteMascota, Long> {

    List<ReporteMascota> findByEstadoIgnoreCase(String estado);

    List<ReporteMascota> findByMascotaId(Long mascotaId);

    List<ReporteMascota> findByEstadoIgnoreCaseAndMascotaId(String estado, Long mascotaId);
}
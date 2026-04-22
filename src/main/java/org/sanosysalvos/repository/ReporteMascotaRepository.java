package org.sanosysalvos.repository;

import org.sanosysalvos.model.ReporteMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteMascotaRepository extends JpaRepository<ReporteMascota, Integer> {

    List<ReporteMascota> findByTipoReporte_IdTipoReporte(Integer idTipoReporte);

    List<ReporteMascota> findByEstatus_IdEstatus(Integer idEstatus);

    List<ReporteMascota> findByMascota_IdMascota(Integer idMascota);
}


package org.sanosysalvos.repository;

import org.sanosysalvos.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    List<Mascota> findByRaza_IdRaza(Integer idRaza);

    List<Mascota> findByEspecie_IdEspecie(Integer idEspecie);

    List<Mascota> findByColorPrimarioIgnoreCase(String colorPrimario);

    List<Mascota> findByTamanoIgnoreCase(String tamano);

    Optional<Mascota> findByIdChip(String idChip);

    List<Mascota> findByNombreMascotaContainingIgnoreCase(String nombre);
}

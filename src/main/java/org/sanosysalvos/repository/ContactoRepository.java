package org.sanosysalvos.repository;

import org.sanosysalvos.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    Optional<Contacto> findByCorreo(String correo);
}

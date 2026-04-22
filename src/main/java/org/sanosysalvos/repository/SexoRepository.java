package org.sanosysalvos.repository;

import org.sanosysalvos.model.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, Integer> {}


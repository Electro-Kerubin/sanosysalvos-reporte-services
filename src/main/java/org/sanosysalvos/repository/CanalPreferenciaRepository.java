package org.sanosysalvos.repository;

import org.sanosysalvos.model.CanalPreferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanalPreferenciaRepository extends JpaRepository<CanalPreferencia, Integer> {}


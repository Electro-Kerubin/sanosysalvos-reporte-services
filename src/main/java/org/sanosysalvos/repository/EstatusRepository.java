package org.sanosysalvos.repository;

import org.sanosysalvos.model.Estatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatusRepository extends JpaRepository<Estatus, Integer> {}


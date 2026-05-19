package org.sanosysalvos.repository;

import org.sanosysalvos.model.MarcaDistintiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaDistintivaRepository extends JpaRepository<MarcaDistintiva, Integer> {}


package org.sanosysalvos.repository;

import org.sanosysalvos.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    /** Recupera todos los eventos pendientes de publicación */
    List<OutboxEvent> findByEstadoOrderByCreadoEnAsc(OutboxEvent.EstadoEvento estado);
}


package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Patrón Outbox — Persistencia Desacoplada.
 *
 * Garantiza que ningún evento se pierda aunque RabbitMQ no esté disponible
 * en el momento de guardar el reporte.
 *
 * Flujo:
 *  1. Se guarda el ReporteMascota en la BD  ┐ misma transacción → atomicidad
 *  2. Se guarda el OutboxEvent en la BD     ┘
 *  3. El OutboxEventPublisher (scheduled) lee los eventos PENDIENTE
 *     y los publica a RabbitMQ.
 *  4. Si se publica con éxito, el estado cambia a PUBLICADO.
 */
@Entity
@Table(name = "outbox_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Tipo de evento, ej: "REPORTE_CREADO", "REPORTE_ACTUALIZADO" */
    @Column(nullable = false, length = 100)
    private String tipoEvento;

    /** ID del agregado que originó el evento (idReporteMascota) */
    @Column(nullable = false)
    private Integer agregadoId;

    /** Payload serializado en JSON */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    /** Estado del evento: PENDIENTE → PUBLICADO | ERROR */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEvento estado;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    private LocalDateTime publicadoEn;

    /** Intentos de publicación (útil para retry) */
    @Column(nullable = false)
    @Builder.Default
    private int intentos = 0;

    public enum EstadoEvento {
        PENDIENTE, PUBLICADO, ERROR
    }
}


package org.sanosysalvos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sanosysalvos.model.OutboxEvent;
import org.sanosysalvos.repository.OutboxEventRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Patrón Outbox — Publisher programado.
 *
 * Cada 5 segundos lee los eventos PENDIENTE desde la tabla outbox_events
 * y los publica a RabbitMQ. Si el envío falla, marca el evento como ERROR
 * para reintentos posteriores.
 *
 * Esto garantiza persistencia desacoplada: aunque RabbitMQ esté caído
 * en el momento de crear el reporte, el evento no se pierde — quedó
 * guardado en la BD y se publicará en cuanto RabbitMQ vuelva a estar disponible.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final OutboxEventRepository outboxRepo;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    /** Se ejecuta cada 5 segundos — publica eventos PENDIENTE */
    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publicarEventosPendientes() {
        List<OutboxEvent> pendientes = outboxRepo
                .findByEstadoOrderByCreadoEnAsc(OutboxEvent.EstadoEvento.PENDIENTE);

        if (pendientes.isEmpty()) return;

        log.info("[Outbox] Procesando {} evento(s) pendiente(s)", pendientes.size());

        for (OutboxEvent evento : pendientes) {
            try {
                // Publica el payload JSON directamente al exchange
                rabbitTemplate.convertAndSend(exchange, routingKey, evento.getPayload());

                evento.setEstado(OutboxEvent.EstadoEvento.PUBLICADO);
                evento.setPublicadoEn(LocalDateTime.now());
                log.info("[Outbox] Evento {} publicado correctamente (idReporte={})",
                        evento.getTipoEvento(), evento.getAgregadoId());

            } catch (Exception ex) {
                evento.setIntentos(evento.getIntentos() + 1);
                if (evento.getIntentos() >= 3) {
                    evento.setEstado(OutboxEvent.EstadoEvento.ERROR);
                    log.error("[Outbox] Evento {} marcado como ERROR tras 3 intentos (idReporte={})",
                            evento.getTipoEvento(), evento.getAgregadoId());
                } else {
                    log.warn("[Outbox] Fallo al publicar evento {} — intento {}/3",
                            evento.getTipoEvento(), evento.getIntentos());
                }
            }

            outboxRepo.save(evento);
        }
    }
}



package org.sanosysalvos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sanosysalvos.model.OutboxEvent;
import org.sanosysalvos.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio responsable de registrar eventos en la tabla outbox_events.
 *
 * Al ser llamado dentro de la misma transacción que guarda el ReporteMascota,
 * se garantiza atomicidad: o ambas escrituras se confirman o ninguna lo hace.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxEventRepository outboxRepo;
    private final ObjectMapper objectMapper = buildObjectMapper();

    /**
     * Registra un evento en el Outbox para ser publicado a RabbitMQ posteriormente.
     *
     * @param tipoEvento   Nombre del evento, ej: "REPORTE_CREADO"
     * @param agregadoId   ID del ReporteMascota
     * @param payload      Objeto a serializar como JSON (ej: ReporteMascotaDTO)
     */
    public void registrar(String tipoEvento, Integer agregadoId, Object payload) {
        try {
            String payloadJson = objectMapper.writeValueAsString(payload);

            OutboxEvent evento = OutboxEvent.builder()
                    .tipoEvento(tipoEvento)
                    .agregadoId(agregadoId)
                    .payload(payloadJson)
                    .estado(OutboxEvent.EstadoEvento.PENDIENTE)
                    .creadoEn(LocalDateTime.now())
                    .intentos(0)
                    .build();

            outboxRepo.save(evento);
            log.info("[Outbox] Evento '{}' registrado para idReporte={}", tipoEvento, agregadoId);

        } catch (JsonProcessingException ex) {
            log.error("[Outbox] Error serializando payload para evento '{}': {}", tipoEvento, ex.getMessage());
            throw new RuntimeException("Error al registrar evento en Outbox", ex);
        }
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}


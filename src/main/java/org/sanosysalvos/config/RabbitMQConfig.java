package org.sanosysalvos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el Reporte Service.
 *
 * Topología:
 *   Exchange (topic): reporte-exchange
 *       └── Queue durable: reporte-coincidencias-queue
 *               binding routing-key: reporte.nuevo
 *
 * El Motor de Coincidencias consumirá desde "reporte-coincidencias-queue".
 */
@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queue}")
    private String queue;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    /** Exchange de tipo Topic — permite routing flexible con wildcards */
    @Bean
    public TopicExchange reporteExchange() {
        return new TopicExchange(exchange, true, false);
    }

    /** Cola durable — los mensajes sobreviven reinicios de RabbitMQ */
    @Bean
    public Queue reporteQueue() {
        return QueueBuilder.durable(queue).build();
    }

    /** Binding: conecta la cola al exchange con la routing key */
    @Bean
    public Binding reporteBinding(Queue reporteQueue, TopicExchange reporteExchange) {
        return BindingBuilder
                .bind(reporteQueue)
                .to(reporteExchange)
                .with(routingKey);
    }

    /** Convierte objetos Java ↔ JSON automáticamente */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /** RabbitTemplate configurado con el converter JSON */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}


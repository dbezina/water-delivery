package com.bezina.water_delivery.inventory_service.config;

import com.bezina.water_delivery.core.config.BaseKafkaConfig;
import com.bezina.water_delivery.core.events.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.Map;

@Configuration
public class KafkaConfig extends BaseKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroup;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;

    /* @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;

    @Value("${spring.kafka.producer.properties.linger}")
    private String linger ;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private String requestTimeout;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private String idempotence;

    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
    private String maxInFlightRequests;*/


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> orderCreatedKafkaListenerFactory() {
        return listenerFactory(OrderCreatedEvent.class, Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG,consumerGroup ,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> paymentFailedKafkaListenerFactory() {
        return listenerFactory(PaymentFailedEvent.class, Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG,consumerGroup ,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        ));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeliveryStatusChangedEvent> deliveryStatusKafkaListenerFactory() {
        return listenerFactory(DeliveryStatusChangedEvent.class, Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG,consumerGroup ,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> stockInsufficientKafkaListenerFactory() {
        return listenerFactory(StockInsufficientEvent.class, Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG,consumerGroup ,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> isDeliveredKafkaListenerFactory() {
        return listenerFactory(IsDeliveredEvent.class, Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG,consumerGroup ,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        ));
    }
}

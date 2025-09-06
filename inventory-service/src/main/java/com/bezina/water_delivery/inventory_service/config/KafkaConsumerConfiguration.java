package com.bezina.water_delivery.inventory_service.config;

import com.bezina.water_delivery.core.events.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private <T> ConsumerFactory<String, T> consumerFactory(Class<T> clazz) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "inventory-service",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> listenerFactory(Class<T> clazz) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(clazz));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> orderCreatedKafkaListenerFactory() {
        return listenerFactory(OrderCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> paymentFailedKafkaListenerFactory() {
        return listenerFactory(PaymentFailedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeliveryStatusChangedEvent> deliveryStatusKafkaListenerFactory() {
        return listenerFactory(DeliveryStatusChangedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> stockInsufficientKafkaListenerFactory() {
        return listenerFactory(StockInsufficientEvent.class);
    }
    @Bean
    public ConsumerFactory<String, IsDeliveredEvent> isDeliveredConsumerFactory() {
        return consumerFactory(IsDeliveredEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> isDeliveredKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(isDeliveredConsumerFactory());
        return factory;
    }

}

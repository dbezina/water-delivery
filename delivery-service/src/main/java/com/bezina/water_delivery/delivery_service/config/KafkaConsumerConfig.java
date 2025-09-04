package com.bezina.water_delivery.delivery_service.config;

import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.core.events.CourierAssignmentEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, OrderConfirmedEvent> orderConfirmedEventConsumerFactory() {
        JsonDeserializer<OrderConfirmedEvent> deserializer =
                new JsonDeserializer<>(OrderConfirmedEvent.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "admin-service",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderConfirmedEvent> orderConfirmedKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderConfirmedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConfirmedEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, CourierAssignmentEvent> courierEventConsumerFactory() {
        JsonDeserializer<CourierAssignmentEvent> deserializer =
                new JsonDeserializer<>(CourierAssignmentEvent.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG, "courier-service",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CourierAssignmentEvent> courierEventKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CourierAssignmentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(courierEventConsumerFactory());
        return factory;
    }
}

package com.bezina.water_delivery.order_service;


import com.bezina.water_delivery.core.events.StockInsufficientEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, StockInsufficientEvent> stockInsufficientEventFactory() {
        JsonDeserializer<StockInsufficientEvent> deserializer =
                new JsonDeserializer<>(StockInsufficientEvent.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "order-service",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> stockInsufficientKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stockInsufficientEventFactory());
        return factory;
    }
}

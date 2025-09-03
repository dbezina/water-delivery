package com.bezina.water_delivery.order_service.config;


import com.bezina.water_delivery.core.events.*;
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


    //for isDelivered
    @Bean
    public ConsumerFactory<String, IsDeliveredEvent> isDeliveredConsumerFactory() {
        JsonDeserializer<IsDeliveredEvent> deserializer =
                new JsonDeserializer<>(IsDeliveredEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> isDeliveredKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(isDeliveredConsumerFactory());
        return factory;
    }

    //for DeliveryStatusChangedEvent
    @Bean
    public ConsumerFactory<String, DeliveryStatusChangedEvent> deliveryStatusConsumerFactory() {
        JsonDeserializer<DeliveryStatusChangedEvent> deserializer =
                new JsonDeserializer<>(DeliveryStatusChangedEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, DeliveryStatusChangedEvent> deliveryStatusKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeliveryStatusChangedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deliveryStatusConsumerFactory());
        return factory;
    }
    //for PaymentConfirmed
    @Bean
    public ConsumerFactory<String, PaymentConfirmedEvent> PaymentConfirmedEventFactory() {
        JsonDeserializer<PaymentConfirmedEvent> deserializer =
                new JsonDeserializer<>(PaymentConfirmedEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmedEvent> paymentConfirmedKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(PaymentConfirmedEventFactory());
        return factory;
    }

        // for Payment Failed

    @Bean
    public ConsumerFactory<String, PaymentFailedEvent> PaymentFailedEventFactory() {
        JsonDeserializer<PaymentFailedEvent> deserializer =
                new JsonDeserializer<>(PaymentFailedEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> paymentFailedKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(PaymentFailedEventFactory());
        return factory;
    }
}

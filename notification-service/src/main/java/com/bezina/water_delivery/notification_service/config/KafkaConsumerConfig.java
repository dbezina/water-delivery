package com.bezina.water_delivery.notification_service.config;

import com.bezina.water_delivery.core.events.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /********************************USER***********************************/

    //for PaymentConfirmed
    @Bean
    public ConsumerFactory<String, PaymentConfirmedEvent> PaymentConfirmedEventFactory() {
        JsonDeserializer<PaymentConfirmedEvent> deserializer =
                new JsonDeserializer<>(PaymentConfirmedEvent.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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
                Map.of( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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
    //for DeliveryStatusChangedEvent
    @Bean
    public ConsumerFactory<String, DeliveryStatusChangedEvent> deliveryStatusConsumerFactory() {
        JsonDeserializer<DeliveryStatusChangedEvent> deserializer =
                new JsonDeserializer<>(DeliveryStatusChangedEvent.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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

    @Bean
    public ConsumerFactory<String, StockInsufficientEvent> stockInsufficientEventFactory() {
        JsonDeserializer<StockInsufficientEvent> deserializer =
                new JsonDeserializer<>(StockInsufficientEvent.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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

    @Bean
    public ConsumerFactory<String, IsDeliveredEvent> isDeliveredEventFactory() {
        JsonDeserializer<IsDeliveredEvent> deserializer =
                new JsonDeserializer<>(IsDeliveredEvent.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> isDeliveredKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IsDeliveredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(isDeliveredEventFactory());
        return factory;
    }
    /*********************COURIER***********************************/

    @Bean
    public ConsumerFactory<String, CourierAssignmentEvent> courierEventConsumerFactory() {
        JsonDeserializer<CourierAssignmentEvent> deserializer =
                new JsonDeserializer<>(CourierAssignmentEvent.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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

    /**************************ADMIN*******************************************************************/
    @Bean
    public ConsumerFactory<String, OrderConfirmedEvent> orderConfirmedEventConsumerFactory() {
        JsonDeserializer<OrderConfirmedEvent> deserializer =
                new JsonDeserializer<>(OrderConfirmedEvent.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
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
    public ConsumerFactory<String, LowStockEvent> lowStockEventFactory() {
        JsonDeserializer<LowStockEvent> deserializer =
                new JsonDeserializer<>(LowStockEvent.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "notification-service",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LowStockEvent> lowStockKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LowStockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(lowStockEventFactory());
        return factory;
    }
}

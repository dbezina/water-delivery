package com.bezina.water_delivery.core.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.Map;


public class BaseKafkaConfig {

    protected <T> ConsumerFactory<String, T> consumerFactory(Class<T> clazz, Map<String, Object> kafkaSettings) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                kafkaSettings,
                new StringDeserializer(),
                deserializer
        );
    }

    protected <T> ConcurrentKafkaListenerContainerFactory<String, T> listenerFactory(
            Class<T> clazz,
            Map<String, Object> kafkaSettings
    ) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(clazz, kafkaSettings));
        return factory;
    }
}

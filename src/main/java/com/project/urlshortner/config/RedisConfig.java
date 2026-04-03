package com.project.urlshortner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /*
        ? RedisTemplate is a helper class that simplifies Redis data access code. It provides methods for common Redis operations.
        ? The StringRedisSerializer ensures data is stored as readable text in Redis, not binary.
        ? By configuring the RedisTemplate with StringRedisSerializer for both keys and values, we ensure that all data stored in Redis is human-readable, which can be helpful for debugging and monitoring purposes.
    */

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}

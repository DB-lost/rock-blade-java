/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-02-22 18:14:46
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-02-22 18:25:47
 * @FilePath: /rock-blade-admin-java/src/main/java/com/rockblade/framework/config/RedisConfig.java
 * @Description: Redis配置类。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    // 设置连接工厂
    redisTemplate.setConnectionFactory(connectionFactory);

    // 使用GenericJackson2JsonRedisSerializer替代旧的序列化器
    GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
    StringRedisSerializer stringSerializer = new StringRedisSerializer();

    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(jsonSerializer);
    redisTemplate.setHashValueSerializer(jsonSerializer);

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}

package com.dhcc.urms.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Annotation:
 * 配置 Redis
 *
 * @Author: Adam Ming
 * @Date: Apr 23, 2020 at 3:42:27 PM
 */
@Configuration
@EnableCaching
@EnableRedisHttpSession
public class RedisConfiguration extends CachingConfigurerSupport {
    RedisConnectionFactory redisConnectionFactory;

    /*
     * 注入 RedisConnectionFactory
     */
    @Autowired
    public RedisConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /*
     * 配置 redisTemplate
     * 通过 redisConnectionFactory 引入 redis 在配置文件中的连接配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);

        return redisTemplate;
    }

    /*
     * 设置数据存入 redis 的序列化方式
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        // redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashValueSerializer(jacksonSerializer);
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.setConnectionFactory(factory);
    }

    /*
     * 实例化 HashOperations 对象，可以使用 Hash 类型操作
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /*
     * 实例化 ValueOperations 对象,可以使用 String 操作
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
}
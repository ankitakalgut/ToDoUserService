package com.bridgelabz.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.bridgelabz.userservice.model.User;

/**********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Configuration class for redis*
 **********************************************************************************/
@Configuration
public class RedisConfiguration
{
	@Bean
	public JedisConnectionFactory connectionFactory() 
	{
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, User> redisTemplate() 
	{
		RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}
}
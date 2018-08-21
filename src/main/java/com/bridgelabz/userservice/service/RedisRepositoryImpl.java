package com.bridgelabz.userservice.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.repository.RedisRepository;
import com.bridgelabz.userservice.utility.JWToken;

/***********************************************************************************
 * @author Ankita Kalgutkar
 *
 *PURPOSE:Implementation of redis repository.
 ************************************************************************************/
@Repository
public class RedisRepositoryImpl implements RedisRepository<String, User>
{
	private static final String key="TOKEN";
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	private HashOperations<String, String, String> hashOps;

	RedisRepositoryImpl(RedisTemplate<String, User> redisTemplate) 
	{
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init()
	{
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveInRedis(String token)
	{
		String userId = JWToken.verifyToken(token).getIssuer();
		hashOps.put(key, userId, token);
	}
	
	@Override
	public String getFromRedis(String userId) 
	{
		return hashOps.get(key, userId);
	}
}

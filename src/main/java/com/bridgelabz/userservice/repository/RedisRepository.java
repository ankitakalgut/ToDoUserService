package com.bridgelabz.userservice.repository;

import com.bridgelabz.userservice.model.User;

/***********************************************************************************
 * @author Ankita Kalgutkar
 *
 * PURPOSE:To store token in redis.
 ************************************************************************************/
public interface RedisRepository<String, User> 
{
	public void saveInRedis(String token);

	public String getFromRedis(String userId);
}
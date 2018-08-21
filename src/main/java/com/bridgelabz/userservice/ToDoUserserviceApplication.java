package com.bridgelabz.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*************************************************************************************
 * @author Ankita Kalgutkar
 *
 * Purpose:Enable Eureka server
 *************************************************************************************/

@SpringBootApplication
@EnableEurekaClient
public class ToDoUserserviceApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ToDoUserserviceApplication.class, args);
	}
}

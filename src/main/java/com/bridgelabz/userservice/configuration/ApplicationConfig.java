package com.bridgelabz.userservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.bridgelabz.userservice.utility.JWToken;
import com.bridgelabz.userservice.utility.MailSender;

/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:To map to particular bean
 ************************************************************************************/
@Configuration
public class ApplicationConfig 
{
	@Bean
	public ModelMapper modelMapper() 
	{
		return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MailSender email()
	{
		return new MailSender();
	}
	
	@Bean
	public JWToken jwToken() 
	{
		return new JWToken();
	}	
}

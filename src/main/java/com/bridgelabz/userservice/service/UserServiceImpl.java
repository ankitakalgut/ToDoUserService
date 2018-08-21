package com.bridgelabz.userservice.service;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.userservice.model.Mailmodel;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserLoginDTO;
import com.bridgelabz.userservice.model.UserRegistrationDTO;
import com.bridgelabz.userservice.rabbitmq.Producer;
import com.bridgelabz.userservice.repository.UserRepository;
import com.bridgelabz.userservice.utility.JWToken;
import com.bridgelabz.userservice.utility.Messages;
import com.bridgelabz.userservice.utility.RestPrecondition;
import com.bridgelabz.userservice.utility.UserExceptionHandler;
import io.jsonwebtoken.Claims;

/*************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Methods for user service implementation..
 **************************************************************************************/
@Service
public class UserServiceImpl implements UserService 
{
	@Autowired
	Messages messages;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	JWToken jwToken;
	
	@Autowired
	Environment environment;
	
	@Autowired
	Mailmodel model;
	
	@Autowired
	Producer producer;
	
	/**
	 * purpose:register
	 * @param userRegistrationDTO
	 * 
	 **/
	@Override
	public void userRegister(UserRegistrationDTO userRegistrationDTO) 
	{
		User user = userRepository.findByEmail(userRegistrationDTO.getEmail());
		RestPrecondition.checkNull(user,messages.get("122"));
		user = modelMapper.map(userRegistrationDTO, User.class);
		user.setVerified(false);
		user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
		userRepository.save(user);
		String token = jwToken.createJWT(user);
		model.setTo(user.getEmail());
		model.setSubject(messages.get("101"));
		model.setText(messages.get("102") + token);	
		producer.produceMail(model);
	}
	
	/**
	 * purpose:login to account
	 * @param userlogin
	 * 
	 **/
	@Override
	public void userLogin(UserLoginDTO userlogin)
	{
		User user = userRepository.findByEmail(userlogin.getEmail());
		RestPrecondition.checkNotNull(user,messages.get("108"));	
		if (user.isVerified())
		{
			if (!bCryptPasswordEncoder.matches(userlogin.getPassword(),user.getPassword()))
			throw new UserExceptionHandler(messages.get("103"));
		} 	
		else 
		{
			throw new UserExceptionHandler(messages.get("104"));
		}
		String token=jwToken.createJWT(user);
		System.out.println("token "+token);
	}

	/**
	 * purpose:activate account
	 * @param token
	 * 
	 **/
	@Override
	public void activate(String token)  
	{
		Claims claims = JWToken.verifyToken(token);
		User user = userRepository.findByEmail(claims.getSubject());
		RestPrecondition.checkNotNull(user,messages.get("105"));
		if (user.isVerified())
			throw new UserExceptionHandler(messages.get("106"));
		user.setVerified(true);
		try 
		{
			userRepository.save(user);
		} 
		catch (DataIntegrityViolationException | ConstraintViolationException e)
		{
			throw new UserExceptionHandler(messages.get("107"));
		}
	}
	
	/**
	 * purpose:forgot Password
	 * @param email
	 * 
	 **/
	@Override
	public void forgotPassword(String email) 
	{
		User user = userRepository.findByEmail(email);
		RestPrecondition.checkNotNull(user,messages.get("108"));
		String token = jwToken.createJWT(user);
		model.setTo(email);
		model.setSubject( messages.get("109"));
		model.setText(messages.get("110") + token);	
		producer.produceMail(model);
	}
	
	/**
	 * purpose:Set new Password
	 * @param token,password
	 * 
	 **/
	
	@Override
	public void newPassword(String token, String pass)
	{
		Claims claims = JWToken.verifyToken(token);
		User user = userRepository.findByEmail(claims.getSubject());
		RestPrecondition.checkNotNull(user,messages.get("111"));
		user.setPassword(bCryptPasswordEncoder.encode(pass));
		try 
		{
			userRepository.save(user);
		} 
		catch (DataIntegrityViolationException | ConstraintViolationException e)
		{
			throw new UserExceptionHandler(messages.get("112"));
		}
	}

	/**
	 * @return list of users
	 */
	public List<User> getUsers()
	{
		List<User> Users=userRepository.findAll();
		return Users;
	}
}
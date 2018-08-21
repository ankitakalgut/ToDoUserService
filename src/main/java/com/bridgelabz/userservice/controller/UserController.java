package com.bridgelabz.userservice.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserLoginDTO;
import com.bridgelabz.userservice.model.UserRegistrationDTO;
import com.bridgelabz.userservice.repository.RedisRepository;
import com.bridgelabz.userservice.service.UserServiceImpl;
import com.bridgelabz.userservice.utility.Response;

/******************************************************************************************
 * @author Ankita Kalgutkar
 *
 *
 *
 *Purpose:Controller class
 *****************************************************************************************/

@RestController
@RequestMapping(value="/user")
public class UserController 
{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	RedisRepository<String, User> redisRepository;

	/**
	 * @param userRegistrationDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	private ResponseEntity<Response> register(@RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception 
	{
		userService.userRegister(userRegistrationDTO);
		logger.info(userRegistrationDTO.getFirstName()+"Registartion is successful");
		return new ResponseEntity<>(new Response("Registration Success", HttpStatus.CREATED), HttpStatus.OK);
	}

	/**
	 * @param userlogin
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> logins(@RequestBody UserLoginDTO userlogin)
	{
		userService.userLogin(userlogin);
		logger.info("User logged in successfully");
		return new ResponseEntity<>(new Response("Login Success", HttpStatus.OK), HttpStatus.OK);
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseEntity<Response> activation(HttpServletRequest req) 
	{
		String token=req.getHeader("Token");
		logger.info("Thankyou for activating account");
		System.out.println("Tokens demo "+token);
		userService.activate(token);
		redisRepository.saveInRedis(token);
		return new ResponseEntity<>(new Response("Account Activated", HttpStatus.OK), HttpStatus.OK);
	}
	
	/**
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/forgotpassword/{email}", method = RequestMethod.GET)
	public ResponseEntity<Response> forgetpassword(@PathVariable String email)
	{
		userService.forgotPassword(email);
		return new ResponseEntity<>(new Response("Password Reset Link Sent", HttpStatus.OK), HttpStatus.OK);
	}

	/**
	 * @param userlogin
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<Response> newPassword(@RequestBody UserLoginDTO userlogin,HttpServletRequest req)
	{
		String token=req.getHeader("Token");
		String password=userlogin.getPassword();
		userService.newPassword(token, password);
		logger.info("Password reset successful");
		return new ResponseEntity<>(new Response("Password Changed Succesfully", HttpStatus.OK), HttpStatus.OK);
	}
	
	/**
	 * @return list of users
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public  List<User>  getAllUsers()
	{
		List<User> list=userService.getUsers();
		logger.info("List of all users"+list);
		return list;
	}	
}

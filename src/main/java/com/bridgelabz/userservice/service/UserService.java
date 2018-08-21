package com.bridgelabz.userservice.service;

import java.util.List;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.model.UserLoginDTO;
import com.bridgelabz.userservice.model.UserRegistrationDTO;

/***************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *Purpose:Method declarations 
 ****************************************************************************/
public interface UserService
{
	public void userRegister(UserRegistrationDTO userRegistrationDTO) ;

	public void activate(String token);

	public void forgotPassword(String email);

	public void newPassword(String token, String pass);

	public void userLogin(UserLoginDTO userlogin);	
	
	public List<User> getUsers();
}

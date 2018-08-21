package com.bridgelabz.userservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*******************************************************************************
 * @author Ankita Kalgutkar
 *
 * UsereLoginDTO entity class
 ********************************************************************************/
public class UserLoginDTO 
{
	
	@NotNull(message = "Field should not be left empty")
	@NotBlank(message = "Field should not be left empty")
	@Email(message = "Invalid email id")
	private String email;

	@NotNull(message = "Field should not be left empty")
	@NotBlank(message = "Field should not be left empty")
	private String password;

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
}

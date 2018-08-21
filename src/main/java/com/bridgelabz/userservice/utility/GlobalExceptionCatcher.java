package com.bridgelabz.userservice.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *
 *PURPOSE:Exception class to catch global exceptions.
 *******************************************************************************/
@ControllerAdvice
public class GlobalExceptionCatcher 
{
	@ExceptionHandler(value = UserExceptionHandler.class)
	public ResponseEntity<Response> registerException(Exception exception) 
	{
		Response response = new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Response> notValidArgument(RuntimeException exception) 
	{
		Response response = new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@ExceptionHandler(value = JWTException.class)
	public ResponseEntity<Response> jwt(Exception exception) 
	{
		Response response = new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
}

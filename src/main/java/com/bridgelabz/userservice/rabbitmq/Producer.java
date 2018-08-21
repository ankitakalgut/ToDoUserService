package com.bridgelabz.userservice.rabbitmq;

import com.bridgelabz.userservice.model.Mailmodel;

/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *purpose:Create RabbitMq Producer:
 ***********************************************************************************/

public interface Producer
{
	void produceMail(Mailmodel mail);
}
package com.bridgelabz.userservice.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bridgelabz.userservice.model.Mailmodel;
 
/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *Purpose:Producer to produce the mail
 ***********************************************************************************/
@Service
public  class ProducerImplementation implements Producer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerImplementation.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;
	
	@Override
	public void produceMail(Mailmodel model)
	{
		amqpTemplate.convertAndSend(exchange, routingKey,model);
		//System.out.println("Send msg = " + model);
		LOGGER.info("send msg="+model);
	}
}
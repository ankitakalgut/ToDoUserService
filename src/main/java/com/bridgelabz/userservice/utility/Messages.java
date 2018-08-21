package com.bridgelabz.userservice.utility;

import java.util.Locale;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/******************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 * Purpose:Message Accessor to access the messages from application.properties
 * 
 ******************************************************************************/
@Component
public class Messages 
{

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init()
    {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String get(String code) 
    {
        return accessor.getMessage(code);
    }
}
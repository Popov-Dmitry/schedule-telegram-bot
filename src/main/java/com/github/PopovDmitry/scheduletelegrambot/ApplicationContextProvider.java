package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextProvider{

    private static ApplicationContext applicationContext;

    @Autowired
    public ApplicationContextProvider(ApplicationContext context) { applicationContext = context; }

    public static ApplicationContext getApplicationContext() { return applicationContext; }
}

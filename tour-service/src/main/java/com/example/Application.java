package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.tourservice.SpringBeans;
import com.example.tourservice.utilities.TravelAgentService;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringBeans.class);
        TravelAgentService agentService = context.getBean(TravelAgentService.class);

        agentService.displayTours();
    }
}
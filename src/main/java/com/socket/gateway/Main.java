package com.socket.gateway;

import com.socket.gateway.server.GatewayServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
   static void main(String[] args){
       ApplicationContext context =
               new ClassPathXmlApplicationContext("applicationContext.xml");

       GatewayServer gatewayServer=context.getBean(GatewayServer.class);
       gatewayServer.startServer();

   }
    }

package com.socket.gateway.service;

import com.socket.gateway.dto.SalesRequestDTO;

public class MessageService {

    public MessageService() {
    }

    public SalesRequestDTO getSalesRequestDTO(){
        SalesRequestDTO salesRequestDTO=new SalesRequestDTO();
        return salesRequestDTO;
    }
}

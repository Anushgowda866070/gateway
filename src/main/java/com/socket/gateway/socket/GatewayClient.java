package com.socket.gateway.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.model.SalesPostResponse;
import com.socket.gateway.service.MessageService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GatewayClient {

    private MessageService messageService;

    public GatewayClient(MessageService messageService) {
        this.messageService = messageService;
    }

    public String sendRequest(SalesRequestDTO request) {

        String jsonResponse=null;

        try {

            Socket socket = new Socket("localhost", 5000);

            System.out.println("Connected to Endpoint");

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonRequest = objectMapper.writeValueAsString(request);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);

            writer.println(jsonRequest);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            jsonResponse = reader.readLine();

            SalesPostResponse response = objectMapper.readValue(jsonResponse, SalesPostResponse.class);

            System.out.println("Transaction Id : " + response.getTransactionId());

            System.out.println("Response Code : " + response.getResponseCode());

            System.out.println("Response Message : " + response.getResponseMessage());

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }
}
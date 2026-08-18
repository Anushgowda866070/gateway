package com.socket.gateway.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.gateway.schemeresponse.SchemeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GatewayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayClient.class);

    public SchemeResponse sendRequest(Object request) {

        String jsonResponse=null;
        SchemeResponse schemeResponse=null;

        try {

            Socket socket = new Socket("localhost", 5000);

            LOGGER.info("Connected to Endpoint");

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonRequest = objectMapper.writeValueAsString(request);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);

            writer.println(jsonRequest);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            jsonResponse = reader.readLine();

            LOGGER.info("RECEIVED =[ {} ]", jsonResponse);

            schemeResponse = objectMapper.readValue(jsonResponse, SchemeResponse.class);

            logSchemeResponse(schemeResponse);

            socket.close();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return schemeResponse;
    }

    private void logSchemeResponse(SchemeResponse schemeResponse) {
        LOGGER.info("Transaction Id : {} and Response Code: {} and Response Message: {}", schemeResponse.getTransactionId(),
                schemeResponse.getResponseCode(), schemeResponse.getResponseMessage());
    }
}
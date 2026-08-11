package com.socket.gateway.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.transaction.TransactionClass;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;

public class GatewayServer {

    private final TransactionClass transactionClass;

    public GatewayServer(TransactionClass transactionClass) {
        this.transactionClass = transactionClass;
    }

    private static final Logger LOGGER =LoggerFactory.getLogger(GatewayServer.class);

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            LOGGER.info("Gateway Server Started on Port 4000");

            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("APP Connected");
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                String jsonRequest = reader.readLine();
                LOGGER.info("Request :{} ", jsonRequest);

                ObjectMapper objectMapper=new ObjectMapper();
                SalesRequestDTO dto=objectMapper.readValue(jsonRequest,SalesRequestDTO.class);

                String response=null;

                    switch (dto.getTransactionType()) {
                        case SALE:
                            response=transactionClass.processSale(dto);
                            break;

                        case REFUND:
                            response=transactionClass.processRefund(dto);
                            break;

                        case VERIFY:
                            response=transactionClass.processVerify(dto);
                            break;

                        case VOID:
                            response=transactionClass.processVoid(dto);
                            break;

                        default:
                            response = ("Invalid Transaction Type");
                            break;
                    }

                PrintWriter writer =
                        new PrintWriter(socket.getOutputStream(), true);
                writer.println(response);
                socket.close();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
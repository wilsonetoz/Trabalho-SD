package com.exemplo;

import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        String url = "http://localhost:8080/hello";
        System.out.println("Publicando servico em: " + url);

        // Publica o serviço SOAP
        Endpoint.publish(url, new HelloServiceImpl());

        System.out.println("Servico Web SOAP pronto!");
    }
}
package com.exemplo;

import jakarta.jws.WebService;

@WebService(endpointInterface = "com.exemplo.HelloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Olá, " + name + "! Bem-vindo ao Web Service SOAP.";
    }
}
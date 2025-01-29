package com.example;
import java.io.*;
import java.net.Socket;
public class Client {
public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 5000);
    BufferedReader in = new BufferedReader(new
    InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
        // Envia a chamada do método para o servidor
        String request = "sayHello:Usuário";
        out.println(request);
        System.out.println("Requisição enviada: " + request);
        // Recebe a resposta do servidor
        String response = in.readLine();
        System.out.println("Resposta do servidor: " + response);
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
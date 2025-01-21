import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexoes na porta 12345...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    StringBuilder dadosRecebidos = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (line.equals("EOF"))
                            break;
                        System.out.println(line);
                        dadosRecebidos.append(line).append("\n");
                    }
                    writer.println("Dados recebidos com sucesso!\n" + dadosRecebidos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

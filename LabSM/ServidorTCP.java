import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorTCP {
    private static final String FILE_NAME = "funcionarios_recebidos.dat";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexoes na porta 12345...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    receberArquivo(clientSocket.getInputStream());
                    List<Funcionario> funcionarios = lerFuncionariosDoArquivo();

                    System.out.println("Funcionarios recebidos:");
                    for (Funcionario f : funcionarios) {
                        System.out.println(
                                "Nome: " + f.getNome() + ", Cargo: " + f.getCargo() + ", Escala: " + f.obterEscala());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receberArquivo(InputStream inputStream) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("Arquivo recebido e salvo como " + FILE_NAME);
        }
    }

    private static List<Funcionario> lerFuncionariosDoArquivo() throws IOException {
        try (FuncionariosInputStream fis = new FuncionariosInputStream(new FileInputStream(FILE_NAME))) {
            return fis.readFuncionarios();
        }
    }
}

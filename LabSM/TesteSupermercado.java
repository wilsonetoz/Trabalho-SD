import java.io.*;
import java.net.Socket;

public class TesteSupermercado {
    public static void main(String[] args) {

        try {
            Supermercado supermercado = new Supermercado("Ponto Certo");

            Funcionario vendedor = new Funcionario("Chico", "Vendedor");
            vendedor.definirEscala("08:00 - 16:00");

            Funcionario caixa = new Funcionario("Bilu", "Caixa");
            caixa.definirEscala("10:00 - 18:00");

            Funcionario gerente = new Funcionario("Wilson", "Gerente");
            gerente.definirEscala("14:00 - 22:00");

            supermercado.adicionarFuncionario(vendedor);
            supermercado.adicionarFuncionario(caixa);
            supermercado.adicionarFuncionario(gerente);

            int numFuncionarios = supermercado.getFuncionarios().size();
            StringBuilder output = new StringBuilder();
            output.append("Numero de funcionarios: ").append(numFuncionarios).append("\n");

            for (Funcionario f : supermercado.getFuncionarios()) {
                int bytesNome = f.getNome().getBytes().length;
                int bytesCargo = f.getCargo().getBytes().length;
                int bytesEscala = f.obterEscala().getBytes().length;

                int totalBytes = bytesNome + bytesCargo + bytesEscala;
                output.append("Funcionario: ").append(f.getNome()).append("\n");
                output.append("  - Nome: ").append(bytesNome).append(" bytes\n");
                output.append("  - Cargo: ").append(bytesCargo).append(" bytes\n");
                output.append("  - Escala: ").append(bytesEscala).append(" bytes\n");
                output.append("  - Total: ").append(totalBytes).append(" bytes\n");
            }

            // Enviar para o servidor via TCP
            try (Socket socket = new Socket("127.0.0.1", 12345);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                writer.println(output.toString()); // Envia os dados
                writer.println("EOF"); // Envia um marcador de fim de transmissão para o servidor

                // Lê a resposta do servidor
                System.out.println("\nResposta do servidor:");
                String response;
                while ((response = reader.readLine()) != null) {
                    System.out.println(response);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

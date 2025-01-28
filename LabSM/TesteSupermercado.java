import java.io.*;
import java.net.Socket;
import java.util.List;

public class TesteSupermercado {
    private static final String FILE_NAME = "funcionarios.dat";

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

            salvarFuncionarios(supermercado.getFuncionarios());
            exibirInformacoesFuncionarios(supermercado.getFuncionarios());

            enviarArquivoParaServidor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salvarFuncionarios(List<Funcionario> funcionarios) throws IOException {
        try (FuncionariosOutputStream fos = new FuncionariosOutputStream(new FileOutputStream(FILE_NAME))) {
            fos.writeFuncionarios(funcionarios);
            System.out.println("Funcionarios salvos no arquivo " + FILE_NAME);
        }
    }

    private static void exibirInformacoesFuncionarios(List<Funcionario> funcionarios) throws IOException {
        for (Funcionario f : funcionarios) {
            int bytesCargo = f.getCargo().getBytes().length;
            int bytesEscala = f.obterEscala().getBytes().length;
            int totalBytes = bytesCargo + bytesEscala;

            System.out.println("Funcionario: " + f.getNome());
            System.out.println("  - Cargo: " + bytesCargo + " bytes");
            System.out.println("  - Escala: " + bytesEscala + " bytes");
            System.out.println("  - Total: " + totalBytes + " bytes");
        }//Se Quiser Ler o arquivo
  //      try (FuncionariosInputStream fis = new FuncionariosInputStream(new FileInputStream("funcionarios_recebidos.dat"))) {
  //      List<Funcionario> Lerfuncionarios = fis.readFuncionarios();
  //      for (Funcionario f : Lerfuncionarios) {
  //          System.out.println("Nome: " + f.getNome());
  //          System.out.println("Cargo: " + f.getCargo());
  //          System.out.println("Escala: " + f.obterEscala());
  //      }
  //      }
    }

    private static void enviarArquivoParaServidor() throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 12345);
                OutputStream os = socket.getOutputStream();
                FileInputStream fis = new FileInputStream(FILE_NAME)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            System.out.println("Arquivo enviado para o servidor.");
        }
    }
}

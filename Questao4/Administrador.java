import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Administrador {
    private static final int PORTA_TCP = 12345;
    private static final String MULTICAST_IP = "230.0.0.1";
    private static final int PORTA_UDP = 12346;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma acao:");
            System.out.println("1. Enviar nota informativa");
            System.out.println("2. Adicionar candidato");
            System.out.println("3. Remover candidato");
            System.out.println("4. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite a mensagem para enviar aos eleitores:");
                    String mensagem = scanner.nextLine();
                    enviarNotaInformativa(mensagem);
                    break;
                case 2:
                    System.out.println("Digite o nome do candidato a ser adicionado:");
                    String candidatoAdicionado = scanner.nextLine();
                    adicionarCandidato(candidatoAdicionado);
                    break;
                case 3:
                    System.out.println("Digite o nome do candidato a ser removido:");
                    String candidatoRemovido = scanner.nextLine();
                    removerCandidato(candidatoRemovido);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void enviarNotaInformativa(String mensagem) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress grupo = InetAddress.getByName(MULTICAST_IP);
            byte[] buffer = mensagem.getBytes();

            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, grupo, PORTA_UDP);
            socket.send(pacote);
            System.out.println("Mensagem enviada: " + mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void adicionarCandidato(String nome) {
        try (Socket socket = new Socket("localhost", PORTA_TCP);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ADMIN");
            out.println("ADICIONAR_CANDIDATO " + nome);
            String resposta = in.readLine();
            System.out.println(resposta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removerCandidato(String nome) {
        try (Socket socket = new Socket("localhost", PORTA_TCP);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ADMIN");
            out.println("REMOVER_CANDIDATO " + nome);
            String resposta = in.readLine();
            System.out.println(resposta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

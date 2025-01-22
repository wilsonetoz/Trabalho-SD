import java.io.*;
import java.net.*;

public class Eleitor {
    private static final String SERVIDOR_IP = "localhost";
    private static final int PORTA_TCP = 12345;
    private static final String MULTICAST_IP = "230.0.0.1";
    private static final int PORTA_UDP = 12346;

    public static void main(String[] args) {
        new Thread(Eleitor::iniciarReceptorMulticast).start();

        try (Socket socket = new Socket(SERVIDOR_IP, PORTA_TCP);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            out.println("ELEITOR");
            System.out.println(in.readLine());
            String nomeEleitor = userInput.readLine();
            out.println(nomeEleitor);

            String resposta = in.readLine();
            System.out.println(resposta);

            if (resposta.equals("Você ja votou!")) {
                return;
            }
            System.out.println(in.readLine());
            String voto = userInput.readLine();
            out.println(voto);

            String resultadoVoto = in.readLine();
            System.out.println(resultadoVoto);

        } catch (IOException e) {
            System.err.println("Erro na comunicacao com o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void iniciarReceptorMulticast() {
        try (MulticastSocket socket = new MulticastSocket(PORTA_UDP)) {
            InetAddress grupo = InetAddress.getByName(MULTICAST_IP);
            socket.joinGroup(grupo);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);
                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                System.out.println("Nota recebida: " + mensagem);
            }
        } catch (IOException e) {
            System.err.println("Erro ao receber multicast: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServidorVotacao {
    private static final int PORTA_TCP = 12345;
    private static final String MULTICAST_IP = "230.0.0.1";
    private static final int PORTA_UDP = 12346;
    private static final int DURACAO_VOTACAO = 180;
    private static final Map<String, Integer> candidatos = new ConcurrentHashMap<>();
    private static final Set<PrintWriter> eleitoresConectados = Collections.synchronizedSet(new HashSet<>());
    private static boolean votacaoAberta = true;

    public static void main(String[] args) {
        System.out.println("Servidor de votação iniciado...");
        candidatos.put("Wilson", 0);
        candidatos.put("Eduarda", 0);
        candidatos.put("Chico", 0);

        new Thread(ServidorVotacao::iniciarReceptorMulticast).start();

        agendarEncerramentoVotacao();

        try (ServerSocket servidorSocket = new ServerSocket(PORTA_TCP)) {
            while (true) {
                Socket socket = servidorSocket.accept();
                new Thread(new ClienteHandler(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void agendarEncerramentoVotacao() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            votacaoAberta = false;
            anunciarVencedor();
        }, DURACAO_VOTACAO, TimeUnit.SECONDS);
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
        }
    }

    private static class ClienteHandler implements Runnable {
        private final Socket socket;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String tipoUsuario = in.readLine();

                if ("ADMIN".equals(tipoUsuario)) {
                    processarComandosAdmin(in, out);
                } else if ("ELEITOR".equals(tipoUsuario)) {
                    processarVotacao(in, out);
                } else {
                    out.println("Erro: Tipo de usuario desconhecido.");
                }

            } catch (IOException e) {
                System.out.println("Erro de comunicacao com o cliente: " + e.getMessage());
            }
        }

        private static void processarComandosAdmin(BufferedReader in, PrintWriter out) {
            try {
                String comando = in.readLine();

                if (comando.startsWith("ADICIONAR_CANDIDATO")) {
                    String nomeCandidato = comando.substring("ADICIONAR_CANDIDATO ".length()).trim();
                    if (nomeCandidato.isEmpty()) {
                        out.println("Erro: Nome do candidato não pode ser vazio.");
                    } else {
                        candidatos.put(nomeCandidato, 0);
                        out.println("Candidato " + nomeCandidato + " adicionado.");
                        System.out.println("Candidato " + nomeCandidato + " removido.");
                        enviarListaCandidatosParaEleitores();
                    }
                } else if (comando.startsWith("REMOVER_CANDIDATO")) {
                    String nomeCandidato = comando.substring("REMOVER_CANDIDATO ".length()).trim();
                    if (candidatos.containsKey(nomeCandidato)) {
                        candidatos.remove(nomeCandidato);
                        out.println("Candidato " + nomeCandidato + " removido.");
                        System.out.println("Candidato " + nomeCandidato + " removido.");
                        enviarListaCandidatosParaEleitores();
                    } else {
                        out.println("Erro: Candidato não encontrado.");
                    }
                } else {
                    out.println("Comando inválido.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void enviarListaCandidatosParaEleitores() {
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress grupo = InetAddress.getByName(MULTICAST_IP);
                String mensagem = "Candidatos atualizados: " + candidatos.keySet();
                byte[] buffer = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, grupo, PORTA_UDP);
                socket.send(pacote);
            } catch (IOException e) {
                System.err.println("Erro ao enviar lista de candidatos via multicast: " + e.getMessage());
            }
        }

        private static final Set<String> eleitoresAutenticados = Collections.synchronizedSet(new HashSet<>());

        private static void processarVotacao(BufferedReader in, PrintWriter out) {
            try {
                out.println("Digite seu nome de eleitor:");
                String eleitor = in.readLine();

                if (eleitor == null || eleitor.trim().isEmpty()) {
                    out.println("Erro: Nome inválido.");
                    return;
                }
                synchronized (eleitoresAutenticados) {
                    if (eleitoresAutenticados.contains(eleitor)) {
                        out.println("Erro: Você já votou!");
                        return;
                    } else {
                        eleitoresAutenticados.add(eleitor);
                        out.println("Autenticação bem-sucedida. Você pode votar.");
                    }
                }
                if (!votacaoAberta) {
                    out.println("A votação já foi encerrada.");
                    return;
                }

                out.println("Candidatos disponíveis: " + candidatos.keySet());
                String voto = in.readLine();
                if (voto != null && candidatos.containsKey(voto)) {
                    candidatos.put(voto, candidatos.get(voto) + 1);
                    out.println("Voto registrado para " + voto);
                    System.out.println("Voto para " + voto + " de " + eleitor);
                } else {
                    out.println("Candidato inválido ou votação encerrada.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void anunciarVencedor() {

        if (candidatos.isEmpty()) {
            System.out.println("Nenhum candidato registrado.");
            return;
        }
        String vencedor = Collections.max(candidatos.entrySet(), Map.Entry.comparingByValue()).getKey();
        int votos = candidatos.get(vencedor);

        String resultado = " VOTAÇÃO ENCERRADA! O vencedor é: " + vencedor + " com " + votos + " votos!";
        System.out.println(resultado);

        synchronized (eleitoresConectados) {
            for (PrintWriter eleitor : eleitoresConectados) {
                eleitor.println(resultado);
            }
        }
    }
}

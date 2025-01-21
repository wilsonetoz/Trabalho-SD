import java.io.*;
import java.util.List;

public class FuncionariosOutputStream implements AutoCloseable {
    private DataOutputStream outStream;

    // Construtor
    public FuncionariosOutputStream(OutputStream outputStream) {
        this.outStream = new DataOutputStream(outputStream);
    }

    // Método para gravar a lista de funcionários
    public void writeFuncionarios(List<Funcionario> funcionarios) throws IOException {
        outStream.writeInt(funcionarios.size()); // Escreve o número de funcionários

        // Escreve os dados de cada funcionário
        for (Funcionario f : funcionarios) {
            outStream.writeUTF(f.getNome()); // Nome
            outStream.writeUTF(f.getCargo()); // Cargo
            outStream.writeUTF(f.obterEscala()); // Escala
        }
    }

    @Override
    public void close() throws IOException {
        outStream.close();
    }
}

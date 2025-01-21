import java.io.*;
import java.util.List;

public class FuncionariosOutputStream implements AutoCloseable {
    private DataOutputStream outStream;

    public FuncionariosOutputStream(OutputStream outputStream) {
        this.outStream = new DataOutputStream(outputStream);
    }

    public void writeFuncionarios(List<Funcionario> funcionarios) throws IOException {
        outStream.writeInt(funcionarios.size());
        for (Funcionario f : funcionarios) {
            outStream.writeUTF(f.getNome());
            outStream.writeUTF(f.getCargo());
            outStream.writeUTF(f.obterEscala());
        }
    }

    @Override
    public void close() throws IOException {
        outStream.close();
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosInputStream implements AutoCloseable {
    private DataInputStream inStream;

    public FuncionariosInputStream(InputStream inputStream) {
        this.inStream = new DataInputStream(inputStream);
    }

    public List<Funcionario> readFuncionarios() throws IOException {
        int numFuncionarios = inStream.readInt();
        List<Funcionario> funcionarios = new ArrayList<>();

        for (int i = 0; i < numFuncionarios; i++) {
            String nome = inStream.readUTF();
            String cargo = inStream.readUTF();
            String escala = inStream.readUTF();
            Funcionario f = new Funcionario(nome, cargo);
            f.definirEscala(escala);
            funcionarios.add(f);
        }
        return funcionarios;
    }

    @Override
    public void close() throws IOException {
        inStream.close();
    }
}

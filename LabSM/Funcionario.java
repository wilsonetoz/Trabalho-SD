import java.io.Serializable;

public class Funcionario implements Escala, Serializable {
    private String nome;
    private String cargo;
    private String horario;

    public Funcionario(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    @Override
    public void definirEscala(String horario) {
        this.horario = horario;
    }

    @Override
    public String obterEscala() {
        return this.horario;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }
}
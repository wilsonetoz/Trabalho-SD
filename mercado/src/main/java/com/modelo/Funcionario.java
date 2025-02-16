package com.modelo;

import java.io.Serializable;
import com.util.Escala;
import com.util.EscalaImpl;

public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cargo;
    private Escala escala;

    public Funcionario(String nome, String cargo, String horario) {
        this.nome = nome;
        this.cargo = cargo;
        this.escala = new EscalaImpl(horario);
    }

    public void definirEscala(String horario) {
        this.escala.definirEscala(horario);
    }

    public String obterEscala() {
        return this.escala.obterEscala();
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public Escala getEscala() {
        return escala;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", escala=" + escala.obterEscala() +
                '}';
    }
}

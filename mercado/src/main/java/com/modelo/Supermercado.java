package com.modelo;

import java.util.ArrayList;
import java.util.List;

public class Supermercado {
    private String nome;
    private List<Funcionario> funcionarios;

    public Supermercado(String nome) {
        this.nome = nome;
        this.funcionarios = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public String getNome() {
        return nome;
    }

    public void listarFuncionarios() {
        System.out.println("Funcionarios do supermercado " + nome + ":");
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome() + " - " + f.getCargo() + " (Escala: " + f.getEscala() + ")");
        }
    }
}

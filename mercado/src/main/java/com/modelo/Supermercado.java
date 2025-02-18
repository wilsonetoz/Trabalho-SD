package com.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Supermercado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private List<Funcionario> funcionarios;

    public Supermercado(String nome) {
        this.nome = nome;
        this.funcionarios = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void removerFuncionario(String nome) {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public String getNome() {
        return nome;
    }

    public void listarFuncionarios() {
        System.out.println("Funcionarios do supermercado " + nome + ":");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println(f.getNome() + " - " + f.getCargo() + " (Escala: " + f.getEscala() + ")");
            }
        }
        System.out.println("------------------------------");
    }

}

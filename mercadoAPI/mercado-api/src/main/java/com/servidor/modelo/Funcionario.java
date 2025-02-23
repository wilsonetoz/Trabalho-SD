package com.servidor.modelo;

import com.servidor.util.*;

public class Funcionario extends Pessoa {
    private String cargo;
    private double salario;
    private Escala escala;

    public Funcionario(String nome, String cargo, double salario) {
        super(nome);
        this.cargo = cargo;
        this.salario = salario;
        this.escala = new EscalaImpl(); // Inicializa a escala
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + getNome() + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", escala=" + escala.obterEscala() +
                '}';
    }
}
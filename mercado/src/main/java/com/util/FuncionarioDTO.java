package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;

public class FuncionarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cargo;
    private double salario;
    private String escala;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(String nome, String cargo, double salario, String escala) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
        this.escala = escala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static FuncionarioDTO fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, FuncionarioDTO.class);
    }

    @Override
    public String toString() {
        return "Funcionario{" + nome + ", cargo= " + cargo +
                ", salario= " + salario + ", escala= " + escala +
                '}';
    }
}

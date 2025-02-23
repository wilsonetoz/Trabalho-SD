package com.servidor.repository;

import com.servidor.modelo.Funcionario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SupermercadoRepository {
    private final List<Funcionario> funcionarios = new ArrayList<>();

    public SupermercadoRepository() {
        Funcionario wilson = new Funcionario("Wilson", "Gerente", 5000.0);
        wilson.getEscala().setEscala("Segunda-feira", "08:00 - 17:00");
        wilson.getEscala().setEscala("Terça-feira", "08:00 - 17:00");

        Funcionario chico = new Funcionario("Chico", "Vendedor", 3000.0);
        chico.getEscala().setEscala("Quarta-feira", "09:00 - 18:00");
        chico.getEscala().setEscala("Quinta-feira", "09:00 - 18:00");

        funcionarios.add(wilson);
        funcionarios.add(chico);
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Optional<Funcionario> buscarFuncionario(String nome) {
        return funcionarios.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public void removerFuncionario(String nome) {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }
}
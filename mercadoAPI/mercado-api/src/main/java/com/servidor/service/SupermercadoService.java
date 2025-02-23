package com.servidor.service;

import com.servidor.modelo.Funcionario;
import java.util.List;
import java.util.Optional;

public interface SupermercadoService {
    void adicionarFuncionario(Funcionario funcionario);

    Optional<Funcionario> buscarFuncionario(String nome);

    List<Funcionario> listarFuncionarios();

    void removerFuncionario(String nome);
}
package com.servidor.service;

import com.servidor.modelo.Funcionario;
import com.servidor.repository.SupermercadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupermercadoServiceImpl implements SupermercadoService {

    @Autowired
    private SupermercadoRepository repository;

    @Override
    public void adicionarFuncionario(Funcionario funcionario) {
        repository.adicionarFuncionario(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarFuncionario(String nome) {
        return repository.buscarFuncionario(nome);
    }

    @Override
    public List<Funcionario> listarFuncionarios() {
        return repository.listarFuncionarios();
    }

    @Override
    public void removerFuncionario(String nome) {
        repository.removerFuncionario(nome);
    }
}
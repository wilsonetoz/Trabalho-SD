package com.servidor;

import com.modelo.Funcionario;
import com.modelo.Supermercado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.remoto.SupermercadoService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SupermercadoServiceImpl extends UnicastRemoteObject implements SupermercadoService {
    private static final long serialVersionUID = 1L;
    private final Supermercado supermercado;

    public SupermercadoServiceImpl() throws RemoteException {
        super();
        this.supermercado = new Supermercado("Ponto Certo");
    }

    @Override
    public void adicionarFuncionario(Funcionario funcionario) throws RemoteException {
        supermercado.adicionarFuncionario(funcionario);
        try {
            System.out.println("Funcionario adicionado(JSON): " + funcionario.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        supermercado.listarFuncionarios();
    }

    @Override
    public List<Funcionario> listarFuncionarios() throws RemoteException {
        return supermercado.getFuncionarios();
    }

    @Override
    public Funcionario buscarFuncionario(String nome) throws RemoteException {
        return supermercado.getFuncionarios().stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removerFuncionario(String nome) throws RemoteException {
        List<Funcionario> funcionarios = supermercado.getFuncionarios();
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
        System.out.println("Funcionario demitido: " + nome);
        supermercado.listarFuncionarios();
    }
}

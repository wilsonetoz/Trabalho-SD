package com.servidor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remoto.SupermercadoService;
import com.util.FuncionarioDTO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SupermercadoServiceImpl extends UnicastRemoteObject implements SupermercadoService {
    private static final long serialVersionUID = 1L;
    private final List<FuncionarioDTO> funcionarios;

    public SupermercadoServiceImpl() throws RemoteException {
        super();
        this.funcionarios = new ArrayList<>();
    }

    @Override
    public void adicionarFuncionario(FuncionarioDTO funcionario) throws RemoteException {
        funcionarios.add(funcionario);
        try {
            System.out.println("Funcionario adicionado(JSON): " + funcionario.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        imprimirListaFuncionarios();
    }

    @Override
    public List<FuncionarioDTO> listarFuncionarios() throws RemoteException {
        return new ArrayList<>(funcionarios);
    }

    @Override
    public FuncionarioDTO buscarFuncionario(String nome) throws RemoteException {
        return funcionarios.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removerFuncionario(String nome) throws RemoteException {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
        System.out.println("Funcionario removido: " + nome);
        imprimirListaFuncionarios();
    }

    private void imprimirListaFuncionarios() {
        System.out.println("Lista de Funcionarios Atualizada:");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado.");
        } else {
            funcionarios.forEach(System.out::println);
        }
        System.out.println("------------------------------");
    }
}

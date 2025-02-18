package com.remoto;

import com.modelo.Funcionario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupermercadoService extends Remote {
    void adicionarFuncionario(Funcionario funcionario) throws RemoteException;

    List<Funcionario> listarFuncionarios() throws RemoteException;

    Funcionario buscarFuncionario(String nome) throws RemoteException;

    void removerFuncionario(String nome) throws RemoteException;
}

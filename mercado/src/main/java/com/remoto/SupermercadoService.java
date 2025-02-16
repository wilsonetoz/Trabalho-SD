package com.remoto;

import com.util.FuncionarioDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupermercadoService extends Remote {
    void adicionarFuncionario(FuncionarioDTO funcionario) throws RemoteException;

    List<FuncionarioDTO> listarFuncionarios() throws RemoteException;

    FuncionarioDTO buscarFuncionario(String nome) throws RemoteException;

    void removerFuncionario(String nome) throws RemoteException;
}

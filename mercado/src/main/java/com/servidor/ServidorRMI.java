package com.servidor;

import com.remoto.SupermercadoService;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServidorRMI {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            SupermercadoService servico = new SupermercadoServiceImpl();
            Naming.rebind("rmi://localhost/SupermercadoService", servico);
            System.out.println("Servidor RMI iniciado...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

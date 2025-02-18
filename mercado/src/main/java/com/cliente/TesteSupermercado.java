package com.cliente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remoto.SupermercadoService;
import com.modelo.Funcionario;
import java.rmi.Naming;
import java.util.List;

public class TesteSupermercado {
    public static void main(String[] args) {
        try {
            SupermercadoService supermercado = (SupermercadoService) Naming
                    .lookup("rmi://localhost/SupermercadoService");

            supermercado.adicionarFuncionario(new Funcionario("Chico", "Vendedor", 2500.00, "Manha"));
            supermercado.adicionarFuncionario(new Funcionario("Wilson", "Gerente", 4500.00, "Tarde"));
            supermercado.adicionarFuncionario(new Funcionario("Tome", "Caixa", 1800.00, "Noite"));

            List<Funcionario> funcionarios = supermercado.listarFuncionarios();
            System.out.println("Lista de Funcionarios:");
            funcionarios.forEach(System.out::println);

            Funcionario funcionario = supermercado.buscarFuncionario("Wilson");
            System.out.println("Funcionario encontrado: " + funcionario);

            String demitido = "Tome";
            supermercado.removerFuncionario(demitido);
            System.out.println("Funcionario '" + demitido + "' removido com sucesso!");

            funcionarios = supermercado.listarFuncionarios();
            System.out.println("Lista de Funcionarios Atualizada:");
            funcionarios.forEach(System.out::println);

            try {
                String json = funcionario.toJson();
                System.out.println("Funcionario em JSON: " + json);

                Funcionario funcionarioConvertido = Funcionario.fromJson(json);
                System.out.println("Funcionario reconvertido: " + funcionarioConvertido);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

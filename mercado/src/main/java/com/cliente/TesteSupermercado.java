package com.cliente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remoto.SupermercadoService;
import com.util.FuncionarioDTO;
import java.rmi.Naming;
import java.util.List;

public class TesteSupermercado {
    public static void main(String[] args) {
        try {
            SupermercadoService supermercado = (SupermercadoService) Naming
                    .lookup("rmi://localhost/SupermercadoService");

            supermercado.adicionarFuncionario(new FuncionarioDTO("Chico", "Vendedor", 2500.00, "Manhã"));
            supermercado.adicionarFuncionario(new FuncionarioDTO("Wilson", "Gerente", 4500.00, "Tarde"));
            supermercado.adicionarFuncionario(new FuncionarioDTO("Tome", "Caixa", 1800.00, "Noite"));

            List<FuncionarioDTO> funcionarios = supermercado.listarFuncionarios();
            System.out.println("Lista de Funcionarios:");
            funcionarios.forEach(System.out::println);

            FuncionarioDTO funcionario = supermercado.buscarFuncionario("Wilson");
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

                FuncionarioDTO funcionarioConvertido = FuncionarioDTO.fromJson(json);
                System.out.println("Funcionario reconvertido: " + funcionarioConvertido);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.servidor.controller;

import com.servidor.modelo.Funcionario;
import com.servidor.service.SupermercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private SupermercadoService service;

    @PostMapping
    public ResponseEntity<String> adicionarFuncionario(@RequestBody Funcionario funcionario) {
        service.adicionarFuncionario(funcionario);
        return ResponseEntity.ok("Funcionário adicionado com sucesso!");
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Funcionario> buscarFuncionario(@PathVariable String nome) {
        Optional<Funcionario> funcionario = service.buscarFuncionario(nome);
        return funcionario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = service.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<String> removerFuncionario(@PathVariable String nome) {
        service.removerFuncionario(nome);
        return ResponseEntity.ok("Funcionário removido com sucesso!");
    }
}
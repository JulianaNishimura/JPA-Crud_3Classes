package com.example.JPA_Crud_3Classes.controller;

import com.example.JPA_Crud_3Classes.dto.VendaDTO;
import com.example.JPA_Crud_3Classes.entity.Venda;
import com.example.JPA_Crud_3Classes.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<String> adicionarVenda(@RequestBody VendaDTO vendaDTO) {
        String resultado = vendaService.adicionarVenda(vendaDTO);
        if (resultado.contains("não encontrado") || resultado.contains("já possui uma venda")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        if (vendas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarVenda(@PathVariable Long id, @RequestBody VendaDTO vendaDTO) {
        String resultado = vendaService.atualizarVenda(id, vendaDTO);
        if (resultado.contains("não encontrada") || resultado.contains("não encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarVenda(@PathVariable Long id) {
        String resultado = vendaService.deletarVenda(id);
        if (resultado.contains("não encontrada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
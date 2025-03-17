package com.example.JPA_Crud_3Classes.controller;

import com.example.JPA_Crud_3Classes.dto.ProdutoDTO;
import com.example.JPA_Crud_3Classes.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<String> adicionarProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            String mensagem = produtoService.adicionarProduto(produtoDTO);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(){
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            String mensagem = produtoService.atualizarProduto(id, produtoDTO);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Produto não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id){
        try {
            String mensagem = produtoService.deletarProduto(id);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
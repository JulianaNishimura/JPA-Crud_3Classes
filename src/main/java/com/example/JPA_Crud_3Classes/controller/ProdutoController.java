package com.example.JPA_Crud_3Classes.controller;

import com.example.JPA_Crud_3Classes.dto.ProdutoDTO;
import com.example.JPA_Crud_3Classes.entity.Produto;
import com.example.JPA_Crud_3Classes.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoRepository pRep;

    @PostMapping
    public ResponseEntity<String> adicionarProduto(@RequestBody ProdutoDTO produtoDTO) {
        if(produtoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O preço deve ser positivo.");
        }

        Produto produto = new Produto();

        produto.setNome(produtoDTO.getNome());
        produto.setTipo(produtoDTO.getTipo());
        produto.setPreco(produtoDTO.getPreco());

        pRep.save(produto);
        return ResponseEntity.ok("Produto adicionado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(){
        List<Produto> produtos = pRep.findAll();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ProdutoDTO> produtoDTOs = new ArrayList<>();
        for (Produto produto : produtos) {
            ProdutoDTO produtoDTO = new ProdutoDTO(
                    produto.getNome(),
                    produto.getTipo(),
                    produto.getPreco()
            );
            produtoDTOs.add(produtoDTO);
        }
        return ResponseEntity.ok(produtoDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        if(produtoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O preço deve ser positivo.");
        }

        if (!pRep.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        Produto produto = pRep.findById(id).orElseThrow();
        produto.setNome(produtoDTO.getNome());
        produto.setTipo(produtoDTO.getTipo());
        produto.setPreco(produtoDTO.getPreco());

        pRep.save(produto);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id){
        if(!pRep.existsById(id)){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        pRep.deleteById(id);
        return ResponseEntity.ok("Produto deletado com sucesso!");
    }
}

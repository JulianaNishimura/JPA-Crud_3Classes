package com.example.JPA_Crud_3Classes.service;

import com.example.JPA_Crud_3Classes.dto.ProdutoDTO;
import com.example.JPA_Crud_3Classes.entity.Produto;
import com.example.JPA_Crud_3Classes.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository pRep;

    public String adicionarProduto(ProdutoDTO produtoDTO) {
        if(produtoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O preço deve ser positivo.");
        }

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setTipo(produtoDTO.getTipo());
        produto.setPreco(produtoDTO.getPreco());

        pRep.save(produto);
        return "Produto adicionado com sucesso!";
    }

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = pRep.findAll();
        if (produtos.isEmpty()) {
            return new ArrayList<>();
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
        return produtoDTOs;
    }

    public String atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        if(produtoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O preço deve ser positivo.");
        }

        if (!pRep.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        Produto produto = pRep.findById(id).orElseThrow();
        produto.setNome(produtoDTO.getNome());
        produto.setTipo(produtoDTO.getTipo());
        produto.setPreco(produtoDTO.getPreco());

        pRep.save(produto);
        return "Produto atualizado com sucesso!";
    }

    public String deletarProduto(Long id) {
        if(!pRep.existsById(id)){
            throw new IllegalArgumentException("Produto não encontrado");
        }

        pRep.deleteById(id);
        return "Produto deletado com sucesso!";
    }
}
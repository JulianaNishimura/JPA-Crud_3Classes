package com.example.JPA_Crud_3Classes.controller;

import com.example.JPA_Crud_3Classes.dto.VendaDTO;
import com.example.JPA_Crud_3Classes.entity.Cliente;
import com.example.JPA_Crud_3Classes.entity.Produto;
import com.example.JPA_Crud_3Classes.entity.Venda;
import com.example.JPA_Crud_3Classes.repository.ClienteRepository;
import com.example.JPA_Crud_3Classes.repository.ProdutoRepository;
import com.example.JPA_Crud_3Classes.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    private VendaRepository vRep;

    @Autowired
    private ClienteRepository cRep;

    @Autowired
    private ProdutoRepository pRep;

    @PostMapping
    public ResponseEntity<String> adicionarVenda(@RequestBody VendaDTO vendaDTO){
        Cliente cliente = cRep.findById(vendaDTO.getClienteId()).orElse(null);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        if (cliente.getVenda() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este cliente já possui uma venda.");
        }


        List<Produto> produtos = pRep.findAllById(vendaDTO.getProdutoIds());
        if(produtos.size() != vendaDTO.getProdutoIds().size()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Algum produto não foi encontrado");
        }

        Venda venda = new Venda();
        venda.setDataDaVenda(vendaDTO.getDataDaVenda());
        venda.setCliente(cliente);

        for (Produto produto : produtos) {
            produto.setVenda(venda);
        }
        venda.setProdutos(produtos);

        cliente.setVenda(venda);
        cRep.save(cliente);

        return ResponseEntity.ok("Venda adicionada com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas(){
        List<Venda> vendas = vRep.findAll();
        if(vendas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarVenda(@PathVariable Long id, @RequestBody VendaDTO vendaDTO){
        if (!vRep.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada com o id: " + id);
        }
        Venda venda = vRep.findById(id).orElseThrow(() -> new RuntimeException("Erro ao buscar a venda"));

        Cliente cliente = cRep.findById(vendaDTO.getClienteId()).orElse(null);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }


        List<Produto> produtos = pRep.findAllById(vendaDTO.getProdutoIds());
        if (produtos.size() != vendaDTO.getProdutoIds().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alguns produtos não foram encontrados.");
        }

//        assert venda != null;
        //por que intellij pediu para eu usar? - pq coloquei or else null na venda :( mudarei para throw.
        venda.setDataDaVenda(vendaDTO.getDataDaVenda());
        venda.setCliente(cliente);
        venda.setProdutos(produtos);
        vRep.save(venda);

        return ResponseEntity.ok("Venda atualizada com sucesso!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarVenda(@PathVariable Long id){
        if(!vRep.existsById(id)){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
        }

        vRep.deleteById(id);
        return ResponseEntity.ok("Venda deletada com sucesso!");
    }
}

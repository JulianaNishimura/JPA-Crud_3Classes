package com.example.JPA_Crud_3Classes.service;

import com.example.JPA_Crud_3Classes.dto.VendaDTO;
import com.example.JPA_Crud_3Classes.entity.Cliente;
import com.example.JPA_Crud_3Classes.entity.Produto;
import com.example.JPA_Crud_3Classes.entity.Venda;
import com.example.JPA_Crud_3Classes.repository.ClienteRepository;
import com.example.JPA_Crud_3Classes.repository.ProdutoRepository;
import com.example.JPA_Crud_3Classes.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vRep;

    @Autowired
    private ClienteRepository cRep;

    @Autowired
    private ProdutoRepository pRep;

    public String adicionarVenda(VendaDTO vendaDTO) {
        Cliente cliente = cRep.findById(vendaDTO.getClienteId()).orElse(null);
        if (cliente == null) {
            return "Cliente não encontrado";
        }

        if (cliente.getVenda() != null) {
            return "Este cliente já possui uma venda.";
        }

        List<Produto> produtos = pRep.findAllById(vendaDTO.getProdutoIds());
        if (produtos.size() != vendaDTO.getProdutoIds().size()) {
            return "Algum produto não foi encontrado";
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

        return "Venda adicionada com sucesso!";
    }

    public List<Venda> listarVendas() {
        return vRep.findAll();
    }

    public String atualizarVenda(Long id, VendaDTO vendaDTO) {
        Venda venda = vRep.findById(id).orElse(null);
        if (venda == null) {
            return "Venda não encontrada com o id: " + id;
        }

        Cliente cliente = cRep.findById(vendaDTO.getClienteId()).orElse(null);
        if (cliente == null) {
            return "Cliente não encontrado.";
        }

        List<Produto> produtos = pRep.findAllById(vendaDTO.getProdutoIds());
        if (produtos.size() != vendaDTO.getProdutoIds().size()) {
            return "Alguns produtos não foram encontrados.";
        }

        // Desassociar o cliente antigo, se for diferente
        Cliente clienteAntigo = venda.getCliente();
        if (clienteAntigo != null && !clienteAntigo.equals(cliente)) {
            clienteAntigo.setVenda(null);
            cRep.save(clienteAntigo);
        }

        // Atualizar a venda
        venda.setDataDaVenda(vendaDTO.getDataDaVenda());
        venda.setCliente(cliente);
        cliente.setVenda(venda); // Manter o relacionamento bidirecional

        // Atualizar os produtos sem substituir a coleção
        venda.getProdutos().clear(); // Remove os produtos antigos (orphanRemoval = true os deleta)
        venda.getProdutos().addAll(produtos); // Adiciona os novos produtos
        for (Produto produto : produtos) {
            produto.setVenda(venda); // Atualiza o lado do Produto
        }

        vRep.save(venda);
        return "Venda atualizada com sucesso!";
    }

    public String deletarVenda(Long id) {

        Venda venda = vRep.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        Cliente cliente = venda.getCliente();
        if (cliente != null) {
            cliente.setVenda(null);
            cRep.save(cliente); // Atualiza o cliente no banco para remover a referência
        }

        vRep.delete(venda);
        return "Venda deletada com sucesso!";
    }
}
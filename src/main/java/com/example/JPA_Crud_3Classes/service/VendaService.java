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
        if (!vRep.existsById(id)) {
            return "Venda não encontrada com o id: " + id;
        }
        Venda venda = vRep.findById(id).orElseThrow(() -> new RuntimeException("Erro ao buscar a venda"));

        Cliente cliente = cRep.findById(vendaDTO.getClienteId()).orElse(null);
        if (cliente == null) {
            return "Cliente não encontrado.";
        }

        List<Produto> produtos = pRep.findAllById(vendaDTO.getProdutoIds());
        if (produtos.size() != vendaDTO.getProdutoIds().size()) {
            return "Alguns produtos não foram encontrados.";
        }

//        assert venda != null;
        //por que intellij pediu para eu usar? - pq coloquei or else null na venda :( mudarei para throw.
        venda.setDataDaVenda(vendaDTO.getDataDaVenda());
        venda.setCliente(cliente);
        venda.setProdutos(produtos);
        vRep.save(venda);

        return "Venda atualizada com sucesso!";
    }

    public String deletarVenda(Long id) {
        if (!vRep.existsById(id)) {
            return "Venda não encontrada";
        }

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
package com.example.JPA_Crud_3Classes.service;

import com.example.JPA_Crud_3Classes.dto.ClienteDTO;
import com.example.JPA_Crud_3Classes.entity.Cliente;
import com.example.JPA_Crud_3Classes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository cRep;

    public String adicionarCliente(ClienteDTO clienteDTO) {
        if(cRep.existsByTelefone(clienteDTO.getTelefone())){
            throw new IllegalArgumentException("Telefone já Cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());

        cRep.save(cliente);
        return "Cliente adicionado com sucesso!";
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = cRep.findAll();
        if (clientes.isEmpty()) {
            return new ArrayList<>();
        }

        List<ClienteDTO> clienteDTOs = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = new ClienteDTO(
                    cliente.getNome(),
                    cliente.getTelefone()
            );
            clienteDTOs.add(clienteDTO);
        }
        return clienteDTOs;
    }

    public String atualizarCliente(Long id, ClienteDTO clienteDTO) {
        if(!cRep.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Cliente cliente = cRep.findById(id).orElseThrow();
        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());

        cRep.save(cliente);
        return "Cliente atualizado com sucesso!";
    }

    public String deletarCliente(Long id) {
        if(!cRep.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        cRep.deleteById(id);
        return "Cliente deletado com sucesso!";
    }
}

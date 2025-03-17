package com.example.JPA_Crud_3Classes.controller;

import com.example.JPA_Crud_3Classes.dto.ClienteDTO;
import com.example.JPA_Crud_3Classes.dto.ProdutoDTO;
import com.example.JPA_Crud_3Classes.entity.Cliente;
import com.example.JPA_Crud_3Classes.entity.Produto;
import com.example.JPA_Crud_3Classes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository cRep;

    @PostMapping
    public ResponseEntity<String> adicionarProduto(@RequestBody ClienteDTO clienteDTO) {

        if(cRep.existsByTelefone(clienteDTO.getTelefone())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Telefone já Cadastrado");
        }

        Cliente cliente = new Cliente();

        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());

        cRep.save(cliente);
        return ResponseEntity.ok("Cliente adicionado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes(){
        List<Cliente> clientes = cRep.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ClienteDTO> clienteDTOs = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = new ClienteDTO(
                    cliente.getNome(),
                    cliente.getTelefone()
            );
            clienteDTOs.add(clienteDTO);
        }
        return ResponseEntity.ok(clienteDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        if(!cRep.existsById(id)){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        Cliente cliente = cRep.findById(id).orElseThrow();
        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());

        cRep.save(cliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id){
        if(!cRep.existsById(id)){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        cRep.deleteById(id);
        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }
}

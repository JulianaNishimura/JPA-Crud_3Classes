package com.example.JPA_Crud_3Classes.repository;

import com.example.JPA_Crud_3Classes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Boolean existsByTelefone(String telefone);
}

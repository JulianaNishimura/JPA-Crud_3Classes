package com.example.JPA_Crud_3Classes.repository;

import com.example.JPA_Crud_3Classes.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}

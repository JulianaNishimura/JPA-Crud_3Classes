package com.example.JPA_Crud_3Classes.repository;

import com.example.JPA_Crud_3Classes.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

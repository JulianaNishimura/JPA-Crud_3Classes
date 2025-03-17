package com.example.JPA_Crud_3Classes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private BigDecimal preco;
    //IDENTITY: O banco de dados gerencia a chave primária com auto-incremento.
    //AUTO: O JPA escolhe a estratégia de geração com base no banco de dados (pode usar IDENTITY, SEQUENCE ou TABLE).

    @ManyToOne
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    @JsonBackReference // necessário pois estava quebrando o json no get já que estava usando a venda e n a dto dela :(
    private Venda venda;
}

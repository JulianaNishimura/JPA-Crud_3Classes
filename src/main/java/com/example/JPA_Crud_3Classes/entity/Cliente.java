package com.example.JPA_Crud_3Classes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL) //uma venda para um cliente e vice-versa
    @JoinColumn(name = "venda_id", referencedColumnName = "id") // Referencia a chave primária de Venda
    @JsonBackReference // necessário pois estava quebrando o json no get já que estava usando a venda e n a dto dela :(
    private Venda venda;

}

package com.example.JPA_Crud_3Classes.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime dataDaVenda;

    @OneToOne(mappedBy = "venda")
    @JsonManagedReference
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // necessário pois estava quebrando o json no get já que estava usando a venda e n a dto dela :(
    private List<Produto> produtos;

    // MappedBy - Isso indica que o relacionamento é bidirecional, ou seja, existe um campo na classe Produto que faz referência à Venda. O campo venda em Produto é o que mapeia a relação.
    //O cascade é usado para definir se as operações realizadas em uma entidade devem ser propagadas para as entidades relacionadas.
    //CascadeType.ALL significa que todas as operações (persistir, atualizar, excluir, etc.) realizadas na Venda serão propagadas para os produtos associados
    //orphanRemoval é uma opção que, quando configurada como true, remove os produtos órfãos.
    //Um produto órfão é um produto que estava associado a uma venda, mas, por algum motivo, perdeu a associação (isto é, foi desassociado da venda).
}

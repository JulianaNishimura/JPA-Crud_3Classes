package com.example.JPA_Crud_3Classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private ZonedDateTime dataDaVenda;
    private Long clienteId;
    private List<Long> produtoIds;
}

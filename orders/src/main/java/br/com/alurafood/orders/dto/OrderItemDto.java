package br.com.alurafood.orders.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoOrderDto {

    private Long id;
    private Integer quantidade;
    private String descricao;
}

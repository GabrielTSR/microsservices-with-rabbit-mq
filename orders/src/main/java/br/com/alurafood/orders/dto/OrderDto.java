package br.com.alurafood.orders.dto;

import br.com.alurafood.orders.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime dataHora;
    private Status status;
    private List<ItemDoOrderDto> itens = new ArrayList<>();



}

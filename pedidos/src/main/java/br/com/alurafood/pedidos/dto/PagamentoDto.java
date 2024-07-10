package br.com.alurafood.pedidos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import br.com.alurafood.pedidos.model.StatusPagamento;

@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private StatusPagamento status;
    private Long formaDePagamentoId;
    private Long pedidoId;


}

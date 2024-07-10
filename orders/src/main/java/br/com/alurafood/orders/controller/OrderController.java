package br.com.alurafood.orders.controller;

import br.com.alurafood.orders.dto.OrderDto;
import br.com.alurafood.orders.dto.StatusDto;
import br.com.alurafood.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDto> listarTodos() {
            return service.obterTodos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDto> listarPorId(@PathVariable @NotNull Long id) {
            OrderDto dto = service.obterPorId(id);

            return  ResponseEntity.ok(dto);
        }

        @GetMapping("/porta")
        public String retornaPorta(@Value("${local.server.port}") String porta){
            return String.format("Requisição respondida pela instância executando na porta %s", porta);
        }

        @PostMapping()
        public ResponseEntity<OrderDto> realizaOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
            OrderDto orderRealizado = service.criarOrder(dto);

            URI endereco = uriBuilder.path("/orders/{id}").buildAndExpand(orderRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(orderRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDto> atualizaStatus(@PathVariable Long id, @RequestBody StatusDto status){
           OrderDto dto = service.atualizaStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pago")
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
            service.aprovaPagamentoOrder(id);

            return ResponseEntity.ok().build();

        }
}

package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.http.OrderClient;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.model.Status;
import br.com.alurafood.payments.repository.PaymentRepositoy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepositoy repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient order;


    public Page<PaymentDto> obterTodos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto obterPorId(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto criarPayment(PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CRIADO);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto atualizarPayment(Long id, PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        return modelMapper.map(payment, PaymentDto.class);
    }

    public void excluirPayment(Long id) {
        repository.deleteById(id);
    }

    public void confirmarPayment(Long id){
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMADO);
        repository.save(payment.get());
        order.atualizaPayment(payment.get().getOrderId());
    }


    public void alteraStatus(Long id) {
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(payment.get());

    }
}


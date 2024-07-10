package br.com.alurafood.orders.repository;

import br.com.alurafood.orders.model.Order;
import br.com.alurafood.orders.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order p set p.status = :status where p = :order")
    void atualizaStatus(Status status, Order order);

    @Query(value = "SELECT p from Order p LEFT JOIN FETCH p.itens where p.id = :id")
    Order porIdComItens(Long id);


}

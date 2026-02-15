package br.com.rodrigobsjava.service;

import br.com.rodrigobsjava.domain.Customer;
import br.com.rodrigobsjava.domain.Order;
import br.com.rodrigobsjava.domain.OrderStatus;
import br.com.rodrigobsjava.repository.CustomerRepository;
import br.com.rodrigobsjava.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    public OrderService(OrderRepository orderRepo, CustomerRepository customerRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    @Transactional
    public Order create(UUID customerId, BigDecimal amount) {
        Customer customer = customerRepo.findByIdOptional(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Order o = Order.create(customer, amount);
        orderRepo.persist(o);
        return o;
    }

    public List<Order> list() {
        return orderRepo.listAllWithCustomer();
    }

    public Order getOrThrow(UUID id) {
        return orderRepo.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Transactional
    public Order updateStatus(UUID id, OrderStatus status) {
        Order o = getOrThrow(id);
        o.changeStatus(status);
        return o;
    }

    @Transactional
    public void delete(UUID id) {
        Order o = getOrThrow(id);
        orderRepo.delete(o);
    }

    public List<Order> byStatus(OrderStatus status) {
        return orderRepo.listByStatusWithCustomer(status);
    }

    public List<Order> byCustomer(UUID id) {
        return orderRepo.listByCustomerWithCustomer(id);
    }

}

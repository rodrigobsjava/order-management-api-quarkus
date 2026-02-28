package br.com.rodrigobsjava.repository;

import br.com.rodrigobsjava.domain.Order;
import br.com.rodrigobsjava.domain.OrderStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderRepository implements PanacheRepositoryBase<Order, UUID> {

    public List<Order> listAllWithCustomer() {
        return find("select o from Order o join fetch o.customer").list();
    }

    public List<Order> listByStatusWithCustomer(OrderStatus status) {
        return find("select o from Order o join fetch o.customer where o.status = ?1", status).list();
    }

    public List<Order> listByCustomerWithCustomer(UUID customerId) {
        return find("select o from Order o join fetch o.customer where o.customer.id = ?1",customerId).list();
    }

}

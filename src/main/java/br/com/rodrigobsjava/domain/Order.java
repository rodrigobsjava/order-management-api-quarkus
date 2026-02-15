package br.com.rodrigobsjava.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.CREATED;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Order() {
    }

    public static Order create(Customer customer, BigDecimal amount) {
        Order o = new Order();
        o.customer = customer;
        o.amount = amount;
        o.status = OrderStatus.CREATED;
        o.createdAt = Instant.now();
        return o;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
        if (status == null) status = OrderStatus.CREATED;
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void markPaid() {
        this.status = OrderStatus.PAID;
    }

    public void changeStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

}

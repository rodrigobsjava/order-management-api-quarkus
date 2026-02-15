package br.com.rodrigobsjava.repository;

import br.com.rodrigobsjava.domain.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {
    public Optional<Customer> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}

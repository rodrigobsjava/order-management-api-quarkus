package br.com.rodrigobsjava.service;

import br.com.rodrigobsjava.domain.Customer;
import br.com.rodrigobsjava.exception.ConflictException;
import br.com.rodrigobsjava.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Customer create(String name, String email) {
        if (repo.findByEmail(email).isPresent()) {
            throw new ConflictException("E-mail already in use");
        }
        Customer c = new Customer(name, email);
        repo.persist(c);
        return c;
    }


    public List<Customer> list() {
        return repo.listAll();
    }

    public Customer getOrThrow(UUID id) {
        return repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Transactional
    public Customer update(UUID id, String name, String email) {
        repo.findByEmail(email).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Email already in use");
            }
        });
        Customer c = getOrThrow(id);
        c.rename(name);
        c.changeEmail(email);
        return c;
    }

    @Transactional
    public void delete(UUID id) {
        Customer c = getOrThrow(id);
        repo.delete(c);
    }

}

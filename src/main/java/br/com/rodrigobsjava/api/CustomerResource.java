package br.com.rodrigobsjava.api;

import br.com.rodrigobsjava.domain.Customer;
import br.com.rodrigobsjava.dto.CreateCustomerRequest;
import br.com.rodrigobsjava.dto.CustomerResponse;
import br.com.rodrigobsjava.dto.UpdateCustomerRequest;
import br.com.rodrigobsjava.service.CustomerService;
import jakarta.validation.Valid;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerService service;

    public CustomerResource(CustomerService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid CreateCustomerRequest request) {
        Customer c = service.create(request.name(), request.email());
        return Response.created(URI.create("/clients/" + c.getId())).entity(toResponse(c)).build();
    }

    @GET
    public List<CustomerResponse> list() {
        return service.list().stream().map(this::toResponse).toList();
    }

    @GET
    @Path("/{id}")
    public CustomerResponse get(@PathParam("id") UUID id) {
        return toResponse(service.getOrThrow(id));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CustomerResponse update(@PathParam("id") UUID id, @Valid UpdateCustomerRequest request) {
        Customer c = service.update(id, request.name(), request.email());
        return toResponse(c);
    }

    @DELETE
    public Response delete(@PathParam("id") UUID id) {
        service.delete(id);
        return Response.noContent().build();
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(c.getId(), c.getName(), c.getEmail(), c.getCreatedAt());
    }
}

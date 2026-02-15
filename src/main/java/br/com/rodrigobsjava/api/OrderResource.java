package br.com.rodrigobsjava.api;

import br.com.rodrigobsjava.domain.Order;
import br.com.rodrigobsjava.domain.OrderStatus;
import br.com.rodrigobsjava.dto.CreateOrderRequest;
import br.com.rodrigobsjava.dto.OrderResponse;
import br.com.rodrigobsjava.dto.UpdateOrderStatusRequest;
import br.com.rodrigobsjava.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderService service;

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @POST
    public Response create(@Valid CreateOrderRequest request) {
        Order o = service.create(request.customerId(), request.amount());
        return Response.created(URI.create("/orders/" + o.getId())).entity(toResponse(o)).build();
    }

    @GET
    public List<OrderResponse> list() {
        return service.list().stream().map(this::toResponse).toList();
    }

    @GET
    @Path("/{id}")
    public OrderResponse get(@PathParam("id") UUID id) {
        return toResponse(service.getOrThrow(id));
    }

    @PATCH
    @Path("/{id}/status")
    public OrderResponse updateStatus(@PathParam("id") UUID id, @Valid UpdateOrderStatusRequest request) {
        return toResponse(service.updateStatus(id, request.status()));
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/by-status/{status}")
    public List<OrderResponse> byStatus(@PathParam("status") OrderStatus status) {
        return service.byStatus(status).stream().map(this::toResponse).toList();
    }

    @GET
    @Path("/by-customer/{customerId}")
    public List<OrderResponse> byCustomer(@PathParam("customerId") UUID customerId) {
        return service.byCustomer(customerId).stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(Order o) {
        return new OrderResponse(o.getId(), o.getCustomer().getId(), o.getStatus(), o.getAmount(), o.getCreatedAt());
    }
}

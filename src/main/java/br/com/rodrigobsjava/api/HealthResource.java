package br.com.rodrigobsjava.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    public record HealthResponse(String status) {}

    @GET
    public HealthResponse health() {
        return new HealthResponse("UP");
    }
}

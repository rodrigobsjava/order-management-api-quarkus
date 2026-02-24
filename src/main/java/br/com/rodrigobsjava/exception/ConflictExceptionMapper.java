package br.com.rodrigobsjava.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ConflictException exception) {
        ApiError body = new ApiError(
                Instant.now(),
                409,
                "Conflict",
                exception.getMessage(),
                "/" + uriInfo.getPath(),
                null
        );

        return Response.status(Response.Status.CONFLICT).entity(body).build();
    }
}

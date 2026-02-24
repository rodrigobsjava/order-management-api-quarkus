package br.com.rodrigobsjava.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Context
    private UriInfo uriInfo;

    public Response toResponse(NotFoundException exception) {
        ApiError body = new ApiError(
                Instant.now(),
                404,
                "Not Found",
                exception.getMessage(),
                "/" + uriInfo.getPath(),
                null
        );
        return Response.status(Response.Status.NOT_FOUND).entity(body).build();
    }
}

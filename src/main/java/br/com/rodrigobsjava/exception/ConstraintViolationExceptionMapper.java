package br.com.rodrigobsjava.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    private UriInfo uriInfo;


    @Override
    public Response toResponse(ConstraintViolationException e) {
        ApiError body = new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                "Validation failed",
                uriInfo.getPath()
        );

        return Response.status(Response.Status.BAD_REQUEST).entity(body).build();
    }
}

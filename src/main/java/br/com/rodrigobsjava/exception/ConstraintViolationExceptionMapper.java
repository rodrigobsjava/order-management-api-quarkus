package br.com.rodrigobsjava.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    private UriInfo uriInfo;


    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<FieldError> fieldErrors = e.getConstraintViolations().stream()
                .map(v -> new FieldError(extractLeafField(v), v.getMessage()))
                .distinct()
                .sorted(Comparator.comparing(FieldError::field, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();

        ApiError body = new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                "Validation failed",
                "/" + uriInfo.getPath(),
                fieldErrors
        );

        return Response.status(Response.Status.BAD_REQUEST).entity(body).build();
    }

    private String extractLeafField(ConstraintViolation<?> v) {
        String leaf = null;
        Path path = v.getPropertyPath();
        for (Path.Node node : path) {
            if (node.getName() != null && !node.getName().isBlank()) {
                leaf = node.getName();
            }
        }
        return (leaf != null) ? leaf : path.toString();
    }
}

package tech.wallet.application.util;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URI;

public interface ResponseUtility {

  default Response ok() {
    return Response.ok().build();
  }

  default Response ok(Object entity) {
    return Response.ok(entity).build();
  }

  default Response notFound() {
    return Response.status(Status.NOT_FOUND).build();
  }

  default Response badRequest() {
    return Response.status(Status.BAD_REQUEST).build();
  }

  default Response notFound(Object entity) {
    return Response.status(Status.NOT_FOUND).entity(entity).build();
  }

  default Response noContent() {
    return Response.status(Status.NO_CONTENT).build();
  }

  default Response noContent(Object entity) {
    return Response.status(Status.NO_CONTENT).entity(entity).build();
  }

  default Response nonAuthoritative() {
    return Response.status(203).build();
  }

  default Response unauthorized() {
    return Response.status(Status.UNAUTHORIZED).build();
  }

  default Response unauthorized(Object entity) {
    return Response.status(Status.UNAUTHORIZED).build();
  }

  default Response gone() {
    return Response.status(Status.GONE).build();
  }

  default Response created() {
    return Response.status(Status.CREATED).build();
  }

  default Response created(Object entity) {
    return Response.status(Status.CREATED).entity(entity).build();
  }

  default Response created(String resourcePah) {
    return Response //
        .created( //
            URI.create(resourcePah)) //
        .build();
  }

  default Response created(String resourcePah, Object entity) {
    return Response //
        .created( //
            URI.create(resourcePah)) //
        .entity(entity) //
        .build();
  }
}

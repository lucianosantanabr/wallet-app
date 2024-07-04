package tech.wallet.application.exception.handler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {

  @Context UriInfo uriInfo;

  @Context HttpServerRequest request;

  @Inject MeterRegistry registry;

  @Override
  public Response toResponse(RuntimeException exception) {
    registry
        .counter(
            "tech.wallet.exception", //
            Tags.of(Tag.of("code", "unhandled-exception")))
        .increment();

    log.warn(
        "Falha não esperada na aplicação. message:[{}] - endpoint:[{}] - headers:[{}]",
        exception.getMessage(),
        uriInfo.getRequestUri().getPath(),
        request.headers(),
        exception);

    return Response //
        .status(412) //
        .entity(
            ErrorResponse.builder() //
                .timestamp(LocalDateTime.now()) //
                .path(uriInfo.getRequestUri().getPath()) //
                .status(412) //
                .code("unhandled-exception") //
                .message(exception.getLocalizedMessage()) //
                .build() //
            )
        .build();
  }
}

package tech.wallet.application.exception.handler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
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
public class InterruptedExceptionHandler implements ExceptionMapper<InterruptedException> {

  @Context private UriInfo uriInfo;

  @Inject MeterRegistry registry;

  @Override
  public Response toResponse(InterruptedException exception) {

    registry
        .counter(
            "tech.wallet.exception", //
            Tags.of(Tag.of("code", "interrupted-exception")))
        .increment();

    log.warn(
        "Falha no thread sleep para retentativa de processamento. error:[{}]",
        exception.getLocalizedMessage(),
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

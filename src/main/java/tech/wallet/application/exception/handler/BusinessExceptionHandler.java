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
import tech.wallet.application.exception.BusinessException;

@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

  @Context private UriInfo uriInfo;

  @Inject MeterRegistry registry;

  @Override
  public Response toResponse(BusinessException e) {

    // register metric exception business
    registry
        .counter(
            "tech.wallet.exception", //
            Tags.of(Tag.of("code", e.getCode())))
        .increment();

    return Response.status(e.getStatus())
        .entity(
            ErrorResponse.builder() //
                .timestamp(LocalDateTime.now()) //
                .path(uriInfo.getRequestUri().getPath()) //
                .status(e.getStatus()) //
                .code(e.getCode()) //
                .message(e.getFormatedMessage()) //
                .params(e.getParams()) //
                .build())
        .build();
  }
}

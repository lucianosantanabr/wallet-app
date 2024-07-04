package tech.wallet.application.exception.handler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import tech.wallet.application.exception.impl.ParameterViolationException;

@Provider
public class ConstraintViolationExceptionHandler
    implements ExceptionMapper<ConstraintViolationException> {

  @Context private UriInfo uriInfo;

  @Inject MeterRegistry registry;

  @Override
  public Response toResponse(ConstraintViolationException ex) {

    registry
        .counter(
            "tech.wallet.exception", //
            Tags.of(Tag.of("code", "constraint-violation")))
        .increment();

    Map<String, Object> params = new HashMap<>();
    for (var c : ex.getConstraintViolations()) {
      String key;
      String value;
      key =
          c.getPropertyPath().toString().isBlank()
              ? //
              c.getLeafBean().getClass().getName()
              : c.getPropertyPath().toString();

      var split = key.split("\\.");
      if (split.length > 0) {
        key = split[split.length - 1];
      } else {
        key = c.getMessage().replace(" ", ".");
      }

      boolean isDefaultMessage =
          c.getConstraintDescriptor()
              .getAttributes()
              .get("message")
              .toString()
              .startsWith("{javax");

      if (isDefaultMessage) {
        String annotatioName =
            c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();

        value =
            String.format(
                "validations.%s.%s.%s",
                c.getLeafBean().getClass().getSimpleName().toLowerCase(),
                key.toLowerCase(),
                annotatioName.toLowerCase());
      } else {
        value = c.getMessage();
      }

      params.put(key, value);
    }

    return Response //
        .status(400) //
        .entity(
            ErrorResponse.builder() //
                .timestamp(LocalDateTime.now()) //
                .path(uriInfo.getRequestUri().getPath()) //
                .status(400) //
                .code(ParameterViolationException.CODE) //
                .message(ParameterViolationException.MESSAGE) //
                .params(params) //
                .build() //
            )
        .build();
  }
}

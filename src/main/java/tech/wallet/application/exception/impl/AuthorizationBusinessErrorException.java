package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;
import tech.wallet.application.exception.Hidden;
import tech.wallet.application.exception.handler.ErrorResponse;

@Getter
@RegisterForReflection
public class AuthorizationBusinessErrorException extends BusinessException {

  public static final String CODE = "exception.wallet.restclient.exception";
  public static final Integer STATUS = 412;
  public AuthorizationBusinessErrorException(@Hidden ErrorResponse error) {
    super( //
        Objects.nonNull(error) && Objects.nonNull(error.getStatus()) ? error.getStatus() : STATUS, //
        Objects.nonNull(error) && notEmpty(error.getCode()) ? error.getCode() : CODE, //
        Objects.nonNull(error) && notEmpty(error.getMessage()) ? error.getMessage() : "no-message");

    if (Objects.nonNull(error) && notEmpty(error.getParams())) {
      error.getParams().forEach(this::param);
    }
  }

  public static boolean notEmpty(String str) {
    return Objects.nonNull(str) && !str.isBlank();
  }
  public static boolean notEmpty(Map<?, ?> map) {
    return Objects.nonNull(map) && !map.isEmpty();
  }
}

package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serial;
import tech.wallet.application.exception.BusinessException;

@RegisterForReflection
public class ParameterViolationException extends BusinessException {

  @Serial
  private static final long serialVersionUID = -2089504619748570266L;

  public static final String CODE = "exception.validations.parameterviolation";
  public static final Integer STATUS = 400;
  public static final String MESSAGE = "There was a constraint violation in request's parameters";

  public ParameterViolationException() {
    super(STATUS, CODE, MESSAGE);
  }
}

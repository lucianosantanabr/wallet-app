package tech.wallet.application.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import tech.wallet.application.exception.impl.ParameterViolationException;

public class BusinessValidator {
  private BusinessValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> void validate(Validator validator, T instance)
      throws ParameterViolationException {
    Set<ConstraintViolation<T>> violations = validator.validate(instance);

    if (violations.isEmpty()) {
      return;
    }

    ParameterViolationException be = new ParameterViolationException();

    for (ConstraintViolation<T> c : violations) {
      be.param(
          c.getPropertyPath().toString().isBlank()
              ? //
              c.getLeafBean().getClass().getName()
              : //
              c.getPropertyPath().toString(),
          c.getMessage());
    }

    throw be;
  }
}

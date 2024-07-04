package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;
@Getter
@RegisterForReflection
public class TransferNotAuthorizationException extends BusinessException {

  public static final String CODE = "exception.transfer.not-authorization";
  public static final Integer STATUS = 412;
  public static final String MESSAGE ="Authorization service not authorized this transfer";

  public TransferNotAuthorizationException() {
    super(STATUS, CODE, MESSAGE);
  }
}

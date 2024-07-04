package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;

@Getter
@RegisterForReflection
public class TransferNotAllowForWalletTypeException extends BusinessException {

  public static final String CODE = "exception.transfer.WalletType.notAllow";
  public static final Integer STATUS = 404;
  public static final String MESSAGE = "This wallet type=[type] is not allowed to transfer";
  public TransferNotAllowForWalletTypeException(String type) {
    super(STATUS, CODE, MESSAGE);
    param("type", type);
  }
}

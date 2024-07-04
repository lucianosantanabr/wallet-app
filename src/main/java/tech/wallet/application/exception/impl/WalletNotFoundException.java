package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;
@Getter
@RegisterForReflection
public class WalletNotFoundException extends BusinessException {

  public static final String CODE = "exception.wallet.notFound";
  public static final Integer STATUS = 412;
  public static final String MESSAGE = "Wallet with id=[walletId] was not found";
  public WalletNotFoundException(String walletId) {
    super(STATUS, CODE, MESSAGE);
    param("walletId", walletId);
  }
}

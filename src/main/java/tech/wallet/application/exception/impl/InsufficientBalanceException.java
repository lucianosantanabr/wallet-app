package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;

@Getter
@RegisterForReflection
public class InsufficientBalanceException extends BusinessException {

  public static final String CODE = "exception.wallet.noBalance";
  public static final Integer STATUS = 412;
  public static final String MESSAGE =
      "wallet id=[walletId] does not have a balance for the amount=[value]";

  public InsufficientBalanceException(String walletId, BigDecimal value) {
    super(STATUS, CODE, MESSAGE);
    param("walletId", walletId);
    param("value", value);
  }
}

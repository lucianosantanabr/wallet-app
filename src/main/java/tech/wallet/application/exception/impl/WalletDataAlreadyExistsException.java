package tech.wallet.application.exception.impl;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import tech.wallet.application.exception.BusinessException;

@Getter
@RegisterForReflection
public class WalletDataAlreadyExistsException extends BusinessException {

  public static final String CODE = "exception.wallet.exists-with-another-cpf_cnpj-and-email";
  public static final Integer STATUS = 412;
  public static final String MESSAGE =
      "It was not possible to create the wallet because there is a record with the same CPFCNPJ=[cpfCnpj] and Email=[email]";

  public WalletDataAlreadyExistsException(String cpfCnpj, String email) {
    super(STATUS, CODE, MESSAGE);
    param("cpfCnpj", cpfCnpj);
    param("email", email);
  }
}

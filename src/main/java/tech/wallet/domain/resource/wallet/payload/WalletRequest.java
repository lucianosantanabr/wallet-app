package tech.wallet.domain.resource.wallet.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.wallet.domain.entity.WalletEntity;
import tech.wallet.domain.enums.WalletType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequest {

  @NotBlank(message = "validation.wallet.fullName.notNullorBlank")
  private String fullName;

  @NotBlank(message = "validate.wallet.cpfcnpj.notNullorBlank")
  private String cpfCnpj;

  @NotBlank(message = "validate.wallet.email.notNullorBlank")
  private String email;

  private String password;

  @NotNull(message = "validate.wallet.type.notnull")
  private WalletType type;

  public static WalletRequest of(WalletEntity entity) {
    return WalletRequest.builder()
        .fullName(entity.getFullName()) //
        .cpfCnpj(entity.getCpfCnpj()) //
        .email(entity.getEmail()) //
        .password(entity.getPassword()) //
        .type(entity.getType()) //
        .build();
  }
}

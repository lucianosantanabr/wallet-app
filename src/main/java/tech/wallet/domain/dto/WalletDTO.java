package tech.wallet.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.wallet.domain.entity.WalletEntity;
import tech.wallet.domain.enums.WalletType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

  private String walletId;
  private String fullName;
  private String cpfCnpj;
  private String email;
  private String password;
  private LocalDateTime createAt;
  private WalletType type;

  public static WalletDTO of(WalletEntity entity) {
    return WalletDTO.builder()
        .walletId(entity.getWalletId()) //
        .fullName(entity.getFullName()) //
        .cpfCnpj(entity.getCpfCnpj()) //
        .email(entity.getEmail()) //
        .password(entity.getPassword()) //
        .type(entity.getType()) //
        .createAt(entity.getCreatedAt()) //
        .build();
  }
}

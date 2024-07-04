package tech.wallet.domain.resource.transfer.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.wallet.domain.entity.TransferEntity;
import tech.wallet.domain.entity.WalletEntity;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

  private String transferId;
  private LocalDateTime transferAt;
  private BigDecimal value;
  private WalletEntity payer;
  private WalletEntity payee;

  public static TransferResponse of(TransferEntity transfer) {
    return TransferResponse.builder()
        .transferId(transfer.getTransferId()) //
        .transferAt(transfer.getTransferAt()) //
        .value(transfer.getValue()) //
        .payer(transfer.getPayer()) //
        .payee(transfer.getPayee()) //
        .build();
  }
}

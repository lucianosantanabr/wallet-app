package tech.wallet.domain.resource.transfer.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

  @NotNull(message = "value not null")
  private BigDecimal value;

  @NotBlank(message = "payer not null")
  private String payer;

  @NotBlank(message = "payer not null")
  private String payee;
}

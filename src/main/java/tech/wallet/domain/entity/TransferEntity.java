package tech.wallet.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "transfer")
public class TransferEntity extends PanacheEntityBase {

  @Id
  @Column(name = "transfer_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  private String transferId;

  @Column(nullable = false, name = "transfer_at")
  private LocalDateTime transferAt;

  @Column(nullable = false)
  private BigDecimal value = BigDecimal.ZERO;

  @ManyToOne
  @JoinColumn(name = "wallet_sender_id", nullable = false)
  private WalletEntity payer;

  @ManyToOne
  @JoinColumn(name = "wallet_receiver_id", nullable = false)
  private WalletEntity payee;

  public TransferEntity(
      WalletEntity sender, WalletEntity receiver, BigDecimal value, LocalDateTime transferAt) {
    this.payer = sender;
    this.payee = receiver;
    this.value = value;
    this.transferAt = transferAt;
  }
}

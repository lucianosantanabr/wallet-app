package tech.wallet.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.wallet.domain.converter.WalletTypeConverter;
import tech.wallet.domain.enums.WalletType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "wallet")
@RegisterForReflection
public class WalletEntity extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "wallet_id")
  @EqualsAndHashCode.Include
  private String walletId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "full_name")
  @NotBlank(message = "validation.wallet.fullname.notBlankAndNotNull")
  private String fullName;

  @Column(name = "cpf_cnpj", unique = true)
  @NotBlank(message = "validate.wallet.cpfcnpj.notBlankAndNotNull")
  private String cpfCnpj;

  @Column(unique = true)
  @NotBlank(message = "validate.wallet.email.notBlankAndNotNull")
  private String email;

  @Column(nullable = false)
  private BigDecimal balance;

  @Column(nullable = false)
  @NotBlank(message = "validate.wallet.password.notBlankAndNotNull")
  private String password;

  @Convert(converter = WalletTypeConverter.class)
  @NotNull(message = "validate.wallet.type.notnull")
  private WalletType type;

  public WalletEntity(LocalDateTime createdAt, String fullName, String cpfCnpj, String email,
      BigDecimal balance, String password, WalletType type) {
    this.createdAt = createdAt;
    this.fullName = fullName;
    this.cpfCnpj = cpfCnpj;
    this.email = email;
    this.balance = balance;
    this.password = password;
    this.type = type;
  }

  public boolean isBalancerEqualOrGreatherThan(BigDecimal value) {
    return this.balance.doubleValue() >= value.doubleValue();
  }

  public void credit(BigDecimal value) {
    this.balance = this.balance.add(value);
  }

  public void debit(BigDecimal value) {
    this.balance = this.balance.subtract(value);
  }
}

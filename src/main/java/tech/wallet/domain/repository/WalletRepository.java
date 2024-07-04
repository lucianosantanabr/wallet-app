package tech.wallet.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;
import tech.wallet.domain.entity.WalletEntity;

@ApplicationScoped
public class WalletRepository implements PanacheRepositoryBase<WalletEntity, String> {

  public Optional<WalletEntity> findValidCpfCnpjOrEmail(String cpfCnpj, String email) {
    return find("cpfCnpj = :cpfCnpj or email = :email", Map.of("cpfCnpj", cpfCnpj, "email", email))
        .firstResultOptional();
  }
}

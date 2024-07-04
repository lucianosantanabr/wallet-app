package tech.wallet.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import tech.wallet.domain.entity.TransferEntity;

@ApplicationScoped
public class TransferRepository implements PanacheRepositoryBase<TransferEntity, String> {}

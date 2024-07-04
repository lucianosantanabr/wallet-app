package tech.wallet.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import tech.wallet.application.exception.impl.WalletDataAlreadyExistsException;
import tech.wallet.application.exception.impl.WalletNotFoundException;
import tech.wallet.domain.dto.WalletDTO;
import tech.wallet.domain.entity.WalletEntity;
import tech.wallet.domain.repository.WalletRepository;
import tech.wallet.domain.resource.wallet.payload.WalletRequest;

@ApplicationScoped
@Slf4j
public class WalletService {

  @Inject WalletRepository walletRepo;

  public List<WalletDTO> getAll() {
    return walletRepo.findAll().stream().map(WalletDTO::of).toList();
  }

  public WalletDTO getId(String id) throws WalletNotFoundException {
    return WalletDTO.of(
        walletRepo.findByIdOptional(id).orElseThrow(() -> new WalletNotFoundException(id)));
  }

  public WalletEntity findById(String id) throws WalletNotFoundException {
    return walletRepo.findByIdOptional(id).orElseThrow(() -> new WalletNotFoundException(id));
  }

  @Transactional
  public WalletDTO create(WalletRequest request) throws WalletDataAlreadyExistsException {

    var walletDb = walletRepo.findValidCpfCnpjOrEmail(request.getCpfCnpj(), request.getEmail());

    if (walletDb.isPresent()) {
      throw new WalletDataAlreadyExistsException(request.getCpfCnpj(), request.getEmail());
    }

    var entity =
        WalletEntity.builder() //
            .fullName(request.getFullName()) //
            .cpfCnpj(request.getCpfCnpj()) //
            .email(request.getEmail()) //
            .password(request.getPassword()) //
            .type(request.getType()) //
            .createdAt(LocalDateTime.now()) //
            .balance(BigDecimal.valueOf(0)) //
            .build();

    walletRepo.persistAndFlush(entity);

    return WalletDTO.of(entity);
  }
}

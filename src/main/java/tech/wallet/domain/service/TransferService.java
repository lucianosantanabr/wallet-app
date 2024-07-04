package tech.wallet.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import tech.wallet.application.exception.impl.AuthorizationBusinessErrorException;
import tech.wallet.application.exception.impl.InsufficientBalanceException;
import tech.wallet.application.exception.impl.TransferNotAllowForWalletTypeException;
import tech.wallet.application.exception.impl.TransferNotAuthorizationException;
import tech.wallet.application.exception.impl.WalletNotFoundException;
import tech.wallet.domain.entity.TransferEntity;
import tech.wallet.domain.entity.WalletEntity;
import tech.wallet.domain.enums.WalletType;
import tech.wallet.domain.repository.TransferRepository;
import tech.wallet.domain.repository.WalletRepository;
import tech.wallet.domain.resource.transfer.TransferResource;
import tech.wallet.domain.resource.transfer.payload.TransferRequest;
import tech.wallet.domain.resource.transfer.payload.TransferResponse;

@ApplicationScoped
public class TransferService {

  @Inject TransferRepository transferRepo;

  @Inject WalletService walletService;

  @Inject AuthorizationService authorizationService;

  @Inject NotificationService notificationService;

  @Transactional
  public TransferResponse transfer(TransferRequest request)
      throws WalletNotFoundException,
          TransferNotAuthorizationException,
          TransferNotAllowForWalletTypeException,
          InsufficientBalanceException,
          AuthorizationBusinessErrorException {

    var sender = walletService.findById(request.getPayer());
    var receiver = walletService.findById(request.getPayee());

    validateWallet(sender, request);

    // debit and credit
    sender.debit(request.getValue());
    sender.persist();

    receiver.credit(request.getValue());
    receiver.persist();

    var transfer = new TransferEntity(sender, receiver, request.getValue(), LocalDateTime.now());
    transferRepo.persist(transfer);

    var resp = TransferResponse.of(transfer);
    CompletableFuture.runAsync(() -> notificationService.sendNotification(resp));

    return resp;
  }

  private void validateWallet(WalletEntity sender, TransferRequest request)
      throws TransferNotAllowForWalletTypeException,
          InsufficientBalanceException,
          TransferNotAuthorizationException,
          AuthorizationBusinessErrorException {

    if (sender.getType().equals(WalletType.SHOPKEEPER)) {
      throw new TransferNotAllowForWalletTypeException(WalletType.SHOPKEEPER.getDescription());
    }

    if (!sender.isBalancerEqualOrGreatherThan(request.getValue())) {
      throw new InsufficientBalanceException(sender.getWalletId(), request.getValue());
    }

    if (Boolean.FALSE.equals(authorizationService.isAuthorized())) {
      throw new TransferNotAuthorizationException();
    }
  }


  public List<TransferResponse> listAll() {
    return transferRepo.listAll().stream().map(TransferResponse::of).toList();
  }
}

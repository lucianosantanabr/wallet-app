package tech.wallet.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.wallet.application.client.NotificationClient;
import tech.wallet.domain.resource.transfer.payload.TransferResponse;

@ApplicationScoped
@Slf4j
public class NotificationService {

  @Inject @RestClient NotificationClient client;

  public void sendNotification(TransferResponse request) {

    try {
      client.sendNotification(request);
      log.info("Notification ok!");
    } catch (Exception e) {
      log.error("Error while sending notification, {}", e.getLocalizedMessage());
    }
  }
}

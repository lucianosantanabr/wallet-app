package tech.wallet.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.wallet.application.client.AuthorizationClient;
import tech.wallet.application.exception.handler.ErrorResponse;
import tech.wallet.application.exception.impl.AuthorizationBusinessErrorException;
@Slf4j
@ApplicationScoped
public class AuthorizationService {

  @Inject @RestClient AuthorizationClient client;

  public Boolean isAuthorized() throws AuthorizationBusinessErrorException {

    try {
      log.info("solicitando autorizacao");
      var resp = client.isAuthorized();
      log.info("solicitacao {}", resp.authorized());
      return resp.authorized();

    } catch (Exception e) {
      log.info("Error na autorizacao");
      throw new AuthorizationBusinessErrorException(
          ErrorResponse.builder().message(e.getLocalizedMessage()).build());
    }
  }
}

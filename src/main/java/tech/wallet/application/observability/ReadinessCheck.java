package tech.wallet.application.observability;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import tech.wallet.application.exception.impl.AuthorizationBusinessErrorException;
import tech.wallet.domain.service.AuthorizationService;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

  @Inject AuthorizationService authorizationService;

  @Override
  public HealthCheckResponse call() {
    try {
      if (Boolean.TRUE.equals(authorizationService.isAuthorized())
          || Boolean.FALSE.equals(authorizationService.isAuthorized())) {
        return HealthCheckResponse.up("APP is active");
      }
    } catch (AuthorizationBusinessErrorException e) {
      return HealthCheckResponse.down("APP is not active");
    }
    return HealthCheckResponse.down("APP is not active");
  }
}

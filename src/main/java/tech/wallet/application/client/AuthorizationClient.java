package tech.wallet.application.client;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.wallet.application.client.payload.AuthorizationResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RegisterRestClient(configKey = "authorization")
@RegisterRestClient(baseUri = "https://run.mocky.io/v3/ad191145-9432-4878-ae62-0bce1306a792")
public interface AuthorizationClient {

  @GET
  @Timeout(unit = ChronoUnit.SECONDS, value = 10)
  @Retry(maxRetries = 6, delay = 4)
  @Fallback(fallbackMethod = "fallbackAuthorized")
  @CircuitBreaker(
      requestVolumeThreshold = 4,
      failureRatio = .75,
      delay = 3,
      successThreshold = 2)
  AuthorizationResponse isAuthorized();

  default AuthorizationResponse fallbackAuthorized() {
    Log.info("teste");
    return new AuthorizationResponse(true);
  }
}

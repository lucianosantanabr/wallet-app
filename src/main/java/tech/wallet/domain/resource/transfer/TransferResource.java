package tech.wallet.domain.resource.transfer;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import tech.wallet.application.exception.impl.AuthorizationBusinessErrorException;
import tech.wallet.application.exception.impl.InsufficientBalanceException;
import tech.wallet.application.exception.impl.TransferNotAllowForWalletTypeException;
import tech.wallet.application.exception.impl.TransferNotAuthorizationException;
import tech.wallet.application.exception.impl.WalletNotFoundException;
import tech.wallet.application.util.ResponseUtility;
import tech.wallet.domain.resource.transfer.payload.TransferRequest;
import tech.wallet.domain.service.AuthorizationService;
import tech.wallet.domain.service.TransferService;

@Path("/transfers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "/transfers", description = "Transfer manipulation endpoint pakage")
public class TransferResource implements ResponseUtility {

  @Inject TransferService transferService;
  @Inject
  AuthorizationService authorizationService;

  @Operation(summary = "Transfer in between wallet")
  @APIResponses(
      value = {
        @APIResponse(responseCode = "201", description = "Transfer success"),
        @APIResponse(responseCode = "412", description = "Payload validation failure")
      })
  @POST
  public Response transfer(@RequestBody(required = true) @Valid TransferRequest request)
      throws TransferNotAuthorizationException,
          TransferNotAllowForWalletTypeException,
          WalletNotFoundException,
          InsufficientBalanceException,
          AuthorizationBusinessErrorException {

    return created(transferService.transfer(request));
  }

  @Operation(summary = "List all Transfers")
  @APIResponses(
      value = {
        @APIResponse(responseCode = "200", description = "Transfers success"),
        @APIResponse(responseCode = "204", description = "Transfers null")
      })
  @GET
  public Response findAll() {

    var respLst = transferService.listAll();
    if (respLst.isEmpty()) {
      return noContent(List.of());
    }
    return ok(respLst);
  }

  @GET
  @Path("/autorization")
  public Response isAutorized() throws AuthorizationBusinessErrorException, InterruptedException {
    Thread.sleep(5000);
    return ok(authorizationService.isAuthorized());
  }
}

package tech.wallet.domain.resource.wallet;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import tech.wallet.application.exception.impl.WalletDataAlreadyExistsException;
import tech.wallet.application.exception.impl.WalletNotFoundException;
import tech.wallet.application.util.ResponseUtility;
import tech.wallet.domain.dto.WalletDTO;
import tech.wallet.domain.resource.wallet.payload.WalletRequest;
import tech.wallet.domain.service.WalletService;

@Path("/wallets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Wallets", description = "Wallet manipulation endpoint pakage")
public class WalletResource implements ResponseUtility {
  @Inject WalletService walletService;

  @Operation(summary = "Search for a wallet by id")
  @APIResponses(
      value = {
        @APIResponse(responseCode = "200", description = "Wallet returned"),
        @APIResponse(responseCode = "404", description = "Wallet does not exist"),
        @APIResponse(responseCode = "412", description = "Account Wallet does not exist")
      })
  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") String id) throws WalletNotFoundException {

    return ok(walletService.getId(id));
  }

  @Operation(summary = "List all wallets")
  @APIResponses(
      value = {
        @APIResponse(responseCode = "200", description = "List wallets returned"),
        @APIResponse(responseCode = "204", description = "No records found")
      })
  @GET
  public Response findALl() {

    List<WalletDTO> wallets = walletService.getAll();
    if (!wallets.isEmpty()) {
      return ok(wallets);
    }

    return noContent(List.of());
  }

  @Operation(summary = "Create a wallet")
  @APIResponses(
      value = {
        @APIResponse(responseCode = "201", description = "Wallet created"),
        @APIResponse(responseCode = "412", description = "Payload validation failure")
      })
  @POST
  public Response create(@RequestBody(required = true) @Valid WalletRequest request)
      throws WalletDataAlreadyExistsException {
    return created(walletService.create(request));
  }
}

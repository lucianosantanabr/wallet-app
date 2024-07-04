package tech.wallet.application.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RegisterForReflection
public class ErrorResponse implements Serializable {

  @Serial private static final long serialVersionUID = 2055597734251725116L;

  private String method;
  private String path;
  private LocalDateTime timestamp;
  private Integer status;
  private String code;
  private String message;
  private @Singular Map<String, Object> params;
}

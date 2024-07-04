package tech.wallet.application.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends Exception {

  @ToString.Include private Integer status = 422;
  @ToString.Include private final String message;
  @ToString.Include private final String code;
  @ToString.Include private Map<String, Object> params;

  protected BusinessException(Integer status, String code, String message) {
    super(message);
    this.message = message;
    this.code = code;
    this.status = status;
    this.params = new HashMap<>();
  }

  public void param(String key, Object value) {
    this.params.put(key, value);
  }

  public Object getParams(String key) {
    return this.params.getOrDefault(key, null);
  }

  public String getParamToString(String key) {
    return this.params.containsKey(key) ? this.params.get(key).toString() : "";
  }

  public void error(Logger logger) {
    logger.error("Exception {}: {}", this.getClass().getSimpleName(), getFormatedMessage());
  }

  public void error() {
    log.error("Business error. code:[{}] - message: {}", this.getCode(), getFormatedMessage());
  }

  public void warn() {
    log.warn("Business error. code:[{}] - message: {}", this.getCode(), getFormatedMessage());
  }

  public void warn(Logger logger) {
    logger.warn("Exception {}: {}", this.getClass().getSimpleName(), getFormatedMessage());
  }

  @Override
  public String getLocalizedMessage() {
    return getFormatedMessage();
  }

  public String getFormatedMessage() {
    String formated = this.getMessage();

    for (Entry<String, Object> entry : params.entrySet()) {
      String regex = "\\[(" + entry.getKey() + ")]";
      formated = formated.replaceAll(regex, entry.getValue().toString());
    }

    return formated;
  }

}

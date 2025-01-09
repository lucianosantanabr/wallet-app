package tech.wallet.application.util.log;

import io.quarkiverse.loggingjson.JsonGenerator;
import io.quarkiverse.loggingjson.JsonProvider;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.quarkus.arc.properties.IfBuildProperty;
import org.jboss.logmanager.ExtLogRecord;

@Singleton
@IfBuildProperty(name = "app.logging.json", stringValue = "app.logging.json")
public class CustomLoggingJsonProvider implements JsonProvider {

  @Override
  public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
    if (event.getThrown() != null) {
      Map<String, Object> exception = new HashMap<String, Object>();

      Throwable ex = event.getThrown();

      exception.put("class", ex.getClass().getName());
      exception.put("message", ex.getMessage());

      generator.writeObjectField("_exception", exception);
    }

    generator.writeStringField("sourceLocation", String.format("%s-%s:%d",
        event.getSourceFileName(), event.getSourceMethodName(), event.getSourceLineNumber()));
    generator.writeStringField("shortMessage", event.getMessage().substring(0,
        event.getMessage().length() > 128 ? 128 : event.getMessage().length()));
  }

}

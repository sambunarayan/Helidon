package jp.co.jeus.helidon.se.echo;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class EchoService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private static final Logger LOGGER = Logger.getLogger(GreetService.class.getName());

    EchoService(Config config) {
        greeting.set(config.get("app.greeting").asString().orElse("Ciao"));
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", "echo")
                .build();
        response.send(returnObject);
    }
}
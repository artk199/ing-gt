package pl.artkak.app.handlers;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import pl.artkak.util.Integration;

import java.io.IOException;

@ExtendWith(VertxExtension.class)
class GameQueueRequestHandlerIntegrationTest extends Integration {

    private static String request;
    private static String expectedResponse;

    @BeforeAll
    static void beforeAll() throws IOException {
        request = readFile("game/example_request.json");
        expectedResponse = readFile("game/example_response.json");
    }

    @BeforeEach
    void prepare(VertxTestContext testContext) {
        init(testContext);
    }

    @Test
    void successfulRequest(VertxTestContext testContext) {
        assertRequest(testContext,
                "/onlinegame/calculate",
                request,
                (body) -> JSONAssert.assertEquals(expectedResponse, body.toString(), true));

    }

}

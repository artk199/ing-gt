package pl.artkak.app;

import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import pl.artkak.app.handlers.AtmServiceRequestHandler;
import pl.artkak.app.handlers.GameQueueRequestHandler;
import pl.artkak.app.handlers.TransactionRequestHandler;

@Slf4j
public class VertxHttpServer extends AbstractVerticle {

    public static final int PORT = 8080;

    @Override
    public void start() {
        var server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.post("/atms/calculateOrder")
                .handler(getAtmRequestHandler());
        router.post("/onlinegame/calculate")
                .handler(getGameQueueRequestHandler());
        router.post("/transactions/report")
                .handler(getTransactionRequestHandler());

        server.requestHandler(router);
        server.listen(PORT);
    }

    private GameQueueRequestHandler getGameQueueRequestHandler() {
        return new GameQueueRequestHandler();
    }

    private AtmServiceRequestHandler getAtmRequestHandler() {
        return new AtmServiceRequestHandler();
    }

    private TransactionRequestHandler getTransactionRequestHandler() {
        return new TransactionRequestHandler();
    }

}

package pl.artkak;

import io.vertx.core.DeploymentOptions;
import io.vertx.rxjava3.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import pl.artkak.app.VertxHttpServer;

@Slf4j
public class KonkursApplication {

    public static final int INSTANCES_OF_HTTP_SERVER_VERTICLES = 20;

    public static void main(String[] args) {
        log.info("Starting 'IngKonkursApp' by Artur K...");
        Vertx vertx = Vertx.vertx();
        DeploymentOptions opts = new DeploymentOptions().setInstances(INSTANCES_OF_HTTP_SERVER_VERTICLES);
        vertx.deployVerticle(VertxHttpServer.class.getName(), opts)
                .subscribe(s -> log.info("Started HTTP server verticles: {}", s),
                        t -> log.error("Unexpected exception during deploy of verticles", t));
    }
}

package pl.artkak.util;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.junit5.VertxTestContext;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.core.buffer.Buffer;
import io.vertx.rxjava3.core.http.HttpClientResponse;
import pl.artkak.app.VertxHttpServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Integration {

    protected final Vertx vertx = Vertx.vertx();

    protected void init(VertxTestContext testContext) {
        vertx.deployVerticle(new VertxHttpServer())
                .subscribe(
                        (ok) -> testContext.completeNow(),
                        testContext::failNow
                );
    }

    protected void assertRequest(VertxTestContext ctx, String uri, String requestBody, ThrowableConsumer<Buffer> bodyAssertFunction) {
        RequestOptions options = new RequestOptions()
                .setHost("localhost")
                .setPort(8080)
                .setMethod(HttpMethod.POST)
                .setURI(uri);
        vertx.createHttpClient()
                .request(options)
                .compose(upstream -> upstream.concatMap(req -> req.send(Buffer.buffer(requestBody))))
                .compose(upstream -> upstream.concatMap(HttpClientResponse::body))
                .subscribe(body -> {
                    ctx.verify(() -> {
                        bodyAssertFunction.accept(body);
                        ctx.completeNow();
                    });
                }, ctx::failNow);
    }

    protected static String readFile(String name) throws IOException {
        String file = Objects.requireNonNull(Integration.class.getClassLoader().getResource(name)).getFile();
        Path filePath = new File(file).toPath();
        return Files.readString(filePath).trim();
    }

    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accept(T t) throws Throwable;
    }

}

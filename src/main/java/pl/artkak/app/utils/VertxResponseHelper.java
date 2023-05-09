package pl.artkak.app.utils;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.rxjava3.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * User: Artur Kąkol
 * Date: 09.05.2023 16:58
 * Company: Blue Media
 *
 * @author <a href="mailto:artur.kakol@bluemedia.pl">Artur Kąkol</a>
 */
@Slf4j
public class VertxResponseHelper {

    public static void errorResponse(RoutingContext ctx, Throwable t) {
        log.error("Error while handling request", t);
        ctx.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }

    public static void jsonResponse(RoutingContext ctx, Object result) {
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .end(JsonObjectMapper.serialize(result));
    }

}

package pl.artkak.app.handlers;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.rxjava3.core.buffer.Buffer;
import io.vertx.rxjava3.ext.web.RoutingContext;
import pl.artkak.app.model.GameQueueRequest;
import pl.artkak.app.utils.JsonObjectMapper;
import pl.artkak.core.game.GameQueueService;

import static pl.artkak.app.utils.VertxResponseHelper.errorResponse;
import static pl.artkak.app.utils.VertxResponseHelper.jsonResponse;

public class GameQueueRequestHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        GameQueueService gameQueueService = new GameQueueService();
        ctx.response().setChunked(true);

        ctx.request().toFlowable()
                .collect(Buffer::buffer, Buffer::appendBuffer)
                .compose(upstream -> upstream.concatMap(b -> Single.just(JsonObjectMapper.deserialize(GameQueueRequest.class, b))))
                .map(gameQueueRequest -> gameQueueService.group(gameQueueRequest.getGroupCount(), gameQueueRequest.getClans()))
                .subscribe(
                        result -> jsonResponse(ctx, result),
                        t -> errorResponse(ctx, t)
                );
    }

}

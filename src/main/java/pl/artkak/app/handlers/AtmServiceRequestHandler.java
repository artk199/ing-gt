package pl.artkak.app.handlers;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.rxjava3.core.buffer.Buffer;
import io.vertx.rxjava3.ext.web.RoutingContext;
import pl.artkak.app.model.AtmServiceTaskRequest;
import pl.artkak.app.model.AtmServiceTasksResponse;
import pl.artkak.core.atm.AtmService;
import pl.artkak.core.atm.IndexedAtmInfo;

import java.util.Collection;
import java.util.List;

import static pl.artkak.app.utils.JsonObjectMapper.deserializeList;
import static pl.artkak.app.utils.VertxResponseHelper.errorResponse;
import static pl.artkak.app.utils.VertxResponseHelper.jsonResponse;

public class AtmServiceRequestHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        AtmService atmService = new AtmService();

        ctx.response().setChunked(true);

        ctx.request().toFlowable()
                .collect(Buffer::buffer, Buffer::appendBuffer)
                .compose(upstream -> upstream.concatMap(b -> Single.just(deserializeList(AtmServiceTaskRequest.class, b))))
                .flattenAsFlowable(atms -> atms)
                .subscribe(
                        req -> atmService.addToRoute(req.getRegion(), req.getAtmId(), req.getRequestType()),
                        t -> errorResponse(ctx, t),
                        () -> jsonResponse(ctx, mapToResponse(atmService.getSortedResult()))
                );
    }

    private List<AtmServiceTasksResponse> mapToResponse(Collection<IndexedAtmInfo> atmInfos) {
        return atmInfos.stream()
                .map(atmInfo -> new AtmServiceTasksResponse(atmInfo.getRegion(), atmInfo.getAtmId()))
                .toList();
    }

}

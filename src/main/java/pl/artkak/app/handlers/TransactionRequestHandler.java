package pl.artkak.app.handlers;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.rxjava3.core.buffer.Buffer;
import io.vertx.rxjava3.ext.web.RoutingContext;
import pl.artkak.app.model.TransactionRequest;
import pl.artkak.app.model.TransactionResponse;
import pl.artkak.app.utils.JsonObjectMapper;
import pl.artkak.core.transaction.AccountBalance;
import pl.artkak.core.transaction.TransactionAggregator;

import java.util.Collection;
import java.util.List;

import static pl.artkak.app.utils.VertxResponseHelper.errorResponse;
import static pl.artkak.app.utils.VertxResponseHelper.jsonResponse;

public class TransactionRequestHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        TransactionAggregator transactionAggregator = new TransactionAggregator();
        ctx.response().setChunked(true);
        ctx.request().toFlowable()
                .collect(Buffer::buffer, Buffer::appendBuffer)
                .compose(upstream -> upstream.concatMap(b -> Single.just(JsonObjectMapper.deserializeList(TransactionRequest.class, b))))
                .flattenAsFlowable(transactions -> transactions)
                .subscribe(
                        t -> transactionAggregator.apply(t.getDebitAccount(), t.getCreditAccount(), t.getAmount()),
                        t -> errorResponse(ctx, t),
                        () -> onComplete(ctx, transactionAggregator.getSortedAccountBalances())
                );
    }

    private void onComplete(RoutingContext ctx, Collection<AccountBalance> accountBalances) {
        List<TransactionResponse> response = accountBalances.stream()
                .map(ab -> new TransactionResponse(ab.getAccount(), ab.getDebitCount(), ab.getCreditCount(), ab.getBalance()))
                .toList();
        jsonResponse(ctx, response);
    }

}

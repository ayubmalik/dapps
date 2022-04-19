package ayubmalik.web3.alchemy;

import io.reactivex.functions.Cancellable;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class AlchemyClient {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final Web3j web3j;

    public AlchemyClient(EthNetwork network, String apiKey) {
        var url = String.format("https://eth-%s.alchemyapi.io/v2/%s", network.id(), apiKey);
        web3j = Web3j.build(new HttpService(url));
    }

    public Long getLatestBlockNumber() {
        try {
            var block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
            return block.getNumber().longValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Cancellable getTransactions(Consumer<Transaction> consumer) {
        var subscription = (Subscription) web3j.transactionFlowable().subscribe(tx -> {
            consumer.accept(tx);
        });

        return () -> {
            log.info("shutting down 3");
            subscription.cancel();
            web3j.shutdown();
        };
    }
}


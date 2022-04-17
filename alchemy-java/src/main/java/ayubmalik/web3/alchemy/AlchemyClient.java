package ayubmalik.web3.alchemy;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

public class AlchemyClient {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Web3j web3j;

    public AlchemyClient(EthNetwork network, String apiKey) {
        var url = String.format("https://eth-%s.alchemyapi.io/v2/%s", network.id(), apiKey);
        this.web3j = Web3j.build(new HttpService(url));
    }

    public Long getLatestBlockNumber() {
        try {
            var block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
            return block.getNumber().longValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Disposable getTransactions() {
        Disposable subscription = web3j.transactionFlowable().subscribe(tx -> {
            log.info("TX {} {} {}", tx.getFrom(), tx.getTo(), tx.getValue());
        });

        return subscription;
    }
}

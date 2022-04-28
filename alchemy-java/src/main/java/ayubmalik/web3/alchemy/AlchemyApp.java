package ayubmalik.web3.alchemy;

import io.reactivex.functions.Cancellable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Objects;

public class AlchemyApp {

    private static final Logger log = LoggerFactory.getLogger(AlchemyApp.class);

    public static void main(String[] args) throws InterruptedException {
        var home = System.getProperty("user.home");
        if (Objects.isNull(home) || home.isBlank()) {
            throw new AppException("could not get user.home");
        }

        var apiKey = System.getenv("API_KEY");
        if (Objects.isNull(apiKey) || apiKey.isBlank()) {
            throw new AppException("invalid API_KEY");
        }

        var ethNetwork = EthNetwork.valueOf(System.getenv("ETH_NETWORK"));
        log.info("home = {}, ethNetwork = {}", home, ethNetwork);

        var txLog = new RocksTxLog(Path.of(home, ".ethereum-tx.%s.log".formatted(ethNetwork.id())));
        monitorTransactions(txLog);
        addShutdownHook(txLog::close);
//
//        var client = new AlchemyClient(ethNetwork, apiKey);
//
//        var cancellable = client.getNewTransactions(txLog::put);
//        addShutdownHook(wrap(cancellable));

    }

    private static void monitorTransactions(RocksTxLog txLog) {
        var max = 5;
        var transactions = txLog.getTransactions(max);
        var total = transactions.stream().map(Transaction::getValue).reduce(BigInteger.ZERO, BigInteger::add);
        var ethTotal =  Convert.fromWei(new BigDecimal(total), Convert.Unit.ETHER);
        log.info("Value of last {} transactions = {} ETH", max, ethTotal);
    }

    private static void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }

    private static Runnable wrap(Cancellable cancellable) {
        return () -> {
            try {
                cancellable.cancel();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}

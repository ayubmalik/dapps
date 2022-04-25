package ayubmalik.web3.alchemy;

import io.reactivex.functions.Cancellable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Objects;

public class AlchemyApp {

    private static final Logger log = LoggerFactory.getLogger(AlchemyApp.class);

    public static void main(String[] args) {
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
        addShutdownHook(txLog::close);

        var client = new AlchemyClient(ethNetwork, apiKey);

        var cancellable = client.getTransactions(tx -> log.info("received tx: {}", tx.getFrom()));
        addShutdownHook(wrap(cancellable));
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

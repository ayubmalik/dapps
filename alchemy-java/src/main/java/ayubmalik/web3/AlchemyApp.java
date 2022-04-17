package ayubmalik.web3;

import ayubmalik.web3.alchemy.AlchemyClient;
import ayubmalik.web3.alchemy.EthNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Objects;

public class AlchemyApp {

    private static final Logger log = LoggerFactory.getLogger(AlchemyApp.class);

    public static void main(String[] args) {
        var home = System.getProperty("user.home");
        if (Objects.isNull(home) || home.isBlank()) {
            throw new AppException("could not get user home");
        }
        log.info("home = {}", home);
        var txLog = new RocksTxLog(Path.of(home, ".txlog"));

        var key = System.getenv("API_KEY");
        if (Objects.isNull(key) || key.isBlank()) {
            throw new AppException("invalid API key");
        }

        var client = new AlchemyClient(EthNetwork.ROPSTEN, key);
        var latestBlockNumber = client.getLatestBlockNumber();
        log.info("latestBlockNumber = {}" , latestBlockNumber);

        client.getTransactions();

    }
}

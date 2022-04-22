package ayubmalik.web3.alchemy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        var apiKey = System.getenv("API_KEY");
        if (apiKey == null) {
            throw new RuntimeException("API_KEY is null");
        }

        var client = new AlchemyClient(EthNetwork.ROPSTEN, apiKey);
        var latest = client.getLatestBlockNumber();
        log.info("block number = {}", latest);
    }

}

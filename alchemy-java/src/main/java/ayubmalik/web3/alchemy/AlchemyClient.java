package ayubmalik.web3.alchemy;

import java.io.IOException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

public class AlchemyClient {

    private final Web3j web3;

    public AlchemyClient(EthNetwork network, String apiKey) {
        var url = String.format("https://eth-%s.alchemyapi.io/v2/%s", network.id(), apiKey);
        this.web3 = Web3j.build(new HttpService(url));
    }

    public Long getLatestBlockNumber() {
        try {
            var block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
            return block.getNumber().longValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

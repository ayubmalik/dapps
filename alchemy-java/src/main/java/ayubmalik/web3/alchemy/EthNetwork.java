package ayubmalik.web3.alchemy;

public enum EthNetwork {

    MAINNET("mainnet"),
    ROPSTEN("ropsten");

    private final String id;

    EthNetwork(String network) {
        this.id = network;
    }

    public String id() {
        return id;
    }
}

package ayubmalik.web3.alchemy;

public enum EthNetwork {

    MAIN("main"),
    ROPSTEN("ropsten");

    private final String id;

    EthNetwork(String network) {
        this.id = network;
    }

    public String id() {
        return id;
    }
}

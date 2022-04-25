package ayubmalik.web3.alchemy;

public class AppException extends RuntimeException {
    public AppException(Exception e) {
        super(e);
    }

    public AppException(String msg) {
        super(msg);
    }
}

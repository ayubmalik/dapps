package ayubmalik.web3;

import org.rocksdb.RocksDBException;

public class AppException extends RuntimeException {
    public AppException(RocksDBException e) {
        super(e);
    }
}

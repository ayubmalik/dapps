package ayubmalik.web3;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.web3j.protocol.core.methods.response.Transaction;

import java.nio.file.Path;
import java.util.List;

public class RocksTxLog implements TxLog {

    static {
        RocksDB.loadLibrary();
    }

    private final RocksDB db;

    public RocksTxLog(Path path) {
        this.db = openRocksDB(path);
    }

    private RocksDB openRocksDB(Path path) {
        try {
         return RocksDB.open(new Options().setCreateIfMissing(true), path.toString());
        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

    @Override
    public Long lastBlockNumber() {
        return null;
    }

    @Override
    public List<Transaction> transactions() {
        return null;
    }

}

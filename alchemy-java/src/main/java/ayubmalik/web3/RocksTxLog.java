package ayubmalik.web3;

import com.google.common.primitives.Longs;
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

    private final byte[] latestBlockNumber = "latestBlockNumber".getBytes();

    private final RocksDB db;

    RocksTxLog(Path path) {
        db = openRocksDB(path);
    }

    @Override
    public Long getLatestBlockNumber() {
        try {
            var blockNum = db.get(latestBlockNumber);
            return Longs.fromByteArray(blockNum);
        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

    @Override
    public void putLatestBlockNumber(Long blockNumber) {
        try {
            db.put(latestBlockNumber, Longs.toByteArray(blockNumber));
        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

    @Override
    public List<Transaction> transactions() {
        return null;
    }

    private RocksDB openRocksDB(Path path) {
        try {
            return RocksDB.open(new Options().setCreateIfMissing(true), path.toString());
        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

}

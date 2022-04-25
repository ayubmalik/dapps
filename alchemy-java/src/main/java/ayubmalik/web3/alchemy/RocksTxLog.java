package ayubmalik.web3.alchemy;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.Transaction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RocksTxLog implements TxLog {

    static {
        RocksDB.loadLibrary();
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RocksDB db;

    public RocksTxLog(Path path) {
        db = openRocksDB(path);
    }

    @Override
    public List<Transaction> transactions(Integer max) {
        log.debug("get latest {} transactions", max);
        return null;
    }

    public void close() {
        log.debug("closing db");
        db.close();
    }

    private RocksDB openRocksDB(Path path) {
        try {
            log.debug("opening rocks db in {}", path);
            var columnFamilyOptions = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();
            var columnFamilyDescriptors = List.of(
                    new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyOptions),
                    new ColumnFamilyDescriptor("transactions".getBytes(), columnFamilyOptions)
            );

            return RocksDB.open(
                    new DBOptions()
                            .setCreateIfMissing(true)
                            .setCreateMissingColumnFamilies(true),
                    path.toString(),
                    columnFamilyDescriptors,
                    new ArrayList<>()
            );
        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }
}

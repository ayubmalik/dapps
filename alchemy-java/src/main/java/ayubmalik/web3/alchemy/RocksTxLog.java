package ayubmalik.web3.alchemy;

import ayubmalik.web3.alchemy.proto.EthSig;
import ayubmalik.web3.alchemy.proto.EthTx;
import com.google.protobuf.ByteString;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.Transaction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RocksTxLog implements TxLog {

    public static final byte[] CF_TRANSACTIONS = "transactions".getBytes();

    static {
        RocksDB.loadLibrary();
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RocksDB db;
    private final List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();

    public RocksTxLog(Path path) {
        db = openRocksDB(path);
    }

    @Override
    public List<Transaction> transactions(Integer max) {
        log.debug("get latest {} transactions", max);
        return null;
    }

    @Override
    public void put(Transaction tx) {
        try {
            var bytes = toProtobuf(tx);
            db.put(getCol("transactions"), tx.getHash().getBytes(), bytes);
            log.info("put tx {}", tx.getHash());
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public void close() {
        log.debug("closing db");
        db.close();
    }

    private ColumnFamilyHandle getCol(final String name) {
        return columnFamilyHandles.stream()
                .filter(c -> matches(c, name))
                .findFirst()
                .orElseThrow();
    }

    private boolean matches(ColumnFamilyHandle c, String name) {
        try {
            return new String(c.getName()).equals(name);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    private RocksDB openRocksDB(Path path) {
        try {
            log.debug("opening rocks db in {}", path);
            var columnFamilyOptions = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();
            var columnFamilyDescriptors = List.of(
                    new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyOptions),
                    new ColumnFamilyDescriptor(CF_TRANSACTIONS, columnFamilyOptions)
            );

            return RocksDB.open(
                    new DBOptions()
                            .setCreateIfMissing(true)
                            .setCreateMissingColumnFamilies(true),
                    path.toString(),
                    columnFamilyDescriptors,
                    columnFamilyHandles
            );

        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

    private byte[] toProtobuf(Transaction tx) {
        var ethTx = EthTx.newBuilder()
                .setHash(bytes(tx.getHash()))
                .setNonce(bytes(tx.getNonceRaw()))
                .setBlockHash(bytes(tx.getBlockHash()))
                .setBlockNumber(bytes(tx.getBlockNumberRaw()))
                .setTxIndex(bytes(tx.getTransactionIndexRaw()))
                .setFrom(bytes(tx.getFrom()))
                .setTo(bytes(tx.getTo()))
                .setValue(bytes(tx.getValueRaw()))
                .setGas(bytes(tx.getGasRaw()))
                .setGasPrice(bytes(tx.getGasPriceRaw()))
                .setData(bytes(tx.getInput()))
                .setCreates(bytes(tx.getCreates()))
                .setPublicKey(bytes(tx.getPublicKey()))
                .setRaw(bytes(tx.getRaw()))
                .setSig(EthSig.newBuilder()
                        .setR(bytes(tx.getR()))
                        .setS(bytes(tx.getS()))
                        .setV(tx.getV())
                        .build())
                .setTxType(bytes(tx.getType()))
                .setMaxFeePerGas(bytes(tx.getMaxFeePerGas()))
                .setMaxPriorityFeePerGas(bytes(tx.getMaxPriorityFeePerGas()))
                .build();
        return ethTx.toByteArray();
    }

    ByteString bytes(String s) {
        if (Objects.isNull(s)) {
            return ByteString.EMPTY;
        }
        return ByteString.copyFrom(s.getBytes(UTF_8));
    }

}

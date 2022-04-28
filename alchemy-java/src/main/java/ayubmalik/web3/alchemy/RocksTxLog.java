package ayubmalik.web3.alchemy;

import ayubmalik.web3.alchemy.proto.EthSig;
import ayubmalik.web3.alchemy.proto.EthTx;
import com.google.protobuf.ByteString;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RocksTxLog implements TxLog {

    static {
        RocksDB.loadLibrary();
    }

    private static final String CF_TRANSACTIONS = "transactions";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RocksDB db;
    private final List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();

    public RocksTxLog(Path path) {
        db = openRocksDB(path);
    }

    @Override
    public List<Transaction> getTransactions(Integer max) {
        var results = new ArrayList<Transaction>(max);
        try (RocksIterator iterator = db.newIterator(getTxColHandle(), new ReadOptions())) {
            iterator.seekToLast();
            for (int i = 0; i < max; i++) {
                iterator.prev();
                var tx = toWeb3(EthTx.parseFrom(iterator.value()));
                debugLog(tx);
                results.add(tx);
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
        return results;
    }

    private void debugLog(Transaction tx) {
        if (log.isDebugEnabled()) {
            var eth = Convert.fromWei(new BigDecimal(tx.getValue()), Convert.Unit.ETHER);
            log.debug("read key = {}, wei = {} eth = {}", tx.getHash(), tx.getValue(), eth);
        }
    }

    @Override
    public void put(Transaction tx) {
        try {
            var bytes = toProtobuf(tx);
            db.put(getTxColHandle(), tx.getHash().getBytes(UTF_8), bytes);
            log.info("put tx {}", tx.getHash());
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public void close() {
        log.debug("closing db");
        columnFamilyHandles.forEach(ColumnFamilyHandle::close);
        db.close();
    }

    private ColumnFamilyHandle getTxColHandle() {
        return columnFamilyHandles.stream().filter(this::matches).findFirst().orElseThrow();
    }

    private boolean matches(ColumnFamilyHandle c) {
        try {
            return new String(c.getName()).equals(CF_TRANSACTIONS);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    private RocksDB openRocksDB(Path path) {
        try {
            log.debug("opening rocks db in {}", path);
            var columnFamilyOptions = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();
            var columnFamilyDescriptors = List.of(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyOptions), new ColumnFamilyDescriptor(CF_TRANSACTIONS.getBytes(UTF_8), columnFamilyOptions));

            return RocksDB.open(new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true), path.toString(), columnFamilyDescriptors, columnFamilyHandles);

        } catch (RocksDBException e) {
            throw new AppException(e);
        }
    }

    private byte[] toProtobuf(Transaction tx) {
        var ethTx = EthTx.newBuilder().setHash(bytes(tx.getHash())).setNonce(bytes(tx.getNonceRaw())).setBlockHash(bytes(tx.getBlockHash())).setBlockNumber(bytes(tx.getBlockNumberRaw())).setTxIndex(bytes(tx.getTransactionIndexRaw())).setFrom(bytes(tx.getFrom())).setTo(bytes(tx.getTo())).setValue(bytes(tx.getValueRaw())).setGas(bytes(tx.getGasRaw())).setGasPrice(bytes(tx.getGasPriceRaw())).setData(bytes(tx.getInput())).setCreates(bytes(tx.getCreates())).setPublicKey(bytes(tx.getPublicKey())).setRaw(bytes(tx.getRaw())).setSig(EthSig.newBuilder().setR(bytes(tx.getR())).setS(bytes(tx.getS())).setV(tx.getV()).build()).setTxType(bytes(tx.getType())).setMaxFeePerGas(bytes(tx.getMaxFeePerGas())).setMaxPriorityFeePerGas(bytes(tx.getMaxPriorityFeePerGas())).build();
        return ethTx.toByteArray();
    }

    private Transaction toWeb3(EthTx ethTx) {
        return new Transaction(ethTx.getHash().toStringUtf8(), ethTx.getNonce().toStringUtf8(), ethTx.getBlockHash().toStringUtf8(), ethTx.getBlockNumber().toStringUtf8(), ethTx.getTxIndex().toStringUtf8(), ethTx.getFrom().toStringUtf8(), ethTx.getTo().toStringUtf8(), ethTx.getValue().toStringUtf8(), ethTx.getGas().toStringUtf8(), ethTx.getGasPrice().toStringUtf8(), ethTx.getData().toStringUtf8(), ethTx.getCreates().toStringUtf8(), ethTx.getPublicKey().toStringUtf8(), ethTx.getRaw().toStringUtf8(), ethTx.getSig().getR().toStringUtf8(), ethTx.getSig().getS().toStringUtf8(), ethTx.getSig().getV(), ethTx.getTxType().toStringUtf8(), ethTx.getMaxFeePerGas().toStringUtf8(), ethTx.getMaxPriorityFeePerGas().toStringUtf8(), List.of());
    }

    ByteString bytes(String s) {
        if (Objects.isNull(s)) {
            return ByteString.EMPTY;
        }
        return ByteString.copyFrom(s.getBytes(UTF_8));
    }

}

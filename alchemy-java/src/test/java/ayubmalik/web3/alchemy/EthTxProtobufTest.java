package ayubmalik.web3.alchemy;

import ayubmalik.web3.alchemy.proto.EthSig;
import ayubmalik.web3.alchemy.proto.EthTx;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.Transaction;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class EthTxProtobufTest {

    private final AlchemyClient client = new AlchemyClient(EthNetwork.ROPSTEN, System.getenv("API_KEY"));

    ByteString bytes(String s) {
        if (Objects.isNull(s)) {
            return ByteString.EMPTY;
        }
        return ByteString.copyFrom(s.getBytes(StandardCharsets.UTF_8));
    }

    String utf8(ByteString bs) {
        if (bs.isEmpty()) {
            return null;
        }
        return bs.toStringUtf8();
    }

    @Test
    void getTransactionToBuilder() {
        var hash = "0xd3e5b2e52dd777a2f5255d63474834f1876817e809cd8be65f8f303df23a73c4";
        var tx = client.getTransaction(hash);

        assertThat(tx.getFrom()).isEqualTo("0x024763f2879d9d5c2b5e32a83527f52024c3f0dc");
        assertThat(tx.getTo()).isEqualTo("0x68b3465833fb72a70ecdf485e0e4c7bd8665fc45");

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

        var tx2 = new Transaction(
                hash,
                utf8(ethTx.getNonce()),
                utf8(ethTx.getBlockHash()),
                utf8(ethTx.getBlockNumber()),
                utf8(ethTx.getTxIndex()),
                utf8(ethTx.getFrom()),
                utf8(ethTx.getTo()),
                utf8(ethTx.getValue()),
                utf8(ethTx.getGas()),
                utf8(ethTx.getGasPrice()),
                utf8(ethTx.getData()),
                utf8(ethTx.getCreates()),
                utf8(ethTx.getPublicKey()),
                utf8(ethTx.getRaw()),
                utf8(ethTx.getSig().getR()),
                utf8(ethTx.getSig().getS()),
                ethTx.getSig().getV(),
                utf8(ethTx.getTxType()),
                utf8(ethTx.getMaxFeePerGas()),
                utf8(ethTx.getMaxPriorityFeePerGas()),
                List.of()
        );

        assertThat(tx2.getHash()).isEqualTo(tx.getHash());
        assertThat(tx2.getNonce()).isEqualTo(tx.getNonce());
        assertThat(tx2.getBlockHash()).isEqualTo(tx.getBlockHash());
        assertThat(tx2.getBlockNumber()).isEqualTo(tx.getBlockNumber());
        assertThat(tx2.getTransactionIndex()).isEqualTo(tx.getTransactionIndex());
        assertThat(tx2.getFrom()).isEqualTo(tx.getFrom());
        assertThat(tx2.getTo()).isEqualTo(tx.getTo());
        assertThat(tx2.getValue()).isEqualTo(tx.getValue());
        assertThat(tx2.getGas()).isEqualTo(tx.getGas());
        assertThat(tx2.getGasPrice()).isEqualTo(tx.getGasPrice());
        assertThat(tx2.getInput()).isEqualTo(tx.getInput());
        assertThat(tx2.getCreates()).isEqualTo(tx.getCreates());
        assertThat(tx2.getPublicKey()).isEqualTo(tx.getPublicKey());
        assertThat(tx2.getRaw()).isEqualTo(tx.getRaw());
        assertThat(tx2.getR()).isEqualTo(tx.getR());
        assertThat(tx2.getS()).isEqualTo(tx.getS());
        assertThat(tx2.getV()).isEqualTo(tx.getV());
        assertThat(tx2.getType()).isEqualTo(tx.getType());
        assertThat(tx2.getMaxFeePerGas()).isEqualTo(tx.getMaxFeePerGas());
        assertThat(tx2.getMaxPriorityFeePerGas()).isEqualTo(tx.getMaxPriorityFeePerGas());
    }
}

package ayubmalik.web3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.web3j.protocol.core.methods.response.Transaction;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RocksTxLogTest {

    @TempDir
    Path tempDir;

    @Test
    void lastBlockNumber() {
        var txLog = new RocksTxLog(tempDir);

        txLog.putLatestBlockNumber(321L);
        var blockNum = txLog.getLatestBlockNumber();
        assertThat(blockNum).isEqualTo(321);
    }

    @Test
    void latestTransactions() {
        var txLog = new RocksTxLog(tempDir);
        List<Transaction> transactions = txLog.transactions(20);
        assertThat(transactions).hasSize(20);
    }
}
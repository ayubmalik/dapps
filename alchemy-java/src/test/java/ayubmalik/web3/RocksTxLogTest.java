package ayubmalik.web3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class RocksTxLogTest {

    @TempDir
    Path tempDir;

    @Test
    void lastBlockNumber() {
        var txLog = new RocksTxLog(tempDir);
        var blockNum = txLog.lastBlockNumber();
        assertThat(blockNum).isEqualTo(1);
    }

    @Test
    void allTransactions() {
        System.out.println(tempDir);
    }
}
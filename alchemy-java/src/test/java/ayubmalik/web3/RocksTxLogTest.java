package ayubmalik.web3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class RocksTxLogTest {

    @TempDir
    Path tempDir;

    @Test
    @Disabled("wip")
    void latestTransactions() {
        var txLog = new RocksTxLog(tempDir);
        var transactions = txLog.transactions(20);
        assertThat(transactions).hasSize(20);
    }
}
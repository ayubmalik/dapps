package ayubmalik.web3;

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

        txLog.putLatestBlockNumber(321L);
        var blockNum = txLog.getLatestBlockNumber();
        assertThat(blockNum).isEqualTo(321);
    }
    
}
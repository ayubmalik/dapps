package ayubmalik.web3;

import java.util.List;
import org.web3j.protocol.core.methods.response.Transaction;

public interface TxLog {

  Long getLatestBlockNumber();

  void putLatestBlockNumber(Long blockNumber);

  List<Transaction> transactions();
}

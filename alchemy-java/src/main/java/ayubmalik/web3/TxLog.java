package ayubmalik.web3;

import org.web3j.protocol.core.methods.response.Transaction;

import java.util.List;

public interface TxLog {

    List<Transaction> transactions(Integer max);
}

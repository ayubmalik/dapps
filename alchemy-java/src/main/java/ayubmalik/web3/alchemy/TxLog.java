package ayubmalik.web3.alchemy;

import org.web3j.protocol.core.methods.response.Transaction;

import java.util.List;

public interface TxLog {

    List<Transaction> transactions(Integer max);

    void put(Transaction tx);
}

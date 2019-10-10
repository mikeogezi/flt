package mikeogezi.daos;

import com.google.gson.Gson;
import mikeogezi.models.Transaction;
import mikeogezi.utils.DefaultUtils;
import mikeogezi.utils.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDao implements Dao {

    private static Logger log = LoggerFactory.getLogger(TransactionDao.class);

    private List<Transaction> transactionList = getTransactions();

    @Override
    public Optional get(long id) {
        try {
            return Optional.of(transactionList.get((int) id));
        }
        catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public Optional getBy (String transactionId) {
        for (Transaction transaction: transactionList) {
            if (transaction.getTransactionId().equals(transactionId)) {
                return Optional.of(transaction);
            }
        }

        return Optional.empty();
    }

    @Override
    public List getAll() {
        return transactionList;
    }

    @Override
    public void save(Object o) {
        Transaction transaction = (Transaction) o;
        transaction.setId(transactionList.size());
        transactionList.add(transaction);
    }

    private static List<Transaction> getTransactions () {
        List<Transaction> transactionList = new ArrayList<>();

        for (int i = 0; i < DefaultUtils.count; ++i) {
            Transaction transaction = new Transaction();

            transaction.setId(i);
            transaction.setTransactionId(DefaultUtils.transactionIds.get(i));

            transaction.setFromAccountNumber(DefaultUtils.accountNumbers.get(i));
            transaction.setFromBankCode(DefaultUtils.bankCodes.get(i));

            transaction.setToAccountNumber(DefaultUtils.accountNumbers.get((i + 1) % DefaultUtils.count));
            transaction.setToBankCode(DefaultUtils.bankCodes.get((i + 1) % DefaultUtils.count));

            transaction.setAmount(DefaultUtils.amounts.get(i));
            transaction.setType(TransactionType.TRANSFER);

            transaction.setNarration(String.format("Transfer from %s to %s", transaction.getFromAccountNumber(),
                    transaction.getToAccountNumber()));

            transactionList.add(transaction);
        }

        log.info("Transactions: {}", new Gson().toJson(transactionList));
        return transactionList;
    }

}

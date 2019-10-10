package mikeogezi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import mikeogezi.utils.TransactionType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRequeryResponse extends Response {

    private TransactionType transactionType;

    private String fromAccountNumber;
    private String fromBankCode;

    private String toAccountNumber;
    private String toBankCode;

    private String narration;

    private BigDecimal amount;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getFromBankCode() {
        return fromBankCode;
    }

    public void setFromBankCode(String fromBankCode) {
        this.fromBankCode = fromBankCode;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getToBankCode() {
        return toBankCode;
    }

    public void setToBankCode(String toBankCode) {
        this.toBankCode = toBankCode;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

//    private Transaction transaction;
//
//    public Transaction getTransaction() {
//        return transaction;
//    }
//
//    public void setTransaction(Transaction transaction) {
//        this.transaction = transaction;
//    }

    public void addTransactionInfo (Transaction transaction) {
//        setTransaction(transaction);
        setTransactionType(transaction.getType());
        setFromAccountNumber(transaction.getFromAccountNumber());
        setFromBankCode(transaction.getFromBankCode());
        setToAccountNumber(transaction.getToAccountNumber());
        setToBankCode(transaction.getToBankCode());
        setNarration(transaction.getNarration());
        setAmount(transaction.getAmount());
    }

}

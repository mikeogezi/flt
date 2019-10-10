package mikeogezi.models;

import mikeogezi.utils.ResponseType;
import mikeogezi.utils.TransactionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TransferRequest extends Request {

    @NotNull(message = "fromAccountNumber cannot be null or empty")
    @Size(min = 10, max = 10, message="fromAccountNumber must be 10 digits")
    private String fromAccountNumber;

    private String fromAccountName;

    @NotNull(message = "fromBankCode cannot be null or empty")
    @Size(min = 3, max = 3, message="fromBankCode must be 3 digits")
    private String fromBankCode;

    @NotNull(message = "toAccountNumber cannot be null or empty")
    @Size(min = 10, max = 10, message="toAccountNumber must be 10 digits")
    private String toAccountNumber;

    private String toAccountName;

    @NotNull(message = "toBankCode cannot be null or empty")
    @Size(min = 3, max = 3, message="toBankCode must be 3 digits")
    private String toBankCode;

    @NotNull(message = "amount cannot be null or empty")
    private BigDecimal amount;

    private String narration;

    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
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

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getToBankCode() {
        return toBankCode;
    }

    public void setToBankCode(String toBankCode) {
        this.toBankCode = toBankCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Transaction buildTransaction (ResponseType responseType) {
        Transaction transaction = new Transaction();

        transaction.setType(TransactionType.TRANSFER);
        transaction.setTransactionId(getTransactionId());
        transaction.setAmount(getAmount());
        transaction.setFromBankCode(getFromBankCode());
        transaction.setFromAccountNumber(getFromAccountNumber());
        transaction.setToBankCode(getToBankCode());
        transaction.setToAccountNumber(getToAccountNumber());
        transaction.setNarration(getNarration());
        transaction.setResponseType(responseType);

        return transaction;
    }

}

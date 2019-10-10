package mikeogezi.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TransferResponse extends Response {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}

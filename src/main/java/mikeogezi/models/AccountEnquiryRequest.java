package mikeogezi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountEnquiryRequest extends Request {

    @NotNull(message = "accountNumber cannot be null or empty")
    @Size(min = 10, max = 10, message="accountNumber must be 10 digits")
    private String accountNumber;

    @NotNull(message = "bankCode cannot be null or empty")
    @Size(min = 3, max = 3, message="bankCode must be 3 digits")
    private String bankCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}

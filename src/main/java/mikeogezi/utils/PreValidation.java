package mikeogezi.utils;

import mikeogezi.exceptions.PreValidationException;
import mikeogezi.models.AccountEnquiryRequest;
import mikeogezi.models.Request;
import mikeogezi.models.TransferRequest;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PreValidation {

    private static PreValidation instance = new PreValidation();

    public static PreValidation getInstance() {
        return instance;
    }

    private void validateRequest (Request request)
        throws PreValidationException {

        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isEmpty(request.getToken())) {
            errors.put("token", "token cannot be empty or null");
        }

        if (!errors.isEmpty()) {
            throw new PreValidationException(errors);
        }

    }

    public void validateAccountEnquiryRequest (AccountEnquiryRequest request)
        throws PreValidationException {

        validateRequest(request);

        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isEmpty(request.getAccountNumber())) {
            errors.put("accountNumber", "accountNumber cannot be empty or null");
        }

        if (StringUtils.isEmpty(request.getBankCode())) {
            errors.put("bankCode", "bankCode cannot be empty or null");
        }

        if (!errors.isEmpty()) {
            throw new PreValidationException(errors);
        }

    }

    public void validateTransferRequest (TransferRequest request)
        throws PreValidationException {

        validateRequest(request);

        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isEmpty(request.getFromAccountNumber())) {
            errors.put("fromAccountNumber", "accountNumber cannot be empty or null");
        }

        if (StringUtils.isEmpty(request.getToAccountNumber())) {
            errors.put("toAccountNumber", "toAccountNumber cannot be empty or null");
        }

        if (StringUtils.isEmpty(request.getAmount()) || request.getAmount().doubleValue() <= 0.0) {
            errors.put("amount", "amount cannot be null, zero or negative");
        }

        if (StringUtils.isEmpty(request.getFromBankCode())) {
            errors.put("fromBankCode", "fromBankCode cannot be empty or null");
        }

        if (StringUtils.isEmpty(request.getToBankCode())) {
            errors.put("toBankCode", "toBankCode cannot be empty or null");
        }

        if (!errors.isEmpty()) {
            throw new PreValidationException(errors);
        }

    }

    public void validateTransactionRequeryRequest (String transactionId)
        throws PreValidationException {

        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isEmpty(transactionId)) {
            errors.put("transactionId", "transactionId cannot be empty or null");
        }

        if (!errors.isEmpty()) {
            throw new PreValidationException(errors);
        }

    }

}

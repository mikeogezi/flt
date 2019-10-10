package mikeogezi;

import java.util.Optional;

import com.google.gson.Gson;
import mikeogezi.daos.AccountDao;
import mikeogezi.daos.TransactionDao;
import mikeogezi.exceptions.PreValidationException;
import mikeogezi.models.*;
import mikeogezi.utils.ApplicationUtils;
import mikeogezi.utils.PreValidation;
import mikeogezi.utils.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class ApplicationController {

    private Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private Gson gson;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index () {
        return "Flutterwave Test";
    }

    @RequestMapping(value = "/api/accountEnquiry", method = RequestMethod.POST)
    public ResponseEntity<AccountEnquiryResponse> makeAccountEnquiry (@Valid @RequestBody AccountEnquiryRequest request) {
        log.info("AccountEnquiryRequest: {}", gson.toJson(request));

        AccountEnquiryResponse response = new AccountEnquiryResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            PreValidation.getInstance().validateAccountEnquiryRequest(request);
        }
        catch (PreValidationException e) {
            response.setType(ResponseType.VALIDATION_ERROR);
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }

        Optional<Account> maybeAccount = accountDao.getBy(request.getBankCode(), request.getAccountNumber());
        if (maybeAccount.isPresent()) {
            response.setType(ResponseType.SUCCESS);
        }
        else {
            response.setType(ResponseType.FAILURE);
            response.setResponseMessage("ACCOUNT_NOT_FOUND");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, status);
    }


    @RequestMapping(value = "/api/transactionRequery/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<TransactionRequeryResponse> makeRequery
            (@PathVariable @NotNull(message = "transactionId cannot be null or empty") String transactionId,
             @RequestParam @NotNull(message = "token cannot be null or empty") String token) {

        log.info("transactionId: {}", transactionId);

        TransactionRequeryResponse response = new TransactionRequeryResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            PreValidation.getInstance().validateTransactionRequeryRequest(transactionId);
        }
        catch (PreValidationException e) {
            response.setType(ResponseType.VALIDATION_ERROR);
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }

        Optional<Transaction> maybeTransaction = transactionDao.getBy(transactionId);
        if (maybeTransaction.isPresent()) {
            response.setType(ResponseType.SUCCESS);
            response.addTransactionInfo(maybeTransaction.get());
        }
        else {
            response.setType(ResponseType.FAILURE);
            response.setResponseMessage("TRANSACTION_NOT_FOUND");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, status);
    }

    @RequestMapping(value = "/api/transfer", method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<TransferResponse> makeTransfer (@RequestParam String resultIn, @Valid @RequestBody TransferRequest request) {
        final String transactionId = ApplicationUtils.generateTransactionId();
        request.setTransactionId(transactionId);
        log.info("TransferRequest: {}", gson.toJson(request));

        TransferResponse response = new TransferResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            PreValidation.getInstance().validateTransferRequest(request);
        }
        catch (PreValidationException e) {
            response.setType(ResponseType.VALIDATION_ERROR);
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }

        /**
         * Validate account details of both the sender and receiver
         */
        if (!accountDao.getBy(request.getToBankCode(), request.getToAccountNumber()).isPresent() ||
                !accountDao.getBy(request.getFromBankCode(), request.getFromAccountNumber()).isPresent()) {

            response.setType(ResponseType.VALIDATION_ERROR);
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }


        if (resultIn.equals("success")) {
            response = makeSuccessfulTransfer(request);
        }
        /**
         * Requery is used here
         */
        else if (resultIn.equals("error")) {
            response = makeErrorTransfer(request);
        }
        else {
            response = makeFailureTransfer(request);
        }

        response.setTransactionId(transactionId);
        return new ResponseEntity<>(response, status);
    }

    /**
     * This transaction succeeds
     */
    private TransferResponse makeSuccessfulTransfer (TransferRequest request) {
        TransferResponse response = buildSuccessfulResponse(request);
        return response;
    }

    /**
     * This transaction will clearly fail
     */
    private TransferResponse makeFailureTransfer (TransferRequest request) {
        TransferResponse response = buildFailureResponse(request);
        return response;
    }

    /**
     * This transaction returns an error
     *
     * We will requery to check its status
     *
     * The requery will let us know if:
     *  1. the transfer failed outright or
     *  2. it was actually successful but we weren't properly told
     */
    private TransferResponse makeErrorTransfer (TransferRequest request) {
        TransferResponse response = buildErrorResponse(request);

        Optional<Transaction> maybeTransaction = transactionDao.getBy(request.getTransactionId());
        if (maybeTransaction.isPresent()) {
            Transaction transaction = maybeTransaction.get();
            /**
             * Requery reveals that the transaction was actually successful
             */
            if (transaction.getResponseType().equals(ResponseType.SUCCESS)) {
                response.setType(ResponseType.SUCCESS);
            }
            /**
             * Requery reveals that the transaction wasn't successful
             */
            else {
                response.setType(transaction.getResponseType());
            }
        }
        /**
         * We are now sure that the transaction failed since no record of it has been found
         */
        else {
            response.setType(ResponseType.FAILURE);
        }

        return response;
    }


    TransferResponse buildSuccessfulResponse (TransferRequest request) {
        return buildResponse(request, ResponseType.SUCCESS);
    }

    TransferResponse buildErrorResponse (TransferRequest request) {
        return buildResponse(request, ResponseType.ERROR);
    }

    TransferResponse buildFailureResponse (TransferRequest request) {
        return buildResponse(request, ResponseType.FAILURE);
    }

    private TransferResponse buildResponse (TransferRequest request, ResponseType responseType) {
        if (responseType == ResponseType.SUCCESS || responseType == ResponseType.FAILURE) {
            Transaction transaction = request.buildTransaction(responseType);
            transactionDao.save(transaction);
        }
        /**
         * To simulate the randomness of the transaction with the error response actually having gone through
         *
         * There's a 33% chance of a success
         * There's a 33% chance of a failure
         * There's a 34% chance of the transaction not being in the database, same as failure
         */
        else {
            double randomNumber = Math.random();
            if (randomNumber <= 0.33) {
                Transaction transaction = request.buildTransaction(ResponseType.SUCCESS);
                transactionDao.save(transaction);
            }
            else if (randomNumber > 0.33 && randomNumber <= 0.66) {
                Transaction transaction = request.buildTransaction(ResponseType.FAILURE);
                transactionDao.save(transaction);
            }
            else {}
        }

        TransferResponse response = new TransferResponse();
        response.setType(responseType);
        return response;
    }

}

package com.iw.application.controllers;

import com.iw.application.service.BankAccountService;
import org.iban4j.Iban;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
@ResponseStatus(HttpStatus.OK)
public class TransferController {
    private static final String ILLEGAL_TRANSACTION_TYPE_TEXT = "Illegal transaction type.";
    BankAccountService bankAccountService;

    public TransferController(@Autowired BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping()
    public String makeTransfer(@RequestBody String body) {
        JSONObject object = new JSONObject(body);
        String transactionStatus = (String) object.get("transactionStatus");
        String transactionType = (String) object.get("transactionType");
        String ibanString = (String) object.get("iban");
        String concept = (String) object.get("concept");
        Integer value = (Integer) object.get("value");
        String issuer = (String) object.get("issuer");
        //String id = (String) object.get("id");

        double amount = value.doubleValue();
        Iban iban = Iban.valueOf(ibanString);

        switch (transactionType) {
            case "WITHDRAWAL" -> bankAccountService.makeWithdrawal(iban, value);
            case "DEPOSIT" -> bankAccountService.makeDeposit(iban, value);
            default -> throw new IllegalArgumentException(ILLEGAL_TRANSACTION_TYPE_TEXT);
        }

        object.remove(transactionStatus);
        object.put("transactionStatus", "ACCEPTED");
        return object.toString();
    }
}

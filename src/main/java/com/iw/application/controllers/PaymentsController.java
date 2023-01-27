package com.iw.application.controllers;

import com.iw.application.data.service.BankAccountService;
import com.iw.application.data.service.CreditCardService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private static final String ILLEGAL_TRANSACTION_TYPE_TEXT = "Illegal transaction type.";
    BankAccountService bankAccountService;
    CreditCardService creditCardService;

    public PaymentsController(@Autowired BankAccountService bankAccountService,
                              @Autowired CreditCardService creditCardService) {
        this.bankAccountService = bankAccountService;
        this.creditCardService = creditCardService;
    }

    @PostMapping()
    public String makePayment(@RequestBody String body) {
        JSONObject object = new JSONObject(body);
        String paymentStatus = (String) object.get("paymentStatus");
        String cardNumber = (String) object.get("cardNumber");
        Integer month = (Integer) object.get("month");
        Integer year = (Integer) object.get("year");
        String csc = (String) object.get("csc");
        Integer value = (Integer) object.get("value");
        String type = (String) object.get("type");
        String shop = (String) object.get("shop");
        Integer securityToken = (Integer) object.get("securityToken");
        //String id = (String) object.get("id");

        double amount = value.doubleValue();

        creditCardService.makePayment(cardNumber, month, year, csc, amount, type, securityToken);

        object.remove(paymentStatus);
        object.put("paymentStatus", "ACCEPTED");
        return object.toString();
    }
}

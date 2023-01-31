package com.iw.application.controllers;

import com.iw.application.data.entity.PaymentEntity;
import com.iw.application.data.repositories.CreditCardRepository;
import com.iw.application.data.repositories.PaymentTokenRepository;
import com.iw.application.service.BankAccountService;
import com.iw.application.service.CreditCardService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private static final String REJECTED = "REJECTED";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String SECURITY_TOKEN_REQUIRED = "SECURITY_TOKEN_REQUIRED";
    private static final String REQUESTED = "REQUESTED";

    private static final String ILLEGAL_PAYMENT_STATUS = "Illegal payment status.";
    private static final String ILLEGAL_PARAMETERS = "Illegal parameters.";
    BankAccountService bankAccountService;
    CreditCardService creditCardService;

    private final PaymentTokenRepository paymentTokenRepository;
    private final CreditCardRepository creditCardRepository;

    public PaymentsController(@Autowired BankAccountService bankAccountService,
                              @Autowired CreditCardService creditCardService,
                              @Autowired PaymentTokenRepository paymentTokenRepository,
                              CreditCardRepository creditCardRepository) {
        this.bankAccountService = bankAccountService;
        this.creditCardService = creditCardService;
        this.paymentTokenRepository = paymentTokenRepository;
        this.creditCardRepository = creditCardRepository;
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
        String operationId = (String) object.get("id");

        double amount = value.doubleValue();

        if (!creditCardService.checkData(cardNumber, month, year, csc)) {
            object.remove(paymentStatus);
            object.put("paymentStatus", REJECTED);
            return object.toString();
        }

        if (paymentStatus.equals(SECURITY_TOKEN_REQUIRED)) {
            PaymentEntity payment = paymentTokenRepository.findByOperationId(operationId);
            if (payment.getToken() == securityToken
                && payment.getAmount() == amount
                && payment.getCardNumber().equals(cardNumber)
                && payment.getOperationId().equals(operationId)) {

                creditCardService.makePayment(cardNumber, amount);
                object.remove(paymentStatus);
                object.put("paymentStatus", ACCEPTED);
            } else {
                throw new IllegalArgumentException(ILLEGAL_PARAMETERS);
            }
        } else if (paymentStatus.equals(REQUESTED)){
            if (amount <= 10) {
                creditCardService.makePayment(cardNumber, amount);
                object.remove(paymentStatus);
                object.put("paymentStatus", ACCEPTED);
            } else {
                PaymentEntity paymentEntity = new PaymentEntity(securityToken, operationId, cardNumber, amount);
                paymentTokenRepository.save(paymentEntity);
                object.remove(paymentStatus);
                object.put("paymentStatus", REQUESTED);
            }
        } else {
            throw new IllegalArgumentException(ILLEGAL_PAYMENT_STATUS);
        }

        // TODO online sms offline pin -> steht im bild
        return object.toString();
    }
}

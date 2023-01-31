package com.iw.application.views.admin;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.service.CreditCardService;
import com.iw.application.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Admin Dashboard")
@Route(value = "admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminManageView extends VerticalLayout {
    private final UserService userService;
    private final CreditCardService creditCardService;

    VerticalLayout editScreen = new VerticalLayout();


    public AdminManageView(@Autowired UserService userService, @Autowired CreditCardService creditCardService) {
        this.userService = userService;
        this.creditCardService = creditCardService;

        add(addUserSelection());
    }

    private Component addUserSelection() {
        HorizontalLayout buttons = new HorizontalLayout();

        Select<UserEntity> userSelect = new Select<>();
        Select<BankAccountEntity> bankAccountSelect = new Select<>();

        userSelect.setItems(userService.getAllUsers());
        userSelect.addValueChangeListener(userEvent -> {
            bankAccountSelect.setItems(userEvent.getValue().getBankAccounts());
        });

        bankAccountSelect.addValueChangeListener(bankAccountEvent -> {
            renderEditScreen(bankAccountEvent.getValue());
        });

        buttons.add(userSelect, bankAccountSelect);
        return buttons;
    }

    private void renderEditScreen(BankAccountEntity bankAccount) {
        this.remove(editScreen);
        editScreen = new VerticalLayout();

        if (bankAccount == null) {
            editScreen.add(new Span("No bank account selected"));
        } else {
            H2 bankAccountHeader = new H2("Bank Account");
            editScreen.add(bankAccountHeader);

            HorizontalLayout ibanLayout = new HorizontalLayout();
            Span ibanLabel = new Span("IBAN: ");
            Span iban = new Span(bankAccount.getIban().toString());
            ibanLayout.add(ibanLabel, iban);

            HorizontalLayout balanceLayout = new HorizontalLayout();
            Span balanceLabel = new Span("Balance: ");
            Span balance = new Span(String.valueOf(bankAccount.getBalance()));
            balanceLayout.add(balanceLabel, balance);

            editScreen.add(ibanLayout, balanceLayout);


            H2 creditCardHeader = new H2("Credit card");
            editScreen.add(creditCardHeader);

            CreditCardEntity creditCard = creditCardService.getCreditCardToBankAccount(bankAccount);

            VerticalLayout creditCardLayout = new VerticalLayout();
            if (creditCard == null) {
                Span noCreditCard = new Span("No credit card");
                creditCardLayout.add(noCreditCard);
            } else {
                HorizontalLayout cardNumberLayout = new HorizontalLayout();
                Span cardNumberLabel = new Span("CardNumber: ");
                Span cardNumber = new Span(creditCard.getCardNumber());
                cardNumberLayout.add(cardNumberLabel, cardNumber);

                HorizontalLayout buttonLayout = new HorizontalLayout();
                Button deactivate = new Button("Deactivate");
                deactivate.addClickListener(click -> {
                    creditCardService.setActive(creditCard, false);
                    this.remove(editScreen);
                    renderEditScreen(bankAccount);
                });
                Button activate = new Button("Activate");
                activate.addClickListener(click -> {
                    creditCardService.setActive(creditCard, true);
                    renderEditScreen(bankAccount);
                });
                if (creditCard.isActive()) {
                    buttonLayout.add(deactivate);
                } else {
                    buttonLayout.add(activate);
                }
                creditCardLayout.add(buttonLayout);
            }

            editScreen.add(creditCardLayout);
        }

        this.add(editScreen);
    }

    private Component addBankAccountEditor(BankAccountEntity bankAccount) {
        VerticalLayout bankAccountEditor = new VerticalLayout();
        return bankAccountEditor;
    }

}

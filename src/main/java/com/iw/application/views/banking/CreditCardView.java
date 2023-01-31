package com.iw.application.views.banking;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.service.BankAccountService;
import com.iw.application.service.CreditCardService;
import com.iw.application.service.UserService;
import com.iw.application.views.InternalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Route(value = "cards", layout = InternalLayout.class)
@PageTitle("Manage Credit Cards | ucaBank")
@PermitAll
public class CreditCardView extends VerticalLayout {

    private final BankAccountService bankAccountService;
    private final UserService userService;
    private final CreditCardService creditCardService;

    private final Button createCreditCard = new Button("Create Credit Card");

    private final Select<BankAccountEntity> bankAccountSelect= new Select<>();

    public CreditCardView(@Autowired BankAccountService bankAccountService,
                          @Autowired UserService userService, @Autowired CreditCardService creditCardService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
        this.creditCardService = creditCardService;

        add(createBankAccountSelect());
    }

    private void renderCreditCardScreen(BankAccountEntity bankAccount) {
        CreditCardEntity creditCard = creditCardService.getCreditCardToBankAccount(bankAccount);
        if (creditCard != null) {
            this.add(createEditCreditCardView(creditCard));
        } else {
            this.add(createCreateCreditCardButton(bankAccount));
        }
    }

    private Component createBankAccountSelect() {
        bankAccountSelect.setLabel("Select Bank Account");
        bankAccountSelect.setItemLabelGenerator(bankAccount -> bankAccount.getIban().toString());
        bankAccountSelect.setItems(userService.getCurrentUser().getBankAccounts());
        bankAccountSelect.addValueChangeListener(bankAccountEntityEvent -> {
            renderCreditCardScreen(bankAccountEntityEvent.getValue());
        });
        return bankAccountSelect;
    }

    private Component createEditCreditCardView(CreditCardEntity creditCard) {
        VerticalLayout editCreditCardLayout = new VerticalLayout();

        HorizontalLayout cardNumberLayout = new HorizontalLayout();
        Span cardNumberLabel = new Span("Card number: ");
        Span cardNumber = new Span(creditCard.getCardNumber());
        cardNumberLayout.add(cardNumberLabel, cardNumber);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        HorizontalLayout issueDateLayout = new HorizontalLayout();
        Span issueDateLabel = new Span("Issue Date: ");
        Span issueDate = new Span(dateFormat.format(creditCard.getIssueDate()));
        issueDateLayout.add(issueDateLabel, issueDate);

        HorizontalLayout validityDateLayout = new HorizontalLayout();
        Span validityDateLabel = new Span("Validity Date: ");
        Span validityDate = new Span(dateFormat.format(creditCard.getValidityDate()));
        validityDateLayout.add(validityDateLabel, validityDate);

        HorizontalLayout debtLayout = new HorizontalLayout();
        Span debtLabel = new Span("Current Debt: ");
        Span debt = new Span(String.valueOf(creditCard.getCurrentDebt()));
        debtLayout.add(debtLabel, debt);

        HorizontalLayout statusLayout = new HorizontalLayout();
        Span statusLabel = new Span("Activity status: ");
        Span status = new Span(String.valueOf(creditCard.isActive()));
        statusLayout.add(statusLabel, status);

        HorizontalLayout limitLayout = new HorizontalLayout();
        Span limitLabel = new Span("Daily limit: ");
        Span limit = new Span(String.valueOf(creditCard.getCardLimit()));
        limitLayout.add(limitLabel, limit);

        HorizontalLayout setLimitLayout = new HorizontalLayout();
        NumberField setLimit = new NumberField("New Limit");
        Button saveLimit = new Button("Save");
        saveLimit.addClickListener(click -> {
            if (!setLimit.isEmpty()) {
                creditCardService.setLimit(creditCard, setLimit.getValue());
                this.remove(editCreditCardLayout);
                this.add(createEditCreditCardView(creditCard));
            }
        });
        setLimitLayout.add(setLimit, saveLimit);

        Button deactivate = new Button("Deactivate");
        deactivate.addClickListener(click -> {
            creditCardService.setActive(creditCard, false);
            this.remove(editCreditCardLayout);
            this.add(createEditCreditCardView(creditCard));
        });
        Button activate = new Button("Activate");
        activate.addClickListener(click -> {
            creditCardService.setActive(creditCard, true);
            this.remove(editCreditCardLayout);
            this.add(createEditCreditCardView(creditCard));
        });
        HorizontalLayout button = new HorizontalLayout();
        if (creditCard.isActive()) {
            button.add(deactivate);
        } else {
            button.add(activate);
        }

        editCreditCardLayout.add(cardNumberLayout, issueDateLayout, validityDateLayout, debtLayout, statusLayout,
                limitLayout, setLimitLayout, button);
        return editCreditCardLayout;
    }

    private Component createCreateCreditCardButton(BankAccountEntity bankAccount) {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        createCreditCard.addClickListener(event -> {
            CreditCardEntity creditCard = creditCardService.createCreditCard(bankAccount);
            this.remove(buttonLayout);
            this.add(createEditCreditCardView(creditCard));
        });

        buttonLayout.add(createCreditCard);

        return buttonLayout;
    }
}

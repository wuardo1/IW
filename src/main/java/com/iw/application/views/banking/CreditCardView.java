package com.iw.application.views.banking;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.data.service.BankAccountService;
import com.iw.application.data.service.CreditCardService;
import com.iw.application.data.service.UserService;
import com.iw.application.views.InternalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route(value = "cards", layout = InternalLayout.class)
@PageTitle("Manage Credit Cards | ucaBank")
@PermitAll
public class CreditCardView extends VerticalLayout {

    BankAccountService bankAccountService;
    UserService userService;

    CreditCardService creditCardService;

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
            // TODO
        } else {
            this.add(createCreditCardButton(bankAccount));
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

    private Component createCreditCardButton(BankAccountEntity bankAccount) {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        createCreditCard.addClickListener(event -> {
            creditCardService.createCreditCard(bankAccount);
        });

        buttonLayout.add(createCreditCard);

        return buttonLayout;
    }
}

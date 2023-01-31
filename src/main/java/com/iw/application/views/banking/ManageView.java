package com.iw.application.views.banking;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.service.BankAccountService;
import com.iw.application.service.UserService;
import com.iw.application.views.InternalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route(value = "home", layout = InternalLayout.class)
@PageTitle("Manage Accounts | ucaBank")
@PermitAll
public class ManageView extends VerticalLayout implements AfterNavigationObserver {

    private final UserService userService;

    private final BankAccountService bankAccountService;

    private final Button addAccount = new Button("Add Account");

    private final Grid<BankAccountEntity> grid = new Grid<>();

    public ManageView(@Autowired BankAccountService bankAccountService,
                      @Autowired UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;

        addClassName("manage-view");
        setSizeFull();

        add(createButtonLayout());

        grid.setHeight("100%");
        grid.addComponentColumn(this::createItem);
        grid.setVerticalScrollingEnabled(true);
        add(grid);

        addAccount.addClickListener(event -> {
            userService.createBankAccount();
            getGridData(); // TODO move this ??
        });
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        addAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(addAccount);
        return buttonLayout;
    }

    private Component createItem(BankAccountEntity bankAccount) {
        HorizontalLayout item = new HorizontalLayout();

        VerticalLayout accountData = new VerticalLayout();
        accountData.setSpacing(false);

        HorizontalLayout ibanBox = new HorizontalLayout();
        Span ibanLabel = new Span("IBAN: ");
        Span iban = new Span(bankAccount.getIban().toString());
        ibanBox.add(ibanLabel, iban);

        HorizontalLayout balanceBox = new HorizontalLayout();
        Span balanceLabel = new Span("Balance: ");
        Span balance = new Span(String.valueOf(bankAccount.getBalance()));
        balanceBox.add(balanceLabel, balance);

        HorizontalLayout creditLineBox = new HorizontalLayout();
        Span creditLineLabel = new Span("Credit line: ");
        Span creditLine = new Span(String.valueOf(bankAccount.getCreditLine()));
        creditLineBox.add(creditLineLabel, creditLine);

        accountData.add(ibanBox, balanceBox, creditLineBox);

        HorizontalLayout accountManager = new HorizontalLayout();
        accountManager.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        TextField destinationAccount = new TextField("Destination IBAN");
        NumberField amount = new NumberField("Amount");
        Button transfer = new Button("Transfer");
        Button removeAccount = new Button("Remove Account");
        if (bankAccount.getBalance() != 0) {
            removeAccount.setEnabled(false);
        }
        removeAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        accountManager.add(destinationAccount, amount, transfer, removeAccount);

        transfer.addClickListener(event -> {
            try {
                bankAccountService.makeTransaction(
                        bankAccount.getIban(),
                        Iban.valueOf(destinationAccount.getValue()),
                        amount.getValue());
            } catch (IllegalArgumentException e) {
                System.out.println("hi");
                // TODO show error message on balance to low exception or illegal format or account not found
            }
            getGridData();
        });

        removeAccount.addClickListener(event -> {
            if (bankAccount.getBalance() == 0) {
                bankAccountService.removeBankAccount(bankAccount);
            }
            getGridData();
        });

        item.add(accountData, accountManager);
        return item;
    }

    private void getGridData() {
        UserEntity user = userService.getCurrentUser();
        grid.setItems(user.getBankAccounts());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        getGridData();
    }
}

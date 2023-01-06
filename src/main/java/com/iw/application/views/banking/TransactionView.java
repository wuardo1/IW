package com.iw.application.views.banking;

import com.iw.application.data.service.account.BankAccountService;
import com.iw.application.data.service.account.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("transaction")
@PageTitle("Transactions | ucaBank")
@AnonymousAllowed
public class TransactionView extends VerticalLayout {

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;

    private final IntegerField destinationAccount = new IntegerField("Destination Account");
    private final NumberField amount = new NumberField("Amount");
    private final Button approve = new Button("Approve");

    public TransactionView() {
        addClassName("manage-view");

        add(createButtonLayout());
        add(createInputLayout());

        approve.addClickListener(event -> {
            try {
                bankAccountService.makeTransaction(
                        userService.getCurrentUser().getBankAccounts().stream().findFirst().get().getAccountNumber(),
                        destinationAccount.getValue(),
                        amount.getValue());
            } catch (Exception e) {
                // TODO show error message on balance to low exception
            }
        });
    }

    private Component createInputLayout() {
        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.addClassName("input-layout");
        inputLayout.add(amount, destinationAccount);
        return inputLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        approve.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(approve);
        return buttonLayout;
    }


}

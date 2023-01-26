package com.iw.application.views.banking;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.TransactionEntity;
import com.iw.application.data.service.BankAccountService;
import com.iw.application.data.service.TransactionService;
import com.iw.application.data.service.UserService;
import com.iw.application.views.InternalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Route(value = "transactions", layout = InternalLayout.class)
@PageTitle("View Transactions | ucaBank")
@PermitAll
public class TransactionHistoryView extends VerticalLayout {

    UserService userService;

    BankAccountService bankAccountService;

    TransactionService transactionService;

    private final Grid<TransactionEntity> grid = new Grid<>();

    private final Select<BankAccountEntity> bankAccountSelect= new Select<>();

    public TransactionHistoryView(@Autowired UserService userService,
                                  @Autowired BankAccountService bankAccountService,
                                  @Autowired TransactionService transactionService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;

        setSizeFull();

        add(createBankAccountSelect());

        grid.setHeight("100%");
        grid.addComponentColumn(this::createItem);
        grid.setVerticalScrollingEnabled(true);
        add(grid);
    }

    private Component createBankAccountSelect() {
        bankAccountSelect.setLabel("Select Bank Account");
        bankAccountSelect.setItemLabelGenerator(bankAccount -> bankAccount.getIban().toString());
        bankAccountSelect.setItems(userService.getCurrentUser().getBankAccounts());
        bankAccountSelect.addValueChangeListener(bankAccountEntityEvent -> {
            getGridData(bankAccountEntityEvent.getValue());
        });

        return bankAccountSelect;
    }

    private Component createItem(TransactionEntity transaction) {
        VerticalLayout item = new VerticalLayout();

        HorizontalLayout sourceLayout = new HorizontalLayout();
        Span sourceLabel = new Span("Source IBAN: ");
        Span source = new Span(transaction.getSourceIban());
        sourceLayout.add(sourceLabel, source);

        HorizontalLayout destinationLayout = new HorizontalLayout();
        Span destinationLabel = new Span("Destination IBAN: ");
        Span destination = new Span(transaction.getDestinationIban());
        destinationLayout.add(destinationLabel, destination);

        HorizontalLayout timeLayout = new HorizontalLayout();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Span timeLabel = new Span("Time: ");
        Span time = new Span(timeFormat.format(transaction.getDateExecuted()));
        timeLayout.add(timeLabel, time);

        HorizontalLayout dateLayout = new HorizontalLayout();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Span dateLabel = new Span("Date: ");
        Span date = new Span(dateFormat.format(transaction.getDateExecuted()));
        dateLayout.add(dateLabel, date);

        HorizontalLayout amountLayout = new HorizontalLayout();
        Span amountLabel = new Span("Amount: ");
        Span amount = new Span(String.valueOf(transaction.getAmount()));
        amountLayout.add(amountLabel, amount);

        item.add(sourceLayout, destinationLayout, timeLayout, dateLayout, amountLayout);
        return item;
    }

    private void getGridData(BankAccountEntity bankAccount) {
        List<TransactionEntity> transactions = transactionService.getTransactionsToBankAccount(bankAccount);
        grid.setItems(transactions);
    }
}

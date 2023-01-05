package com.iw.application.views.banking;

import com.iw.application.data.service.account.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.awt.*;
import java.util.Collection;


// TODO this doesnt work yet
@Route("manage")
@PageTitle("Manage Accounts")
@PermitAll
public class ManageView extends VerticalLayout {

    @Autowired
    UserService userService;

    Button newBankAccount = new Button("New Bank Account");
    public ManageView() {
        newBankAccount.addActionListener(e -> userService.createBankAccount());
        add(

        );

    }
}

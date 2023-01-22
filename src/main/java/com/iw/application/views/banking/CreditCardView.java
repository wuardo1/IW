package com.iw.application.views.banking;

import com.iw.application.data.service.BankAccountService;
import com.iw.application.data.service.UserService;
import com.iw.application.views.InternalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route(value = "cards", layout = InternalLayout.class)
@PageTitle("Manage Credit Cards | ucaBank")
@PermitAll
public class CreditCardView {

    BankAccountService bankAccountService;
    UserService userService;

    public CreditCardView(@Autowired BankAccountService bankAccountService,
                          @Autowired UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;


    }
}

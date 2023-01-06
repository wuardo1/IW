package com.iw.application.views.banking;

import com.iw.application.data.entity.account.User;
import com.iw.application.data.entity.account.UserGroup;
import com.iw.application.data.service.account.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;


// TODO this doesnt work yet
@Route("manage")
@PageTitle("Manage Accounts | ucaBank")
@AnonymousAllowed
public class ManageView extends VerticalLayout {

    @Autowired
    UserService userService;

    private Button register = new Button("Register");

    public ManageView() {
        addClassName("manage-view");

        add(createButtonLayout());

        register.addClickListener(event -> {
            userService.createBankAccount();
        });
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);
        return buttonLayout;
    }
}

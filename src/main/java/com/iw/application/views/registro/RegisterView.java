package com.iw.application.views.registro;

import com.iw.application.data.entity.account.User;
import com.iw.application.data.entity.account.UserGroup;
import com.iw.application.data.service.account.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@PageTitle("Register | ucaBank")
@Route("register")
@Uses(Icon.class)
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    @Autowired
    UserService userService;

    private EmailField mail = new EmailField("Email adress");
    private PasswordField password = new PasswordField("Password");

    private PasswordField passwordRepeated = new PasswordField("Repeat password");
    private Button register = new Button("Register");


    Binder<PasswordField> passwordBinder = new Binder<>();

    public RegisterView() {
        addClassName("register-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //binder.bindInstanceFields(this);
        clearForm();

        register.addClickListener(event -> {
            register(mail.getValue(), password.getValue());
        });
    }

    private void register(String mail, String password) {
        if (password.equals(passwordRepeated.getValue())
                && Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()
                && Pattern.compile(EMAIL_PATTERN).matcher(mail).matches()) {
            Collection<UserGroup> groups = new ArrayList<>();
            groups.add(UserGroup.ROLE_USER);
            User user = new User(mail, password, groups);
            userService.addUser(user);
            Notification.show("User registered");
            clearForm();
        } else {
            Notification.show("Check inputs");
        }
    }

    private void clearForm() {
        //binder.setBean(new User());
    }


    private Component createTitle() {
        return new H3("Register");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        mail.setErrorMessage("Please enter a valid email address");
        password.setHelperText(
                "A password must be at least 8 characters. It has to have at least one letter and one digit.");
        password.setPattern(PASSWORD_PATTERN);
        password.setErrorMessage("Not a valid password");
        passwordBinder.forField(passwordRepeated)
                        .withValidator(passw -> passw.equals(password.getValue()),
                                "The passwords do not match")
                                .bind(PasswordField::getValue, PasswordField::setValue);
        formLayout.add(mail, password, passwordRepeated);

        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);
        return buttonLayout;
    }
}

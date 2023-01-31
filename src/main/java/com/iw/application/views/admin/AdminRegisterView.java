package com.iw.application.views.admin;

import com.iw.application.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.regex.Pattern;

@PageTitle("Admin Dashboard")
@Route(value = "register-admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminRegisterView extends VerticalLayout {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    private final UserService userService;

    private final EmailField mail = new EmailField("Email address");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField passwordRepeated = new PasswordField("Repeat password");
    private final Button register = new Button("Register");

    Binder<PasswordField> passwordBinder = new Binder<>();

    public AdminRegisterView(@Autowired UserService userService) {
        this.userService = userService;

        setSizeFull();

        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(createFormLayout());
        add(createButtonLayout());
    }

    private void register(String mail, String password) {
        if (password.equals(passwordRepeated.getValue())
                && Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()
                && Pattern.compile(EMAIL_PATTERN).matcher(mail).matches()) {

            userService.addAdmin(mail, password);
            Notification.show("User registered");
        } else {
            Notification.show("Check inputs");
        }
    }

    private Component createFormLayout() {
        Div formContainer = new Div();
        formContainer.setMaxWidth(50, Unit.PERCENTAGE); // TODO redo

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
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

//        mail.addKeyPressListener(event -> {
//            registerDisabler(mail.getValue(), password.getValue(), passwordRepeated.getValue());
//        });
//
//        password.addKeyPressListener(event -> {
//            registerDisabler(mail.getValue(), password.getValue(), passwordRepeated.getValue());
//        });
//
//        passwordRepeated.addKeyPressListener(event -> {
//            registerDisabler(mail.getValue(), password.getValue(), passwordRepeated.getValue());
//        });

        formContainer.add(formLayout);
        return formContainer;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        register.addClickListener(event -> {
            register(mail.getValue(), password.getValue());
        });

        buttonLayout.add(register);
        return buttonLayout;
    }

    private void registerDisabler(String mail, String password, String passwordRepeated) {
        if (password.equals(passwordRepeated)
                && Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()
                && Pattern.compile(EMAIL_PATTERN).matcher(mail).matches()) {
            register.setEnabled(true);
        } else {
            register.setEnabled(false);
        }
    }
}

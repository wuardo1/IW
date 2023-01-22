package com.iw.application.views.register;

import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.entity.UserGroupEntity;
import com.iw.application.data.service.UserService;
import com.iw.application.views.PublicLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
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
@Route(value = "register", layout = PublicLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    private final UserService userService;

    private final EmailField mail = new EmailField("Email adress");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField passwordRepeated = new PasswordField("Repeat password");
    private final Button register = new Button("Register");


    Binder<PasswordField> passwordBinder = new Binder<>();

    public RegisterView(@Autowired UserService userService) {
        this.userService = userService;

        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

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
            Collection<UserGroupEntity> groups = new ArrayList<>();
            groups.add(UserGroupEntity.ROLE_USER);
            UserEntity userEntity = new UserEntity(mail, password, groups);
            userService.addUser(userEntity);
            Notification.show("User registered");
            clearForm();
            UI.getCurrent().navigate("login"); // TODO logged in after register
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
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //register.setEnabled(false);
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

package com.iw.application.views.welcome;

import com.iw.application.views.PublicLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Hello World")
@Route(value = "", layout = PublicLayout.class)
@AnonymousAllowed
public class WelcomeView extends HorizontalLayout {


    public WelcomeView() {
        Span text = new Span("Welcome to the famous UCA Bank... login or register for the best service in the world!");
        add(text);
    }

}

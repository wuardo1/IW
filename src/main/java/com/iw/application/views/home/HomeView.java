package com.iw.application.views.home;

import com.iw.application.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class HomeView extends HorizontalLayout {

    public HomeView() {
        add(new H1("Welcome to Vaadin CRM"));
    }

}

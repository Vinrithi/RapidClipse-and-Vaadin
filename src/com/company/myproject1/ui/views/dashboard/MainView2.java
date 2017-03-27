package com.company.myproject1.ui.views.dashboard;

import com.vaadin.server.Page;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView2 extends HorizontalLayout {

    public MainView2() {
        setSizeFull();
        addStyleName("mainview");

        Page.getCurrent().setUriFragment("main");

        addComponent(new DashboardMenu());

        final ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new DashboardNavigator(content);
    }
}

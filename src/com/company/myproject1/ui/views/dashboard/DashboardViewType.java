package com.company.myproject1.ui.views.dashboard;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.company.myproject1.ui.views.bills.BillsView;
import com.company.myproject1.ui.views.expenses.Expenses;
import com.company.myproject1.ui.views.landlords.LandlordsView;
import com.company.myproject1.ui.views.properties.PropertiesView;
import com.company.myproject1.ui.views.rents.RentView;
import com.company.myproject1.ui.views.reports.Reports;
import com.company.myproject1.ui.views.tenants.TenantsView;
import com.company.myproject1.ui.views.units.UnitsView;

/*
 * Created by elon on 3/7/2017.
 */
public enum DashboardViewType {
    DASHBOARD("dashboard", Dashboard.class, FontAwesome.HOME, true),
    LANDLORDS("landlords", LandlordsView.class, FontAwesome.BRIEFCASE, false),
    PROPERTIES("properties", PropertiesView.class, FontAwesome.BANK, false),
    UNITS("units", UnitsView.class, FontAwesome.KEY, false),
    TENANTS("tenants", TenantsView.class, FontAwesome.USERS, false),
    RENT("rent", RentView.class, FontAwesome.DOLLAR, false),
    BILLS("bills", BillsView.class, FontAwesome.BITBUCKET_SQUARE, false),
    EXPENSES("expenses", Expenses.class, FontAwesome.CREDIT_CARD, false),
    REPORTS("reports", Reports.class, FontAwesome.BAR_CHART_O, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
                              final Class<? extends View> viewClass, final Resource icon,
                              final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}

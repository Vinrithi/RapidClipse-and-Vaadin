package com.company.myproject1.ui.views.dashboard;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.component.ChartUtils;
import com.company.myproject1.ui.component.PropertiesStatsPieChart;
import com.company.myproject1.ui.component.RentStatsHorizontalBarChart;
import com.company.myproject1.ui.component.SparklineChart;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Created by elon on 3/7/2017.
 */
public class Dashboard extends Panel implements View {

    private Label titleLabel;
    private final VerticalLayout root;
    private CssLayout dashboardPanels;

    public Dashboard() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        DashboardEventBus.register(this);

        root = new VerticalLayout();
        /*root.setSizeFull();*/
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);

        root.addComponent(buildHeader());

        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
        root.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutEvents.LayoutClickEvent event) {
                DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
            }
        });
    }

    private Component buildDashboardStats() {
        CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");
        Responsive.makeResponsive(sparks);

        double[] valuesForRevenue = {1.05,0.81,0.79,1.24};
        String[] labels = {"December 2016", "January 2017", "February 2017", "March 2017"};
        int proposedMax = 2;
        int proposedMin = 0;

        SparklineChart s = new SparklineChart("Total Revenue", "M", "KES ",
                ChartUtils.valoBlue, valuesForRevenue, labels, proposedMax, proposedMin);
        sparks.addComponent(s);

        double[] valuesForLandlords = {10,6,18,20};
        //String[] labels = {"October 2016", "November 2016", "December 2016", "January 2017", "February 2017", "March 2017"};
        proposedMax = 20;
        proposedMin = 8;

        s = new SparklineChart("Landlords", "", "",
                ChartUtils.valoGreen, valuesForLandlords, labels, proposedMax, proposedMin);
        sparks.addComponent(s);

        double[] valuesForProperties = {65,80,150,200};
        //String[] labels = {"October 2016", "November 2016", "December 2016", "January 2017", "February 2017", "March 2017"};
        proposedMax = 200;
        proposedMin = 50;

        s = new SparklineChart("Properties", "", "",
                ChartUtils.valoYellow, valuesForProperties, labels, proposedMax, proposedMin);
        sparks.addComponent(s);

        double[] valuesForTenants = {3.7,4,3.5,6};
        //String[] labels = {"October 2016", "November 2016", "December 2016", "January 2017", "February 2017", "March 2017"};
        proposedMax = 6;
        proposedMin = 3;

        s = new SparklineChart("Tenants", "K", "",
                ChartUtils.valoRed, valuesForTenants, labels, proposedMax, proposedMin);
        sparks.addComponent(s);

        return sparks;
    }


    private Component buildHeader() {
        VerticalLayout verticalLayoutHeader = new VerticalLayout();
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout subHeader = new HorizontalLayout();
        //header.addStyleName("viewheader");
        header.setWidth("100%");
        header.setSpacing(true);

        //subHeader.addStyleName(ValoTheme.);
        subHeader.setWidth("100%");
        subHeader.setSpacing(true);

        titleLabel = new Label("Dashboard");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        titleLabel = new Label("Welcome to KejaPay admin panel");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H3);
        //titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        subHeader.addComponent(titleLabel);

        verticalLayoutHeader.addComponents(header, subHeader);
        verticalLayoutHeader.setComponentAlignment(subHeader, Alignment.BOTTOM_LEFT);

        return verticalLayoutHeader;
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        dashboardPanels.addComponent(buildDashboardStats());
        dashboardPanels.addComponent(buildRentStatsChart());
        dashboardPanels.addComponent(buildPropertiesStatsPieChart());
        //dashboardPanels.addComponent(buildPopularMovies());

        return dashboardPanels;
    }

    private Component buildRentStatsChart() {

        String[] labels = {"June 2016", "July 2016", "August 2016", "September 2016", "October 2016", "November 2016", "December 2016", "January 2017", "February 2017", "March 2017"};
        List<Double> values = new ArrayList<>();
        values.add(0.3);values.add(0.5);values.add(0.7);values.add(0.7);values.add(0.8);values.add(0.9);values.add(1.05);values.add(0.81);values.add(0.79);values.add(1.24);

        RentStatsHorizontalBarChart rentStatsHorizontalBarChart = new RentStatsHorizontalBarChart(labels, ChartUtils.valoBlue, values);
        /*rentStatsHorizontalBarChart.setSizeFull();*/
        rentStatsHorizontalBarChart.setMargin(new MarginInfo(true, true, true, true));

        return createContentWrapper(rentStatsHorizontalBarChart);
    }

    private Component buildPropertiesStatsPieChart(){
        String[] labels = {"Arkville Gardens","Visage Villas", "Semansar Apartments", "Reddox Court", "Oakwood Gardens"};
        String[] colors = {ChartUtils.valoGreen, ChartUtils.valoRed, ChartUtils.valoBlue, ChartUtils.valoCyan, ChartUtils.valoYellow};
        double[] values = {12545, 33458, 54445, 54445, 99979};

        PropertiesStatsPieChart propertiesStatsPieChart = new PropertiesStatsPieChart(labels, values, colors);
        propertiesStatsPieChart.setMargin(new MarginInfo(true, true, true, true));

        return createContentWrapper(propertiesStatsPieChart);
    }

    private Component buildNotes() {
        TextArea notes = new TextArea("Notes");
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");
        return panel;
    }

    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");
        //---------------
        //slot.setSizeFull();
        //---------------
        //setSizeFull();

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);
        //---------------
        //card.setSizeFull();

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);
        //content.setSizeFull();

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuBar.MenuItem max = tools.addItem("", FontAwesome.EXPAND, new MenuBar.Command() {

            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }

    private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }


    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {

    }
}

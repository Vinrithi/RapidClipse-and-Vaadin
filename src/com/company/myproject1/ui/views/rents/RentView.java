package com.company.myproject1.ui.views.rents;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.data_from_db.RentService;
import com.company.myproject1.ui.domain.Rent;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.haijian.CSVExporter;
import org.vaadin.haijian.ExcelExporter;
import org.vaadin.haijian.PdfExporter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/*
 * Created by ochomoswill on 3/14/2017.
 */
public class RentView extends VerticalLayout implements View {


    @Autowired
    private RentService rentService = new RentService();
    private Rent rent = new Rent();




    public final Table rentTable = new Table();
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final String[] DEFAULT_COLLAPSIBLE = {"propertyId", "unitId", "month", "year","payee", "amountPaid", "receivedBy", "datePaid", "transactionCode" };



    public RentView() {
        setSizeFull();
        setMargin(true);
        addStyleName("transactions");
        DashboardEventBus.register(this);

        addComponent(buildToolbar());

        Panel tablePanel = new Panel();
        tablePanel.setSizeFull();


        rentTable.setSizeFull();
        rentTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        rentTable.addStyleName(ValoTheme.TABLE_COMPACT);
        rentTable.setSelectable(true);

        rentTable.setColumnCollapsingAllowed(true);/*
        rentTable.setColumnCollapsible("time", false);
        rentTable.setColumnCollapsible("price", false);*/

        rentTable.setColumnReorderingAllowed(true);
        updateTable();
        rentTable.setSortContainerPropertyId("time");
        rentTable.setSortAscending(false);

        rentTable.setVisibleColumns("propertyId", "unitId", "month", "year","payee", "amountPaid", "receivedBy", "datePaid", "transactionCode");
        rentTable.setColumnHeaders("Property ID","Unit ID", "Month", "Year", "Payee", "Amount Paid","Received By", "Date Paid", "Transaction Code");

        // Allow dragging items to the reports menu
        rentTable.setDragMode(Table.TableDragMode.MULTIROW);
        //table.setMultiSelect(true);

        rentTable.setImmediate(true);

        VerticalLayout tableExport = new VerticalLayout();

        HorizontalLayout buildTableToolbar = new HorizontalLayout();

        Table sampleTable;
        try {
            sampleTable = createExampleTable(rentTable);
            //tableExport.addComponent(sampleTable);
            ExcelExporter excelExporter = new ExcelExporter();
            excelExporter.setDateFormat("yyyy-MM-dd");
            excelExporter.setTableToBeExported(sampleTable);
            //tableExport.addComponent(excelExporter);
            excelExporter.setCaption("Export to Excel");
            excelExporter.addStyleName("small");
            excelExporter.addStyleName("friendly");
            CSVExporter csvExporter = new CSVExporter();
            csvExporter.setCaption("Export to CSV");
            csvExporter.setContainerToBeExported(sampleTable
                    .getContainerDataSource());
            csvExporter.setVisibleColumns(sampleTable.getVisibleColumns());
            csvExporter.addStyleName("small");
            csvExporter.addStyleName("primary");
            //tableExport.addComponent(csvExporter);
            PdfExporter pdfExporter = new PdfExporter(sampleTable);
            pdfExporter.setCaption("Export to PDF");
            pdfExporter.setWithBorder(true);
            pdfExporter.setSizeFull();
            pdfExporter.addStyleName("small");
            pdfExporter.addStyleName("danger");
            //tableExport.addComponent(pdfExporter);


            buildTableToolbar.addComponents(excelExporter, csvExporter, pdfExporter);
            buildTableToolbar.setSpacing(true);
            tableExport.addComponents(buildTableToolbar,sampleTable);
            tableExport.setSpacing(true);

            excelExporter.setDownloadFileName("demo-excel-exporter");
            pdfExporter.setDownloadFileName("demo-pdf-exporter.pdf");
            csvExporter.setDownloadFileName("demo-csv-exporter.csv");
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        rentTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                //Manage collection and manually fetch property of table
                rent = (Rent) event.getItemId();
                BeanFieldGroup.bindFieldsUnbuffered(rent, this);
                RentWindow.open(rent, true);
            }
        });

        //tablePanel.setContent(rentTable);
        tablePanel.setContent(tableExport);
        tablePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        addComponent(tablePanel);
        setExpandRatio(tablePanel, 1);
    }

    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        DashboardEventBus.unregister(this);
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label("Rent Payment");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        HorizontalLayout tools = new HorizontalLayout(buildFilter());
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    private Table createExampleTable(Table table) throws UnsupportedOperationException, ParseException {
        return table;
    }


    private Component buildFilter() {
        final TextField filter = new TextField();
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
                Container.Filterable data = (Container.Filterable) rentTable.getContainerDataSource();
                data.removeAllContainerFilters();
                data.addContainerFilter(new Container.Filter() {
                    @Override
                    public boolean passesFilter(final Object itemId,final Item item) {
                        if (event.getText() == null
                                || event.getText().equals("")) {
                            return true;
                        }

                        return filterByProperty("month", item,event.getText())
                                || filterByProperty("year", item,event.getText());

                    }

                    @Override
                    public boolean appliesToProperty(final Object propertyId) {
                        if (propertyId.equals("month")
                                || propertyId.equals("year")) {
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        filter.addShortcutListener(new ShortcutListener("Clear", ShortcutAction.KeyCode.ESCAPE, null) {
            @Override
            public void handleAction(final Object sender, final Object target) {
                filter.setValue("");
                ((com.vaadin.data.Container.Filterable) rentTable.getContainerDataSource()).removeAllContainerFilters();
            }
        });
        return filter;
    }



    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (String propertyId : DEFAULT_COLLAPSIBLE) {
            if (rentTable.isColumnCollapsed(propertyId) == Page.getCurrent().getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }



    @Subscribe
    public void browserResized(final DashboardEvent.BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.
        if (defaultColumnsVisible()) {
            for (String propertyId : DEFAULT_COLLAPSIBLE) {
                rentTable.setColumnCollapsed(propertyId, Page.getCurrent().getBrowserWindowWidth() < 800);
            }
        }
    }

    private boolean filterByProperty(final String prop, final Item item,
                                     final String text) {
        if (item == null || item.getItemProperty(prop) == null
                || item.getItemProperty(prop).getValue() == null) {
            return false;
        }
        String val = item.getItemProperty(prop).getValue().toString().trim()
                .toLowerCase();
        if (val.contains(text.toLowerCase().trim())) {
            return true;
        }
        return false;
    }

    public void updateTable() {

        List<Rent> rents = rentService.findAll();
        rentTable.setContainerDataSource(new BeanItemContainer<>(Rent.class, rents));

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

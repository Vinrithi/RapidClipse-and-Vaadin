package com.company.myproject1.ui.views.bills;

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
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.data_from_db.BillService;
import com.company.myproject1.ui.domain.Bill;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.haijian.CSVExporter;
import org.vaadin.haijian.ExcelExporter;
import org.vaadin.haijian.PdfExporter;

import javax.xml.transform.stream.StreamSource;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * Created by ochomoswill on 3/14/2017.
 */
public class BillsView extends VerticalLayout implements View {

    @Autowired
    private BillService billService = new BillService();
    private Bill bill = new Bill();




    public final Table billTable = new Table();
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final String[] DEFAULT_COLLAPSIBLE = {"propertyId", "unitId", "month", "year","previousWaterReading", "currentWaterReading", "waterBill", "serviceCharge", "dateRecorded", "billingStatus" };



    public BillsView() {
        setSizeFull();
        setMargin(true);
        addStyleName("transactions");
        DashboardEventBus.register(this);

        addComponent(buildToolbar());

        Panel tablePanel = new Panel();
        tablePanel.setSizeFull();




        billTable.setSizeFull();
        //billTable.addStyleName(ValoTheme.TABLE_BORDERLESS);
        billTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        billTable.addStyleName(ValoTheme.TABLE_COMPACT);
        billTable.setSelectable(true);

        billTable.setColumnCollapsingAllowed(true);/*
        billTable.setColumnCollapsible("time", false);
        billTable.setColumnCollapsible("price", false);*/

        billTable.setColumnReorderingAllowed(true);
        updateTable();
        billTable.setSortContainerPropertyId("time");
        billTable.setSortAscending(false);

        billTable.setVisibleColumns("propertyId", "unitId", "month", "year","previousWaterReading", "currentWaterReading", "waterBill", "serviceCharge", "dateRecorded", "billingStatus");
        billTable.setColumnHeaders("Property ID","Unit ID", "Month", "Year", "Previous Reading", "Current Reading","Water Bill", "Service Charge", "Date Recorded", "Billing Status");

        // Allow dragging items to the reports menu
        billTable.setDragMode(Table.TableDragMode.MULTIROW);
        //table.setMultiSelect(true);

        billTable.setImmediate(true);

        VerticalLayout tableExport = new VerticalLayout();

        HorizontalLayout buildTableToolbar = new HorizontalLayout();

        Table sampleTable;
        try {
            sampleTable = createExampleTable(billTable);
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


        billTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                //Manage collection and manually fetch property of table
                bill = (Bill) event.getItemId();
                BeanFieldGroup.bindFieldsUnbuffered(bill, this);
                BillsWindow.open(bill, true);
            }
        });

        //tablePanel.setContent(billTable);
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

        Label title = new Label("Bills");
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
                Container.Filterable data = (Container.Filterable) billTable.getContainerDataSource();
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
                ((com.vaadin.data.Container.Filterable) billTable.getContainerDataSource()).removeAllContainerFilters();
            }
        });
        return filter;
    }



    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (String propertyId : DEFAULT_COLLAPSIBLE) {
            if (billTable.isColumnCollapsed(propertyId) == Page.getCurrent().getBrowserWindowWidth() < 800) {
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
                billTable.setColumnCollapsed(propertyId, Page.getCurrent().getBrowserWindowWidth() < 800);
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

        List<Bill> bill = billService.findAll();
        billTable.setContainerDataSource(new BeanItemContainer<>(Bill.class, bill));

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

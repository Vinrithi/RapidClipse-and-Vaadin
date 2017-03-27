package com.company.myproject1.ui.views.bills;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.ComboBoxService;
import com.company.myproject1.ui.data.data_from_db.BillService;
import com.company.myproject1.ui.domain.Bill;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Created by ochomoswill on 3/14/2017.
 */
public class BillsWindow extends Window {

    public static final String ID = "rentWindow";

    @Autowired
    private BillService billService = new BillService();
    private Bill bill = new Bill();
    private ComboBoxService comboBoxService = new ComboBoxService();


    private BillsView billsView  = new BillsView();



    private final BeanFieldGroup<Bill> fieldGroup;

    //form layouts
    private FormLayout rentEditForm = new FormLayout();



    //edit form components
    @PropertyId("id")
    private TextField editID = new TextField("Bill ID");
    @PropertyId("propertyId")
    private ComboBox editPropertyID = new ComboBox("Property ID");
    @PropertyId("unitId")
    private ComboBox editUnitID = new ComboBox("Unit ID");
    @PropertyId("month")
    private ComboBox editMonth = new ComboBox("Month");
    @PropertyId("year")
    private ComboBox editYear = new ComboBox("Year");
    @PropertyId("previousWaterReading")
    private TextField editPreviousWaterReading = new TextField("Previous Water Reading");
    @PropertyId("currentWaterReading")
    private TextField editCurrentWaterReading= new TextField("Current Water Reading");
    @PropertyId("waterBill")
    private TextField editWaterBill  = new TextField("Water Bill");
    @PropertyId("serviceCharge")
    private TextField editServiceCharge = new TextField("Service Charge");
    @PropertyId("dateRecorded")
    private DateField editDateRecorded  = new DateField("Date Recorded");
    @PropertyId("billingStatus")
    private TextField editBillingStatus = new TextField("Billing Status");

    private TabSheet detailsWrapper = new TabSheet();

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateBills());

    //Main Window
    public BillsWindow(final Bill bill, final boolean billTabOpen){

        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight(100.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);


        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);

        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildEditPropertyTab());

        content.addComponent(buildFooter());

        if (billTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        System.out.println(detailsWrapper.getComponentCount());



        fieldGroup = new BeanFieldGroup<Bill>(Bill.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(bill);



    }

    public Object[] populatePropertyComboBox()
    {

        Object[] propertyComboData = comboBoxService.populateComboBox("property_id", "property_name", "properties");
        return propertyComboData;

    }

    public Object[] populateUnitComboBox()
    {

        Object[] unitComboData = comboBoxService.populateComboBox("unit_id", "unit_name", "units");
        return unitComboData;

    }


    private Component buildEditPropertyTab(){
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Edit Bill");
        root.setIcon(FontAwesome.EDIT);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        editPropertyID.setWidth("100%");
        //populating combobox
        Object[] propertyComboData = populatePropertyComboBox();
        String[] propertyID = (String[]) propertyComboData[0];
        String[] propertyName = (String[]) propertyComboData[1];

        for(int i=0; i < propertyID.length; i++)
        {
            editPropertyID.addItem(propertyID[i] +" - "+ propertyName[i]);
        }

        editPropertyID.setNullSelectionAllowed(false);
        editPropertyID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);

        editUnitID.setWidth("100%");
        //populating combobox
        Object[] unitComboData = populateUnitComboBox();
        String[] unitID = (String[]) unitComboData[0];
        String[] unitName = (String[]) unitComboData[1];

        for(int i=0; i < unitID.length; i++)
        {
            editUnitID.addItem(unitID[i] +" - "+ unitName[i]);
        }

        editUnitID.setNullSelectionAllowed(false);
        editUnitID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);

        editMonth.setWidth("100%");
        editMonth.addItems( "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        //Remove empty option
        editMonth.setNullSelectionAllowed(false);

        editMonth.setInputPrompt("Select Status");

        editYear.setWidth("100%");
        editYear.addItems( "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025");
        //Remove empty option
        editYear.setNullSelectionAllowed(false);

        editYear.setInputPrompt("Select Status");
        editPreviousWaterReading.setWidth("100%");
        editCurrentWaterReading.setWidth("100%");
        editWaterBill.setWidth("100%");

        editServiceCharge.setWidth("100%");
        editDateRecorded.setWidth("100%");
        editBillingStatus.setWidth("100%");

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        editBtn.addStyleName("friendly");
        editBtn.setWidth("100px");
        btnlayout.addComponent(editBtn);
        btnlayout.setComponentAlignment(editBtn, Alignment.TOP_RIGHT);

        details.addComponents( editPropertyID,
                editUnitID,
                editMonth,
                editYear,
                editPreviousWaterReading,
                editCurrentWaterReading,
                editWaterBill,
                editServiceCharge,
                editDateRecorded,
                editBillingStatus,
                btnlayout);


        return root;

    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        /*Button btnCancel = new Button("Cancel");
        Button btnAdd = new Button("Add Property");
        Button btnEdit = new Button("Edit Property");

        btnCancel.addStyleName(ValoTheme.BUTTON_DANGER);
        btnCancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

        footer.addComponents(btnCancel);
        footer.setComponentAlignment(btnCancel, Alignment.TOP_LEFT);*/
        Label windowDesc = new Label("Allows you to add and edit property details");
        footer.addComponent(windowDesc);
        footer.setComponentAlignment(windowDesc, Alignment.MIDDLE_LEFT);


        return footer;
    }





    public void updateBills(){

        //propertyObj.setId(stringToLong(editID.getValue()));
        bill.setId(stringToLong(editID.getValue()));

        String arrSelectedPropertyID[] = editPropertyID.getValue().toString().split("-");
        bill.setPropertyId(arrSelectedPropertyID[0]);

        String arrSelectedUnitID[] = editUnitID.getValue().toString().split("-");
        bill.setUnitId(arrSelectedUnitID[0]);

        bill.setMonth(editMonth.getValue().toString());
        bill.setYear(Integer.parseInt(editYear.getValue().toString()));
        bill.setPreviousWaterReading(stringToInteger(editPreviousWaterReading.getValue()));
        bill.setCurrentWaterReading(stringToInteger(editCurrentWaterReading.getValue()));
        bill.setWaterBill(stringToDouble(editWaterBill.getValue()));
        bill.setServiceCharge(stringToDouble(editServiceCharge.getValue()));
        bill.setDateRecorded(editDateRecorded.getValue());
        bill.setBillingStatus(editBillingStatus.getValue());


        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            billService.update(bill);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        billsView.updateTable();

    }





    public Long stringToLong(String stringValue){
        Long longValue = Long.valueOf(stringValue.replaceAll(",", "").toString());
        return longValue;
    }

    public int stringToInteger(String stringValue){
        Integer IntegerValue = Integer.valueOf(stringValue.replaceAll(",", "").toString());
        return IntegerValue;
    }

    public Double stringToDouble(String stringValue){
        Double DoubleValue = Double.valueOf(stringValue.replaceAll(",", "").toString());
        return DoubleValue;
    }

    public static void open(final Bill bill, final boolean billTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new BillsWindow(bill, billTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

}

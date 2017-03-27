package com.company.myproject1.ui.views.rents;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.ComboBoxService;
import com.company.myproject1.ui.data.data_from_db.RentService;
import com.company.myproject1.ui.domain.Rent;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Created by ochomoswill on 3/14/2017.
 */
public class RentWindow extends Window {

    public static final String ID = "rentWindow";

    @Autowired
    private RentService rentService = new RentService();
    private Rent rent = new Rent();
    private ComboBoxService comboBoxService = new ComboBoxService();

    private RentView rentView = new RentView();

    private final BeanFieldGroup<Rent> fieldGroup;

    //form layouts
    private FormLayout rentEditForm = new FormLayout();



    //edit form components
    @PropertyId("id")
    private TextField editID = new TextField("Rent ID");
    @PropertyId("propertyId")
    private ComboBox editPropertyID = new ComboBox("Property ID");
    @PropertyId("unitId")
    private ComboBox editUnitID = new ComboBox("Unit ID");
    @PropertyId("month")
    private ComboBox editMonth = new ComboBox("Month");
    @PropertyId("year")
    private ComboBox editYear = new ComboBox("Year");
    @PropertyId("payee")
    private TextField editPayee = new TextField("Payee");
    @PropertyId("amountPaid")
    private TextField editAmountPaid= new TextField("Amount Paid");
    @PropertyId("receivedBy")
    private TextField editReceipient  = new TextField("Received By");
    @PropertyId("datePaid")
    private DateField editDatePaid= new DateField("Date Paid");
    @PropertyId("transactionCode")
    private TextField editTransactionCode  = new TextField("Transaction Code");

    private TabSheet detailsWrapper = new TabSheet();

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateProperties());

    //Main Window
    public RentWindow(final Rent rent, final boolean rentTabOpen){

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

        if (rentTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        System.out.println(detailsWrapper.getComponentCount());



        fieldGroup = new BeanFieldGroup<Rent>(Rent.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(rent);



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
        root.setCaption("Edit Rent");
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
        editPayee.setWidth("100%");
        editAmountPaid.setWidth("100%");
        editReceipient.setWidth("100%");

        editDatePaid.setWidth("100%");
        editTransactionCode.setWidth("100%");

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
                editPayee,
                editAmountPaid,
                editReceipient,
                editDatePaid,
                editTransactionCode,
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





    public void updateProperties(){

        rent.setId(stringToLong(editID.getValue()));

        String arrSelectedPropertyID[] = editPropertyID.getValue().toString().split("-");
        rent.setPropertyId(arrSelectedPropertyID[0]);

        String arrSelectedUnitID[] = editUnitID.getValue().toString().split("-");
        rent.setUnitId(arrSelectedUnitID[0]);
        rent.setMonth(editMonth.getValue().toString());
        rent.setYear(Integer.parseInt(editYear.getValue().toString()));
        rent.setPayee(editPayee.getValue());
        rent.setAmountPaid(stringToDouble(editAmountPaid.getValue()));
        rent.setReceivedBy(editReceipient.getValue());
        rent.setDatePaid(editDatePaid.getValue());
        rent.setTransactionCode(editTransactionCode.getValue());


        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            rentService.update(rent);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        rentView.updateTable();

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

    public static void open(final Rent rent, final boolean rentTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new RentWindow(rent, rentTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

}

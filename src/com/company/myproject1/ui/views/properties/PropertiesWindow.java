package com.company.myproject1.ui.views.properties;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.ComboBoxService;
import com.company.myproject1.ui.data.data_from_db.PropertyService;
import com.company.myproject1.ui.domain.Property;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Created by ochomoswill on 3/11/2017.
 */
public class PropertiesWindow extends Window {
    public static final String ID = "propertyWindow";

    @Autowired
    private PropertyService propertyService = new PropertyService();
    private ComboBoxService comboBoxService = new ComboBoxService();
    private Property propertyObj = new Property();

    private PropertiesView propertiesView = new PropertiesView();



    private final BeanFieldGroup<Property> fieldGroup;

    //form layouts
    private FormLayout propertyAddForm = new FormLayout();
    private FormLayout propertyEditForm = new FormLayout();



    //edit form components
    @PropertyId("id")
    private TextField editID = new TextField("Property ID");
    @PropertyId("landlord_id")
    private ComboBox editLandlordID = new ComboBox("Landlord ID");
    @PropertyId("property_name")
    private TextField editPropertyName = new TextField("Property Name");
    @PropertyId("location")
    private TextField editLocation = new TextField("Location");
    @PropertyId("number_of_units")
    private TextField editNumberOfUnits = new TextField("Number of Units");
    @PropertyId("vacant_units")
    private TextField editVacantUnits = new TextField("Vacant Units");
    @PropertyId("date_added")
    private DateField editDateAdded = new DateField("Date Added");
    @PropertyId("status")
    private ComboBox editStatus = new ComboBox("Select your status");
    @PropertyId("land_registration_number")
    private TextField editLandRegistrationNumber = new TextField("Land Registration No");
    @PropertyId("water_bill_rate")
    private TextField editWaterBillRate = new TextField("Water Bill Rate");
    @PropertyId("agency_fee")
    private TextField editAgencyFee = new TextField("Agency Fee");
    @PropertyId("caretaker_fee")
    private TextField editCaretakerFee = new TextField("Caretaker Fee");


    private ComboBox addLandlordID = new ComboBox("Landlord ID");
    private TextField addPropertyName = new TextField("Property Name");
    private TextField addLocation = new TextField("Location");
    private TextField addNumberOfUnits = new TextField("Number of Units");
    private TextField addVacantUnits = new TextField("Vacant Units");
    private DateField addDateAdded = new DateField("Date Added");
    private ComboBox addStatus = new ComboBox("Select your status");
    private TextField addLandRegistrationNumber = new TextField("Land Registration No");
    private TextField addWaterBillRate = new TextField("Water Bill Rate");
    private TextField addAgencyFee = new TextField("Agency Fee");
    private TextField addCaretakerFee = new TextField("Caretaker Fee");
    private TabSheet detailsWrapper = new TabSheet();

    private ComboBox combo = new ComboBox("OchomoswillElonCombo");

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateProperties());
    private Button saveBtn = new Button("Save", e -> addProperties());

    //Main Window
    public PropertiesWindow(final Property property, final boolean propertyTabOpen){

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

        detailsWrapper.addComponent(buildAddPropertiesTab());

        detailsWrapper.addComponent(buildEditPropertyTab());

        content.addComponent(buildFooter());

        if (propertyTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        System.out.println(detailsWrapper.getComponentCount());



        fieldGroup = new BeanFieldGroup<Property>(Property.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(property);



    }

    private Component buildAddPropertiesTab(){
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Add Property");
        root.setIcon(FontAwesome.PLUS);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        addLandlordID.setWidth("100%");
        //populating combobox
        Object[] landlordComboData = populateLandlordComboBox();
        String[] propertyID = (String[]) landlordComboData[0];
        String[] propertyName = (String[]) landlordComboData[1];

        for(int i=0; i < propertyID.length; i++)
        {
            addLandlordID.addItem(propertyID[i] +" - "+ propertyName[i]);
        }

        addLandlordID.setNullSelectionAllowed(false);
        addLandlordID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);


        addPropertyName.setWidth("100%");
        addLocation.setWidth("100%");
        addNumberOfUnits.setWidth("100%");
        addVacantUnits.setWidth("100%");
        addDateAdded.setWidth("100%");

        addStatus.setWidth("100%");
        addStatus.addItems("IN CONTRACT", "SUSPENDED", "REMOVED");
        //Remove empty option
        addStatus.setNullSelectionAllowed(false);


        addLandRegistrationNumber.setWidth("100%");
        addWaterBillRate.setWidth("100%");
        addAgencyFee.setWidth("100%");
        addCaretakerFee.setWidth("100%");

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        saveBtn.addStyleName("primary");
        saveBtn.setWidth("100px");
        btnlayout.addComponent(saveBtn);
        btnlayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);

        details.addComponents(  addLandlordID,
                addPropertyName,
                addLocation,
                addNumberOfUnits,
                addVacantUnits,
                addDateAdded,
                addStatus,
                addLandRegistrationNumber,
                addWaterBillRate,
                addAgencyFee,
                addCaretakerFee,
                btnlayout);


        return root;

    }

    public Object[] populateLandlordComboBox()
    {

        Object[] propertyComboData = comboBoxService.populateComboBox("landlord_id", "landlord_name", "landlords");
        return propertyComboData;

    }

    private Component buildEditPropertyTab(){
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Edit Property");
        root.setIcon(FontAwesome.EDIT);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        editLandlordID.setWidth("100%");
        //populating combobox
        //Object[] propertyComboData = comboBoxService.populateComboBox("landlord_id", "landlord_name", "landlords");
        Object[] landlordComboData = populateLandlordComboBox();
        String[] propertyID = (String[]) landlordComboData[0];
        String[] propertyName = (String[]) landlordComboData[1];

        for(int i=0; i < propertyID.length; i++)
        {
            editLandlordID.addItem(propertyID[i] +" - "+ propertyName[i]);
        }

        editLandlordID.setNullSelectionAllowed(false);
        editLandlordID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);



        editPropertyName.setWidth("100%");
        editLocation.setWidth("100%");
        editNumberOfUnits.setWidth("100%");
        editVacantUnits.setWidth("100%");
        editDateAdded.setWidth("100%");

        editStatus.setWidth("100%");
        editStatus.addItems( "IN CONTRACT", "SUSPENDED", "REMOVED");
        //Remove empty option
        editStatus.setNullSelectionAllowed(false);

        editStatus.setInputPrompt("Select Status");
        editLandRegistrationNumber.setWidth("100%");
        editWaterBillRate.setWidth("100%");
        editAgencyFee.setWidth("100%");
        editCaretakerFee.setWidth("100%");

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        editBtn.addStyleName("friendly");
        editBtn.setWidth("100px");
        btnlayout.addComponent(editBtn);
        btnlayout.setComponentAlignment(editBtn, Alignment.TOP_RIGHT);

        details.addComponents( editLandlordID,
                editPropertyName,
                editLocation,
                editNumberOfUnits,
                editVacantUnits,
                editDateAdded,
                editStatus,
                editLandRegistrationNumber,
                editWaterBillRate,
                editAgencyFee,
                editCaretakerFee,
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

        //propertyObj.setId(stringToLong(editID.getValue()));
        propertyObj.setId(stringToLong(editID.getValue()));

        String arrSelectedLandlordID[] = editLandlordID.getValue().toString().split("-");
        //System.out.println(arrSelectedLandlordID[0]);

        propertyObj.setLandlord_id(arrSelectedLandlordID[0]);
        propertyObj.setProperty_name(editPropertyName.getValue());
        propertyObj.setLocation(editLocation.getValue());
        propertyObj.setNumber_of_units(Integer.parseInt(editNumberOfUnits.getValue()));
        propertyObj.setDate_added(editDateAdded.getValue());
        propertyObj.setStatus(editStatus.getValue().toString());
        propertyObj.setLand_registration_number(editLandRegistrationNumber.getValue());
        propertyObj.setWater_bill_rate(Integer.parseInt(editWaterBillRate.getValue()));
        propertyObj.setAgency_fee(Double.parseDouble(editAgencyFee.getValue()));
        propertyObj.setCaretaker_fee(stringToDouble(editCaretakerFee.getValue()));


        System.out.println(stringToLong(editID.getValue()));
        System.out.println(editPropertyName.getValue());
        System.out.println(editLocation.getValue());
        System.out.println(Integer.parseInt(editNumberOfUnits.getValue()));
        System.out.println(Integer.parseInt(editVacantUnits.getValue()));
        System.out.println(editDateAdded.getValue());
        System.out.println(editStatus.getValue().toString());
        System.out.println(editLandRegistrationNumber.getValue());
        System.out.println(Integer.parseInt(editWaterBillRate.getValue()));
        System.out.println(Double.parseDouble(editAgencyFee.getValue()));
        System.out.println(stringToDouble(editCaretakerFee.getValue()));

        try {
            // Updated user should also be persisted to database. But
            // not in this demo.
            propertyService.update(propertyObj);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        new PropertiesView().updateTable();

    }

    public void addProperties(){

        String arrSelectedLandlordID[] = addLandlordID.getValue().toString().split("-");
        //System.out.println(arrSelectedLandlordID[0]);

        propertyObj.setLandlord_id(arrSelectedLandlordID[0]);
        propertyObj.setProperty_name(addPropertyName.getValue());
        propertyObj.setLocation(addLocation.getValue());
        propertyObj.setNumber_of_units(Integer.parseInt(addNumberOfUnits.getValue()));
        propertyObj.setDate_added(addDateAdded.getValue());
        propertyObj.setStatus(addStatus.getValue().toString());
        propertyObj.setLand_registration_number(addLandRegistrationNumber.getValue());
        propertyObj.setWater_bill_rate(Integer.parseInt(addWaterBillRate.getValue()));
        propertyObj.setAgency_fee(Double.parseDouble(addAgencyFee.getValue()));
        propertyObj.setCaretaker_fee(stringToDouble(addCaretakerFee.getValue()));



        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            propertyService.add(propertyObj);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        close();
        propertiesView.updateTable();

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

    public static void open(final Property property, final boolean propertyTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new PropertiesWindow(property, propertyTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}

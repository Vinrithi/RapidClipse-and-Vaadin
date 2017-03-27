package com.company.myproject1.ui.views.tenants;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.ComboBoxService;
import com.company.myproject1.ui.data.data_from_db.TenantService;
import com.company.myproject1.ui.domain.Tenant;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ochomoswill on 3/14/2017.
 */
public class TenantsWindow extends Window {


    public static final String ID = "tenantWindow";

    @Autowired
    private TenantService tenantService = new TenantService();
    private Tenant tenant = new Tenant();
    private ComboBoxService comboBoxService = new ComboBoxService();

    private TenantsView tenantsView = new TenantsView();



    private final BeanFieldGroup<Tenant> fieldGroup;

    //form layouts
    private FormLayout tenantAddForm = new FormLayout();
    private FormLayout tenantEditForm = new FormLayout();

    //edit form components
    @PropertyId("id")
    private TextField editID = new TextField("Tenant ID");
    @PropertyId("unitID")
    private ComboBox editUnitID = new ComboBox("Unit ID");
    @PropertyId("tenantName")
    private TextField editTenantName = new TextField("Tenant Name");
    @PropertyId("nationalIDnumber")
    private TextField editNationalIDnumber = new TextField("National ID No");
    @PropertyId("phoneNumber")
    private TextField editPhoneNumber = new TextField("Phone Number");
    @PropertyId("dateAdded")
    private DateField editDateAdded = new DateField("Date Added");
    @PropertyId("tenancyStatus")
    private ComboBox editTenancyStatus= new ComboBox("Tenancy Status");

    private ComboBox addUnitID = new ComboBox("Unit ID");
    private TextField addTenantName = new TextField("Tenant Name");
    private TextField addNationalIDnumber = new TextField("National ID No");
    private TextField addPhoneNumber = new TextField("Phone Number");
    private DateField addDateAdded = new DateField("Date Added");
    private ComboBox addTenancyStatus= new ComboBox("Tenancy Status");

    private TabSheet detailsWrapper = new TabSheet();

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateTenants());
    private Button saveBtn = new Button("Save", e -> addTenants());

    //Main Window
    public TenantsWindow(final Tenant tenant, final boolean tenantTabOpen){

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

        detailsWrapper.addComponent(buildAddTenantsTab());

        detailsWrapper.addComponent(buildEditTenantsTab());

        content.addComponent(buildFooter());

        if (tenantTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        System.out.println(detailsWrapper.getComponentCount());



        fieldGroup = new BeanFieldGroup<Tenant>(Tenant.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(tenant);



    }

    public Object[] populateUnitComboBox()
    {

        Object[] unitComboData = comboBoxService.populateComboBox("unit_id", "unit_name", "units");
        return unitComboData;

    }

    private Component buildAddTenantsTab(){
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Add Tenant");
        root.setIcon(FontAwesome.PLUS);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);


        addUnitID.setWidth("100%");
        //populating combobox
        Object[] unitComboData = populateUnitComboBox();
        String[] unitID = (String[]) unitComboData[0];
        String[] unitName = (String[]) unitComboData[1];

        for(int i=0; i < unitID.length; i++)
        {
            addUnitID.addItem(unitID[i] +" - "+ unitName[i]);
        }

        addUnitID.setNullSelectionAllowed(false);
        addUnitID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);

        addTenantName.setWidth("100%");
        addNationalIDnumber.setWidth("100%");
        addPhoneNumber.setWidth("100%");
        addDateAdded.setWidth("100%");

        addTenancyStatus.setWidth("100%");
        addTenancyStatus.addItems("LIVING IN", "VACATING", "VACATED");
        //Remove empty option
        addTenancyStatus.setNullSelectionAllowed(false);

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        saveBtn.addStyleName("primary");
        saveBtn.setWidth("100px");
        btnlayout.addComponent(saveBtn);
        btnlayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);

        details.addComponents(  addUnitID,
                                addTenantName,
                                addNationalIDnumber,
                                addPhoneNumber,
                                addDateAdded,
                                addTenancyStatus,
                                btnlayout);


        return root;

    }

    private Component buildEditTenantsTab(){
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

        editTenantName.setWidth("100%");
        editNationalIDnumber.setWidth("100%");
        editPhoneNumber.setWidth("100%");
        editDateAdded.setWidth("100%");

        editTenancyStatus.setWidth("100%");
        editTenancyStatus.addItems("LIVING IN", "VACATING", "VACATING");
        //Remove empty option
        editTenancyStatus.setNullSelectionAllowed(false);



        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        editBtn.addStyleName("friendly");
        editBtn.setWidth("100px");
        btnlayout.addComponent(editBtn);
        btnlayout.setComponentAlignment(editBtn, Alignment.TOP_RIGHT);

        details.addComponents(  editUnitID,
                                editTenantName,
                                editNationalIDnumber,
                                editPhoneNumber,
                                editDateAdded,
                                editTenancyStatus,
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
        Label windowDesc = new Label("Allows you to add and edit tenants details");
        footer.addComponent(windowDesc);
        footer.setComponentAlignment(windowDesc, Alignment.MIDDLE_LEFT);


        return footer;
    }





    public void updateTenants(){

        //propertyObj.setId(stringToLong(editID.getValue()));
        tenant.setId(stringToLong(editID.getValue()));

        String arrSelectedUnitID[] = editUnitID.getValue().toString().split("-");
        tenant.setUnitID(arrSelectedUnitID[0]);
        tenant.setTenantName(editTenantName.getValue());
        tenant.setNationalIDnumber(stringToLong(editNationalIDnumber.getValue()));
        tenant.setPhoneNumber(stringToLong(editPhoneNumber.getValue()));
        tenant.setDateAdded(editDateAdded.getValue());
        tenant.setTenancyStatus(editTenancyStatus.getValue().toString());

        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            tenantService.update(tenant);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        tenantsView.updateTable();

    }

    public void addTenants(){



        String arrSelectedUnitID[] = addUnitID.getValue().toString().split("-");
        tenant.setUnitID(arrSelectedUnitID[0]);
        tenant.setTenantName(addTenantName.getValue());
        tenant.setNationalIDnumber(stringToLong(addNationalIDnumber.getValue()));
        tenant.setPhoneNumber(stringToLong(addPhoneNumber.getValue()));
        tenant.setDateAdded(addDateAdded.getValue());
        tenant.setTenancyStatus(addTenancyStatus.getValue().toString());


        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            tenantService.add(tenant);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        close();
        tenantsView.updateTable();

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

    public static void open(final Tenant tenant, final boolean tenantTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new TenantsWindow(tenant, tenantTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

}

package com.company.myproject1.ui.views.units;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.ComboBoxService;
import com.company.myproject1.ui.data.data_from_db.UnitService;
import com.company.myproject1.ui.domain.Property;
import com.company.myproject1.ui.domain.Unit;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Created by ochomoswill on 3/14/2017.
 */
public class UnitsWindow extends Window {

    public static final String ID = "unitWindow";

    @Autowired
    private UnitService unitService = new UnitService();
    private com.company.myproject1.ui.domain.Unit unit = new com.company.myproject1.ui.domain.Unit();
    private ComboBoxService comboBoxService = new ComboBoxService();

    private UnitsView unitsView = new UnitsView();




    private final BeanFieldGroup<com.company.myproject1.ui.domain.Unit> fieldGroup;

    //form layouts
    private FormLayout unitAddForm = new FormLayout();
    private FormLayout unitEditForm = new FormLayout();

    //edit form components
    @PropertyId("id")
    private TextField editID = new TextField("Unit ID");
    @PropertyId("propertyId")
    private ComboBox editPropertyID = new ComboBox("Property ID");
    @PropertyId("unitName")
    private TextField editUnitName = new TextField("Unit Name");
    @PropertyId("classification")
    private TextField editClassification = new TextField("Unit Types");
    @PropertyId("price")
    private TextField editPrice = new TextField("Unit Price");
    @PropertyId("status")
    private ComboBox editStatus = new ComboBox("Select your status");
    @PropertyId("dateAdded")
    private DateField editDateAdded = new DateField("Date Added");


    private ComboBox addPropertyID = new ComboBox("Property ID");
    private TextField addUnitName = new TextField("Unit Name");
    private TextField addClassification = new TextField("Unit Types");
    private TextField addPrice = new TextField("Unit Price");
    private ComboBox addStatus = new ComboBox("Select your status");
    private DateField addDateAdded = new DateField("Date Added");

    private TabSheet detailsWrapper = new TabSheet();

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateUnits());
    private Button saveBtn = new Button("Save", e -> addUnits());

    //Main Window
    public UnitsWindow(final com.company.myproject1.ui.domain.Unit unit, final boolean unitTabOpen){

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

        detailsWrapper.addComponent(buildAddUnitsTab());

        detailsWrapper.addComponent(buildEditUnitsTab());

        content.addComponent(buildFooter());

        if (unitTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        System.out.println(detailsWrapper.getComponentCount());



        fieldGroup = new BeanFieldGroup<com.company.myproject1.ui.domain.Unit>(com.company.myproject1.ui.domain.Unit.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(unit);



    }

    public Object[] populatePropertyComboBox()
    {

        Object[] propertyComboData = comboBoxService.populateComboBox("property_id", "property_name", "properties");
        return propertyComboData;

    }

    private Component buildAddUnitsTab(){
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Add Unit");
        root.setIcon(FontAwesome.PLUS);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        addPropertyID.setWidth("100%");
        //populating combobox
        Object[] propertyComboData = populatePropertyComboBox();
        String[] propertyID = (String[]) propertyComboData[0];
        String[] propertyName = (String[]) propertyComboData[1];

        for(int i=0; i < propertyID.length; i++)
        {
            addPropertyID.addItem(propertyID[i] +" - "+ propertyName[i]);
        }

        addPropertyID.setNullSelectionAllowed(false);
        addPropertyID.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);


        addUnitName.setWidth("100%");
        addClassification.setWidth("100%");
        addPrice.setWidth("100%");
        addStatus.setWidth("100%");
        addDateAdded.setWidth("100%");

        addStatus.setWidth("100%");
        addStatus.addItems("OCCUPIED", "VACANT", "VACATING");
        //Remove empty option
        addStatus.setNullSelectionAllowed(false);



        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        saveBtn.addStyleName("primary");
        saveBtn.setWidth("100px");
        btnlayout.addComponent(saveBtn);
        btnlayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);

        details.addComponents(  addPropertyID,
                                addUnitName,
                                addClassification,
                                addPrice,
                                addStatus,
                                addDateAdded,
                                btnlayout);


        return root;

    }

    private Component buildEditUnitsTab(){
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


        editUnitName.setWidth("100%");
        editClassification.setWidth("100%");
        editPrice.setWidth("100%");
        editStatus.setWidth("100%");
        editDateAdded.setWidth("100%");

        editStatus.setWidth("100%");
        editStatus.addItems( "OCCUPIED", "VACANT", "VACATING");
        //Remove empty option
        editStatus.setNullSelectionAllowed(false);

        editStatus.setInputPrompt("Select Status");


        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        editBtn.addStyleName("friendly");
        editBtn.setWidth("100px");
        btnlayout.addComponent(editBtn);
        btnlayout.setComponentAlignment(editBtn, Alignment.TOP_RIGHT);

        details.addComponents(  editPropertyID,
                                editUnitName,
                                editClassification,
                                editPrice,
                                editStatus,
                                editDateAdded,
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





    public void updateUnits(){

        unit.setId(stringToLong(editID.getValue()));

        String arrSelectedPropertyID[] = editPropertyID.getValue().toString().split("-");
        unit.setPropertyId(arrSelectedPropertyID[0]);

        unit.setUnitName(editUnitName.getValue());
        unit.setClassification(editClassification.getValue());
        unit.setPrice(stringToDouble(editPrice.getValue()));
        unit.setStatus(editStatus.getValue().toString());
        unit.setDateAdded(editDateAdded.getValue());

        System.out.println(stringToLong(editID.getValue()));
        System.out.println(editUnitName.getValue());
        System.out.println(editClassification.getValue());
        System.out.println(stringToDouble(editPrice.getValue()));
        System.out.println(editStatus.getValue().toString());
        System.out.println(editDateAdded.getValue());

        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            unitService.update(unit);


        } catch (Exception e) {
            Notification.show("Error while updating unit details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        unitsView.updateTable();

    }

    public void addUnits(){


        String arrSelectedPropertyID[] = addPropertyID.getValue().toString().split("-");
        unit.setPropertyId(arrSelectedPropertyID[0]);
        unit.setUnitName(addUnitName.getValue());
        unit.setClassification(addClassification.getValue());
        unit.setPrice(stringToDouble(addPrice.getValue()));
        unit.setStatus(addStatus.getValue().toString());
        unit.setDateAdded(addDateAdded.getValue());




        try {
            // Updated user should also be persisted to database. But
            // not in this demo.

            unitService.add(unit);


        } catch (Exception e) {
            Notification.show("Error while updating property details", Notification.Type.ERROR_MESSAGE);
        }

        close();
        unitsView.updateTable();

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

    public static void open(final com.company.myproject1.ui.domain.Unit unit, final boolean unitTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new UnitsWindow(unit, unitTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }


}

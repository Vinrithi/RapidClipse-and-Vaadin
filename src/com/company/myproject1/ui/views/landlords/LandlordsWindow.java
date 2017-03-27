package com.company.myproject1.ui.views.landlords;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.data.data_from_db.LandlordService;
import com.company.myproject1.ui.domain.Landlord;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/*
 * Created by ochomoswill on 3/8/2017.
 */

public class LandlordsWindow extends Window{


    public static final String ID = "landlordsWindow";

    @Autowired
    private LandlordService landlordService = new LandlordService();
    private Landlord landlordObj = new Landlord();

    private LandlordsView landlordsView = new LandlordsView();



    private final BeanFieldGroup<Landlord> fieldGroup;

    //form layouts
    private FormLayout landlordAddForm = new FormLayout();
    private FormLayout landlordEditForm = new FormLayout();



    //edit form components
    @PropertyId("id")
    private TextField editLandlordID = new TextField("Landlord ID");
    @PropertyId("landlord_name")
    private TextField editLandlordName = new TextField("Landlord Name");
    @PropertyId("nationalIDnumber")
    private TextField editNationalIDno = new TextField("National ID No");
    @PropertyId("phone_number")
    private TextField editPhoneNumber = new TextField("Phone Number");
    @PropertyId("email_address")
    private TextField editEmailAddress = new TextField("Email Address");
    @PropertyId("kra_pin")
    private TextField editKraPin = new TextField("KRA Pin");
    @PropertyId("banker")
    private TextField editBanker = new TextField("Bank");
    @PropertyId("account_number")
    private TextField editAccountNumber = new TextField("Account Number");
    @PropertyId("date_added")
    private DateField editDateAdded = new DateField("Date Added");

    //add form components
    private TextField addLandlordName = new TextField("Landlord Name");
    private TextField addNationalIDno = new TextField("National ID No");
    private TextField addPhoneNumber = new TextField("Phone Number");
    private TextField addEmailAddress = new TextField("Email Address");
    private TextField addKraPin = new TextField("KRA Pin");
    private TextField addBanker = new TextField("Bank");
    private TextField addAccountNumber = new TextField("Account Number");
    private DateField addDateAdded = new DateField("Date Added");

    //form buttons
    private Button editBtn = new Button("Edit", e -> updateLandlords());
    private Button saveBtn = new Button("Save", e -> addLandlords());

    //Main Window
    public LandlordsWindow(final Landlord landlord, final boolean landlordTabOpen){

        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight(90.0f, Unit.PERCENTAGE);


        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildAddLandlordsTab());
        detailsWrapper.addComponent(buildEditLandlordTab());

        if (landlordTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        fieldGroup = new BeanFieldGroup<Landlord>(Landlord.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(landlord);
    }

    private Component buildAddLandlordsTab(){
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Add Landlord");
        root.setIcon(FontAwesome.PLUS);
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);

        landlordAddForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        landlordAddForm.setSpacing(true);
        landlordAddForm.setMargin(true);
        landlordAddForm.setSizeFull();

        addLandlordName.setWidth("100%");
        addNationalIDno.setWidth("100%");
        addPhoneNumber.setWidth("100%");
        addEmailAddress.setWidth("100%");
        addKraPin.setWidth("100%");
        addBanker.setWidth("100%");
        addAccountNumber.setWidth("100%");
        addDateAdded.setWidth("100%");

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        saveBtn.addStyleName("primary");
        saveBtn.setWidth("100px");
        btnlayout.addComponent(saveBtn);
        btnlayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);

        landlordAddForm.addComponents(addLandlordName, addNationalIDno, addPhoneNumber, addEmailAddress, addKraPin,addBanker,addAccountNumber, addDateAdded, btnlayout);
        root.addComponent(landlordAddForm);
        root.setExpandRatio(landlordAddForm, 1);
        return root;
    }

    private Component buildEditLandlordTab(){
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Edit Landlord");
        root.setIcon(FontAwesome.EDIT);
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);

        landlordEditForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        landlordEditForm.setSpacing(true);
        landlordEditForm.setMargin(true);
        landlordEditForm.setSizeFull();

        editLandlordName.setWidth("100%");
        editNationalIDno.setWidth("100%");
        editPhoneNumber.setWidth("100%");
        editEmailAddress.setWidth("100%");
        editKraPin.setWidth("100%");
        editBanker.setWidth("100%");
        editAccountNumber.setWidth("100%");
        editDateAdded.setWidth("100%");

        HorizontalLayout btnlayout = new HorizontalLayout();
        btnlayout.setMargin(new MarginInfo(true, false, true, true));
        btnlayout.setSpacing(true);
        btnlayout.setSizeFull();
        editBtn.addStyleName("friendly");
        editBtn.setWidth("100px");
        btnlayout.addComponent(editBtn);
        btnlayout.setComponentAlignment(editBtn, Alignment.TOP_RIGHT);

        landlordEditForm.addComponents(editLandlordName, editNationalIDno, editPhoneNumber, editEmailAddress, editKraPin,editBanker,editAccountNumber, editDateAdded, btnlayout);
        root.addComponent(landlordEditForm);
        root.setExpandRatio(landlordEditForm, 1);
        return root;
    }





    public void updateLandlords(){

        landlordObj.setId(stringToLong(editLandlordID.getValue()));

        landlordObj.setLandlord_name(editLandlordName.getValue());
        landlordObj.setNationalIDnumber(editNationalIDno.getValue());
        landlordObj.setPhone_number(editPhoneNumber.getValue());
        landlordObj.setEmail_address(editEmailAddress.getValue());
        landlordObj.setKra_pin(editKraPin.getValue());
        landlordObj.setBanker(editBanker.getValue());
        landlordObj.setAccount_number(editAccountNumber.getValue());
        landlordObj.setDate_added(editDateAdded.getValue());

        try {
            // Updated user should also be persisted to database. But
            // not in this demo.
            landlordService.update(landlordObj);
            /*Notification success = new Notification("Landlord updated successfully");
            success.setDelayMsec(2000);
            success.setStyleName("bar success small");
            success.setPosition(Position.BOTTOM_CENTER);
            success.show(Page.getCurrent());*/

        } catch (Exception e) {
            Notification.show("Error while updating landlord details", Notification.Type.ERROR_MESSAGE);
        }

        //DashboardEventBus.post(new LandlordsView());
        close();
        new LandlordsView().updateTable();

    }

    public void addLandlords(){


        landlordObj.setLandlord_name(addLandlordName.getValue());
        landlordObj.setNationalIDnumber(addNationalIDno.getValue());
        landlordObj.setPhone_number(addPhoneNumber.getValue());
        landlordObj.setEmail_address(addEmailAddress.getValue());
        landlordObj.setKra_pin(addKraPin.getValue());
        landlordObj.setBanker(addBanker.getValue());
        landlordObj.setAccount_number(addAccountNumber.getValue());
        landlordObj.setDate_added(addDateAdded.getValue());

        landlordService.add(landlordObj);

        close();
        landlordsView.updateTable();

    }



    public Long stringToLong(String stringValue){
        Long longValue = Long.valueOf(stringValue.replaceAll(",", "").toString());
        return longValue;
    }

    public static void open(final Landlord landlord, final boolean landlordTabOpen)
    {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new LandlordsWindow(landlord, landlordTabOpen);
        UI.getCurrent().addWindow(w);
        w.focus();
    }



}

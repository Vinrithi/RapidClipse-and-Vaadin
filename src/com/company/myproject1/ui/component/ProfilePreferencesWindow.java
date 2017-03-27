package com.company.myproject1.ui.component;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.*;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.company.myproject1.ui.domain.User;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEvent.CloseOpenWindowsEvent;
import com.company.myproject1.ui.event.DashboardEvent.ProfileUpdatedEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import com.company.myproject1.ui.event.Notifications;

@SuppressWarnings("serial")
public class ProfilePreferencesWindow extends Window {

    public static final String ID = "profilepreferenceswindow";

    //private final BeanFieldGroup<User> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */

    //@PropertyId("userName")
    private TextField userNameField;

    //@PropertyId("password")
    private TextField paswordField;

    //@PropertyId("email")
    private TextField emailField;

    //@PropertyId("phoneNumber")
    private TextField phoneNumberField;

    private PasswordField currentPasswordField;

    private PasswordField newPasswordField;

    private PasswordField confirmNewPasswordField;

    //@PropertyId("firstName")
    private TextField firstNameField;

    //@PropertyId("lastName")
    private TextField lastNameField;

    //@PropertyId("gender")
    private Select genderField;

    //@PropertyId("nationalID")
    private TextField nationalIDNumberField;

    //@PropertyId("status")
    private TextField statusField;

    //@PropertyId("dateAdded")
    private TextField dateAddedField;

    //@PropertyId("designation")
    private TextField designationField;

    //@PropertyId("authorisationLevel")
    private TextField authorisationLevelField;

    //@PropertyId("bio")
    private TextArea bioField;

    private User user = (User) VaadinSession.getCurrent().getAttribute(
            User.class.getName());

    private ProfilePreferencesWindow(final User user, final boolean preferencesTabOpen) {

        if(user == null){
            closeUserDetailsTab("Error! Session timed out.");
        }else{
            addStyleName("profile-window");
            setId(ID);
            Responsive.makeResponsive(this);

            setModal(true);
            setCloseShortcut(KeyCode.ESCAPE, null);
            setResizable(false);
            setClosable(false);
            setHeight(100.0f, Unit.PERCENTAGE);

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

            detailsWrapper.addComponent(buildProfileTab());
            //detailsWrapper.addComponent(buildPreferencesTab());

            if (preferencesTabOpen) {
                detailsWrapper.setSelectedTab(1);
            }

            content.addComponent(buildFooter());
        }

        /*fieldGroup = new BeanFieldGroup<User>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);*/
    }

    /*private Component buildPreferencesTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Preferences");
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label message = new Label("Not implemented in this demo");
        message.setSizeUndefined();
        message.addStyleName(ValoTheme.LABEL_LIGHT);
        root.addComponent(message);
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

        return root;
    }*/

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource(
                "img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Change", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("This feature is not available at the moment.");
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        Label personalInfoSection = new Label("Personal Info");
        personalInfoSection.addStyleName(ValoTheme.LABEL_H4);
        personalInfoSection.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(personalInfoSection);

        userNameField = new TextField("Username");
        details.addComponent(userNameField);
        userNameField.setValue(user.getUserName());

        firstNameField = new TextField("First Name");
        details.addComponent(firstNameField);
        firstNameField.setValue(user.getFirstName());

        lastNameField = new TextField("Last Name");
        details.addComponent(lastNameField);
        lastNameField.setValue(user.getLastName());

        nationalIDNumberField = new TextField("National ID Number");
        nationalIDNumberField.setWidth("100%");
        /*nationalIDNumberField.setRequired(true);
        nationalIDNumberField.setNullRepresentation("");*/
        details.addComponent(nationalIDNumberField);
        nationalIDNumberField.setValue(user.getNationalID());

        genderField = new Select();
        genderField.setCaption("Gender");
        genderField.addItem("Male");
        genderField.addItem("Female");
        genderField.addItem("Other");
        details.addComponent(genderField);
        genderField.setValue(user.getGender());

        Label changePasswordSection = new Label("Change password");
        changePasswordSection.addStyleName(ValoTheme.LABEL_H4);
        changePasswordSection.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(changePasswordSection);

        currentPasswordField = new PasswordField("Current Password");
        /*currentPasswordField.setComponentError(new UserError(
                "This password is incorrect"));*/
        details.addComponent(currentPasswordField);

        newPasswordField = new PasswordField("New Password");
        /*newPasswordField.setComponentError(new UserError(
                "The passwords no not match"));*/
        details.addComponent(newPasswordField);

        confirmNewPasswordField = new PasswordField("Confirm New Password");
        /*confirmNewPasswordField.setComponentError(new UserError(
                "The passwords no not match"));*/
        details.addComponent(confirmNewPasswordField);

/*        titleField = new ComboBox("Title");
        titleField.setInputPrompt("Please specify");
        titleField.addItem("Mr.");
        titleField.addItem("Mrs.");
        titleField.addItem("Ms.");
        titleField.setNewItemsAllowed(true);
        details.addComponent(titleField);*/

        Label contactInfoSection = new Label("Contact Info");
        contactInfoSection.addStyleName(ValoTheme.LABEL_H4);
        contactInfoSection.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(contactInfoSection);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        /*emailField.setRequired(true);*/
        /*emailField.setNullRepresentation("");*/
        details.addComponent(emailField);
        emailField.setValue(user.getEmail());

        phoneNumberField = new TextField("Phone number");
        phoneNumberField.setWidth("100%");
        phoneNumberField.setNullRepresentation("");
        details.addComponent(phoneNumberField);
        phoneNumberField.setValue(user.getPhoneNumben());

        Label additionalInfoSection = new Label("Additional Info");
        additionalInfoSection.addStyleName(ValoTheme.LABEL_H4);
        additionalInfoSection.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(additionalInfoSection);

        dateAddedField = new TextField("Date of Account Creation");
        dateAddedField.setWidth("100%");
        /*dateAddedField.setNullRepresentation("");*/
        details.addComponent(dateAddedField);
        dateAddedField.setValue(user.getDateAdded());
        dateAddedField.setReadOnly(true);

        authorisationLevelField = new TextField("Authorisation Level");
        authorisationLevelField.setWidth("100%");
        /*authorisationLevelField.setNullRepresentation("");*/
        details.addComponent(authorisationLevelField);
        authorisationLevelField.setValue(user.getAuthorisationLevel());
        authorisationLevelField.setReadOnly(true);

        designationField = new TextField("Designation");
        /*designationField.setInputPrompt("http://");*/
        designationField.setWidth("100%");
        /*designationField.setNullRepresentation("");*/
        details.addComponent(designationField);
        designationField.setValue(user.getDesignation());
        designationField.setReadOnly(true);

        statusField = new TextField("Status");
        /*designationField.setInputPrompt("http://");*/
        statusField.setWidth("100%");
        /*designationField.setNullRepresentation("");*/
        details.addComponent(statusField);
        statusField.setValue(user.getStatus());
        statusField.setReadOnly(true);

        bioField = new TextArea("Bio");
        bioField.setWidth("100%");
        bioField.setRows(4);
        /*bioField.setNullRepresentation("");*/
        details.addComponent(bioField);
        bioField.setValue(user.getBio());

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button btnUpdate = new Button("Update");
        Button btnCancel = new Button("Cancel");

        btnUpdate.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnCancel.addStyleName(ValoTheme.BUTTON_DANGER);

        btnUpdate.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                if(user != null){
                    if(validateFields()){
                        if(!(currentPasswordField.getValue().equals("") && newPasswordField.getValue().equals("") && confirmNewPasswordField.getValue().equals(""))){
                            System.out.println("1");
                            if(user.getPassword().equals(currentPasswordField.getValue())){
                                System.out.println("2");
                                if(newPasswordField.getValue().equals("") || confirmNewPasswordField.getValue().equals("")){
                                    System.out.println("3");
                                    Notifications.showNotification("Error!", "<span>Passwords do not match or empty</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                                }else {
                                    System.out.println("4");
                                    if(newPasswordField.getValue().equals(confirmNewPasswordField.getValue())){
                                        System.out.println("5");
                                        try {
                                            System.out.println("6");
                                            //fieldGroup.commit();
                                            // Updated user should also be persisted to database. But
                                            // not in this demo.

                                            user.setPassword(newPasswordField.getValue());
                                            updateUserDetails();
                                        } catch (Exception e) {
                                            System.out.println("7");
                                            Notification.show("Error while updating profile. Your username might be already taken",
                                                    Type.ERROR_MESSAGE);
                                        }
                                    }else{
                                        System.out.println("8");
                                        Notifications.showNotification("Error!", "<span>Passwords do not match</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                                    }
                                }
                            }else{
                                System.out.println("9");
                                Notifications.showNotification("Error!", "<span>Incorrect Password.</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                            }
                        }else{
                            try {
                                System.out.println("10");

                                //fieldGroup.commit();
                                // Updated user should also be persisted to database. But
                                // not in this demo.
                                updateUserDetails();

                            } catch (Exception e) {
                                System.out.println("7");
                                Notification.show("Error while updating profile. Your username might be already taken",
                                        Type.ERROR_MESSAGE);
                            }
                        }
                    }else{
                        Notifications.showNotification("Error!", "<span>All editable fields must be filled (Except Change password section, if not used).</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                    }
                }else{
                    System.out.println("11");
                    closeUserDetailsTab("Error! Session timed out.");
                }
            }
        });

        btnCancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                DashboardEventBus.post(new ProfileUpdatedEvent());
                close();
            }
        });

        btnUpdate.focus();
        footer.addComponents(btnCancel, btnUpdate);
        footer.setComponentAlignment(btnCancel, Alignment.TOP_LEFT);
        footer.setComponentAlignment(btnUpdate, Alignment.TOP_RIGHT);
        return footer;
    }

    private void updateUserDetails(){

        user.setUserName(userNameField.getValue());
        user.setEmail(emailField.getValue());
        user.setPhoneNumber(phoneNumberField.getValue());
        user.setFirstName(firstNameField.getValue());
        user.setLastName(lastNameField.getValue());
        user.setGender(genderField.getValue().toString());
        user.setNationalID(nationalIDNumberField.getValue());
        user.setBio(bioField.getValue());


        DashboardEventBus.post(new DashboardEvent.UpdateUserEvent(user));

        Notification success = new Notification(
                "Profile updated successfully");
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());

        DashboardEventBus.post(new ProfileUpdatedEvent());
        close();
    }

    private void closeUserDetailsTab(String reasonForClosing){
        Notification.show(reasonForClosing,
                Type.ERROR_MESSAGE);
        DashboardEventBus.post(new DashboardEvent.UserLoggedOutEvent());
    }


    /**
     * TODO enable genderField validation
     * Somehow, system takes it as a null pointer exception if select field is blank. Find a work around.
     * @return Boolean goAheadAndUpdate
     */

    private boolean validateFields(){
        boolean goAheadAndUpdate = true;
        if(userNameField.getValue().equals("") || emailField.getValue().equals("") || phoneNumberField.getValue().equals("") || firstNameField.getValue().equals("") || lastNameField.getValue().equals("") || genderField.getValue().toString().equals("") || nationalIDNumberField.getValue().equals("") || bioField.getValue().equals(""))
            goAheadAndUpdate = false;

        return goAheadAndUpdate;
    }

    public static void open(final User user, final boolean preferencesTabActive) {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
        Window w = new ProfilePreferencesWindow(user, preferencesTabActive);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}

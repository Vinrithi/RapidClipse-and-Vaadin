package com.company.myproject1.ui.views.login;

import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import com.company.myproject1.ui.event.Notifications;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by elon on 3/3/2017 - 11:01 PM.
 * Package: com.company.myproject1.frontend.ui.
 * Project: vaadinUISample.
 */

public class Login extends VerticalLayout {

	JdbcTemplate jdbcTemplate;

    public Login(){
        setSizeFull();
        Page.getCurrent().setUriFragment("login");

/*        //Custom Styles
        Page.Styles styles = Page.getCurrent().getStyles();
        // inject css
        styles.add(".layout-white-bordered { background-color:#ffffff; border: solid 1px #C5C5C5; }" +
                ".logo {padding-left: 25%;}" +
                ".padding-top-5: {padding-top: 5%;}");

        setSizeFull();*/

        final Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        final Notification notification = new Notification(
                "Welcome KejaPay System");
        notification
                .setDescription("<span>This is a KejaPay admin panel demo. Use <b>trump@gmail.com</b> as your Username and <b>1234</b> as your Password. Do not leave the fields blank.</span><br><br><span style='font-size: 80%'>All rights reserved. &copy; Copyright Sky World Limited.</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());

        this.jdbcTemplate = new JdbcTemplate(Conn.dataSource1());
    }

    private Component buildLoginForm(){
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildFields());

        return loginPanel;
    }

    private Component buildFields(){
        final VerticalLayout fields = new VerticalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");
        fields.setWidth("350");
        //fields.addStyleName("layout-white-bordered");

        //String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        //final Image imgLogo = new Image("", new FileResource(new File(basepath +"/assets/images/logo.png")));
        final Image imgLogo = new Image(null, new ThemeResource("img/logo.png"));
        imgLogo.setWidth("80%");

        final TextField tfUsername = new TextField("Username / Email");
        tfUsername.setIcon(FontAwesome.USER);
        tfUsername.setWidth("100%");
        tfUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField pfPassword = new PasswordField("Password");
        pfPassword.setIcon(FontAwesome.LOCK);
        pfPassword.setWidth("100%");
        pfPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final CheckBox cbxRememberMe = new CheckBox("Remember me", true);

        final Button btnSignIn = new Button("Sign In");
        btnSignIn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSignIn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        btnSignIn.setWidth("100%");
        btnSignIn.focus();

        final Button btnForgotPassword = new Button("Forgot Password?");
        btnForgotPassword.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnForgotPassword.setIcon(FontAwesome.LOCK);
        btnForgotPassword.setWidth("100%");

        fields.addComponents(imgLogo, tfUsername, pfPassword, cbxRememberMe, btnSignIn, btnForgotPassword);
        fields.setComponentAlignment(btnForgotPassword, Alignment.BOTTOM_LEFT);
        fields.setComponentAlignment(btnSignIn, Alignment.BOTTOM_LEFT);
        fields.setComponentAlignment(imgLogo, Alignment.TOP_CENTER);

        btnSignIn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {

                if(Objects.equals(tfUsername.getValue(), "")) {
                    if (Objects.equals(pfPassword.getValue(), "")) {
                        Notifications.showNotification("Error!", "<span>Phone number and PIN field cannot be left blank.</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                    }else{
                        Notifications.showNotification("Error!", "<span>Phone number cannot be left blank.</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                    }
                }else{
                    if (Objects.equals(pfPassword.getValue(), "")) {
                        Notifications.showNotification("Error!", "<span>PIN field cannot be left blank.</span>", Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                    }else{
                        if(cbxRememberMe.getValue()){
                            Notifications.showNotification("Remember me.", "No worries. We'll remember you next time you log in", Type.ASSISTIVE_NOTIFICATION, 3000,"tray dark small closable login-help", Position.TOP_CENTER);
                        }else{
                            Notifications.showNotification("Remember me.", "Anonymous much? We got you covered.", Type.ASSISTIVE_NOTIFICATION, 3000,"tray dark small closable login-help", Position.TOP_CENTER);
                        }
                        //getUI().getNavigator().navigateTo("dashboard");
                        DashboardEventBus.post(new DashboardEvent.UserLoginRequestedEvent(tfUsername.getValue(), pfPassword.getValue()));
                  }
                }

            	//tfUsername.setValue(findAdd(1));
            }
        });


        btnForgotPassword.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                Notifications.showNotification("Coming soon.", "Sorry for the inconvenience, but password resetting is still under development.", Type.ASSISTIVE_NOTIFICATION, 3000,"tray dark small closable login-help", Position.TOP_CENTER);
            }
        });

        return fields;
    }



	public String findAdd(final int custId){

		final String sql = "SELECT email_address FROM user_accounts WHERE user_id = ?";

		final String name = this.jdbcTemplate.queryForObject(sql, String.class, custId);

		return name;

	}




}

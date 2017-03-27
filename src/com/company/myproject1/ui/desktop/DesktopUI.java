package com.company.myproject1.ui.desktop;

import java.util.Locale;

import com.company.myproject1.ui.data.DataProvider;
import com.company.myproject1.ui.domain.User;
import com.company.myproject1.ui.event.DashboardEvent;
import com.company.myproject1.ui.event.DashboardEventBus;
import com.company.myproject1.ui.views.dashboard.MainView2;
import com.company.myproject1.ui.views.login.Login;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.xdev.ui.XdevUI;
import com.xdev.ui.navigation.XdevNavigator;

@Push(value = PushMode.MANUAL, transport = Transport.LONG_POLLING)
//@Theme("MyProject1")

@SpringUI
@Theme("dashboard")

@Title("KejaPay Admin Panel")
/*@Widgetset("com.company.kejapay.dashboard.DashboardWidgetSet")*/
@SuppressWarnings("serial")
/*@PreserveOnRefresh*/
public class DesktopUI extends XdevUI {
	private static final DataProvider dataProvider = new com.company.myproject1.ui.data.data_from_db.DataProvider();
    private static final DashboardEventBus dashboardEventbus = new DashboardEventBus();


	public DesktopUI() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final VaadinRequest request) {
		//this.initUI();

		 setLocale(Locale.getDefault());


		DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);


       updateContent();


       Page.getCurrent().addBrowserWindowResizeListener(
               new BrowserWindowResizeListener() {
                   @Override
                   public void browserWindowResized(
                           final Page.BrowserWindowResizeEvent event) {
                       	DashboardEventBus.post(new DashboardEvent.BrowserResizeEvent());
                   }
               });


	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.navigator = new XdevNavigator(this, this);

		this.navigator.addView("", MainView.class);

		this.setSizeFull();
	} // </generated-code>

	// <generated-code name="variables">
	private XdevNavigator navigator; // </generated-code>

	private void updateContent() {
	        final User user = (User) VaadinSession.getCurrent().getAttribute(
	                User.class.getName());
	        if (user != null) {
	            // Authenticated user
	            setContent(new MainView2());

	           //getNavigator().navigateTo(getNavigator().getState());
	        } else {
	        	setContent(new Login());
	        }
	    }

	    @Subscribe
	    public void updateUser(final  DashboardEvent.UpdateUserEvent event){
	        getDataProvider().updateUser(event.getUserDetails());
	    }

	    @Subscribe
	    public void userLoginRequested(final DashboardEvent.UserLoginRequestedEvent event) {
	        final User user = getDataProvider().authenticate(event.getPhoneNumber(),
	                event.getPin());
	        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
	        updateContent();
	    }

	    @Subscribe
	    public void userLoggedOut(final DashboardEvent.UserLoggedOutEvent event) {
	        // When the user logs out, current VaadinSession gets closed and the
	        // page gets reloaded on the login screen. Do notice the this doesn't
	        // invalidate the current HttpSession.
	        VaadinSession.getCurrent().close();
	        Page.getCurrent().reload();
	    }

	    @Subscribe
	    public void closeOpenWindows(final DashboardEvent.CloseOpenWindowsEvent event) {
	        for (final Window window : getWindows()) {
	            window.close();
	        }
	    }


	    public static DashboardEventBus getDashboardEventbus() {
	        return dashboardEventbus;
	    }

	    public static DataProvider getDataProvider() {
	        return dataProvider;
	    }
}
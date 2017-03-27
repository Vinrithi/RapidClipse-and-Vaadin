
package com.company.myproject1.ui;

import com.company.myproject1.ui.desktop.DesktopUI;
import com.company.myproject1.ui.phone.PhoneUI;
import com.company.myproject1.ui.tablet.TabletUI;

import javax.servlet.annotation.WebServlet;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import com.xdev.communication.ClientInfo;
import com.xdev.communication.XdevServlet;

@WebServlet(value = "/*", asyncSupported = true)
public class Servlet extends XdevServlet {
	public Servlet() {
		super();
	}

	@Override
	protected void initSession(SessionInitEvent event) {
		super.initSession(event);

		event.getSession().addUIProvider(new ServletUIProvider());
	}

	/**
	 * UIProvider which provides different UIs depending on the caller's device.
	 */
	private static class ServletUIProvider extends UIProvider {
		@Override
		public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
			ClientInfo client = ClientInfo.getCurrent();
			if (client != null) {
				if (client.isMobile()) {
					return PhoneUI.class;
				}
				if (client.isTablet()) {
					return TabletUI.class;
				}
			}
			return DesktopUI.class;
		}
	}
}
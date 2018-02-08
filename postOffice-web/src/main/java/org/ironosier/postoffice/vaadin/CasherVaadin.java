package org.ironosier.postoffice.vaadin;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;


import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


@CDIView("cashier")
@RolesAllowed({ "[cashier]" })
@SuppressWarnings("serial")
public class CasherVaadin extends VerticalLayout implements View {

	
	@Override
	public void enter(ViewChangeEvent event) {
		addComponent(new Label("This is casher`s view"));
		addComponent(new Button("logout", e -> logout()));
	}

	private void logout() {
		try {
			JaasAccessControl.logout();
			Notification.show("logout");
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}

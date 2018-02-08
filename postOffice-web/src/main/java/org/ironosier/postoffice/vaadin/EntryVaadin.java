package org.ironosier.postoffice.vaadin;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIUI("")
@Title("Почта")
public class EntryVaadin extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		Navigator navigator = new Navigator(this, layout);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(ErrorVaadin.class);

		if (!isAuthorized(request)) {
			navigator.navigateTo("login");
		} else {
			navigateToFirstView(request);
		}

	}

	public void navigateToFirstView(VaadinRequest request) {
		if (checkRole("[cashier]", request)) {
			getUI().getNavigator().navigateTo("cashier");
		}
		if (checkRole("[manager]", request)) {
			getUI().getNavigator().navigateTo("manager");
		}
	}

	private boolean checkRole(String role, VaadinRequest request) {
		return request.isUserInRole(role);
	}

	private boolean isAuthorized(VaadinRequest request) {
		return request.getUserPrincipal() != null;
	}
}

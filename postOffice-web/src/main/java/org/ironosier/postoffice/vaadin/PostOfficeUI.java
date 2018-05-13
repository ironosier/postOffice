package org.ironosier.postoffice.vaadin;

import javax.inject.Inject;



import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIUI("")
@Title("Почта")
public class PostOfficeUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {

		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		setStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
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
		if (checkRole("[charts]", request)) {
			getUI().getNavigator().navigateTo("charts");
		}
	}

	private boolean checkRole(String role, VaadinRequest request) {
		return request.isUserInRole(role);
	}

	private boolean isAuthorized(VaadinRequest request) {
		return request.getUserPrincipal() != null;
	}
}

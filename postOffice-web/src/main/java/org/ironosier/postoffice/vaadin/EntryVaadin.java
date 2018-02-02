package org.ironosier.postoffice.vaadin;

import javax.inject.Inject;

import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
@PushStateNavigation
@CDIUI("")
@Title("Почта")
public class EntryVaadin extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		Navigator navigator = new Navigator(this, layout);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(ErrorVaadin.class);

		String defaultView = Page.getCurrent().getUriFragment();

		if (defaultView == null || defaultView.trim().isEmpty()) {
			defaultView = "secured";
		}
		if (isAuthorized(request)) {
			navigator.navigateTo(defaultView);
		} else {
			navigator.navigateTo("login");
		}

	}

	private boolean isAuthorized(VaadinRequest request) {
		return request.getUserPrincipal() != null;
	}
}

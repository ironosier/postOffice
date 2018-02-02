package org.ironosier.postoffice.vaadin;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("secured")
public class SecuredVaadin extends VerticalLayout implements View {
	
	public SecuredVaadin() {
		addComponent(new Label("Это закрытая страница"));
	}

}

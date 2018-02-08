package org.ironosier.postoffice.vaadin;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("error")
public class ErrorVaadin extends Composite implements View {

	public ErrorVaadin() {
		setCompositionRoot(new VerticalLayout(new Label("This is ERROR view")));
	}

}

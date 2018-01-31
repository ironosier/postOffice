package org.ironosier.postoffice.vaadin;



import javax.inject.Inject;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.service.TestTableService;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIUI("login")
@Title("Почта")
public class EntryVaadin extends UI {

	@Inject
	private TestTableService testTableService;

	@Override
	protected void init(VaadinRequest request) {

		TextField login = new TextField("Логин");
		login.setIcon(VaadinIcons.USER);

		PasswordField pass = new PasswordField("Пароль");
		pass.setIcon(VaadinIcons.PASSWORD);

		Grid<TestTable> grid = new Grid<>("Test Table");
		grid.addColumn(TestTable::getId).setCaption("id");
		grid.addColumn(TestTable::getName).setCaption("name");
		grid.addColumn(TestTable::getPass).setCaption("pass");
		grid.setItems(testTableService.getList());
		grid.addItemClickListener(e -> {
			login.setValue(e.getItem().getName());
			pass.setValue(Integer.toString(e.getItem().getPass()));
		});

		Label label = new Label("Ведите логин и пароль");
	
		
		FormLayout content = new FormLayout();
		content.addComponent(label);
		content.addComponent(login);
		content.addComponent(pass);
		content.addComponent(grid);
		

		Panel panel = new Panel("Авторизация");
		panel.setWidth(null);
		panel.setContent(content);

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(panel);
		layout.setSizeFull();
		layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		setContent(layout);

	}
}

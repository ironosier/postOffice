package org.ironosier.postoffice.vaadin;


import javax.inject.Inject;
import javax.servlet.ServletException;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.service.TableService;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("login")
public class LoginVaadin extends VerticalLayout implements View {

	@Inject
	private TableService service;
	
	@Override
	public void enter(ViewChangeEvent event) {
		TextField login = new TextField("Логин");
		login.setIcon(VaadinIcons.USER);

		PasswordField pass = new PasswordField("Пароль");
		pass.setIcon(VaadinIcons.PASSWORD);

		Grid<TestTable> grid = new Grid<>();
		grid.addColumn(TestTable::getId).setCaption("id");
		grid.addColumn(TestTable::getName).setCaption("name");
		grid.addColumn(TestTable::getPass).setCaption("pass");
		grid.addColumn(TestTable::getRole).setCaption("role");
		grid.setItems(service.getList());
		grid.addItemClickListener(e -> {
			login.setValue(e.getItem().getName());
			pass.setValue(e.getItem().getPass());
		});

		Label label = new Label("Ведите логин и пароль");

		Button loginButton = new Button("Войти", e -> login(login.getValue(), pass.getValue()));

		FormLayout content = new FormLayout();
		content.addComponent(label);
		content.addComponent(login);
		content.addComponent(pass);
		content.addComponent(grid);
		content.addComponent(loginButton);

		Panel panel = new Panel("Авторизация");
		panel.setWidth(null);
		panel.setContent(content);

		addComponent(panel);
		setSizeFull();
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	}

	private void login(String login, String pass) {
		try {
			JaasAccessControl.login(login, pass);
			Notification.show("Доступ разрешен");
			getUI().getNavigator().navigateTo("secured");
		} catch (ServletException e) {
			Notification.show("Доступ запрещен", Type.ERROR_MESSAGE);
		}
	}
}

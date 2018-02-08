package org.ironosier.postoffice.vaadin;



import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.service.TableService;
import org.mindrot.jbcrypt.BCrypt;

import com.vaadin.cdi.CDIView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("manager")
@RolesAllowed({"[manager]"})
public class ManagerVaadin extends VerticalLayout implements View {
	
	@Inject
	private TableService service;
	
	@Override
	public void enter(ViewChangeEvent event) {
		TextField login = new TextField("Логин");
		TextField pass = new TextField("Пароль");
		ListSelect<String> roles = new ListSelect<>("Доступные роли");
		roles.setItems("cashier", "manager");
		VerticalLayout content = new VerticalLayout();
		content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		content.addComponent(new Label("Введите креденшалы нового пользователя"));
		content.addComponent(login);
		content.addComponent(pass);
		content.addComponent(roles);
		content.addComponent(new Button("Зарегестрировать", e -> createUser(login.getValue(), pass.getValue(), roles.getValue().toString())));
		Panel panel = new Panel("Зарегистрировать нового пользователя с шифрованным паролем");
		panel.setSizeUndefined();
		panel.setContent(content);
		addComponent(panel);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

	}
	
	private void createUser(String name, String pass, String role) {
		TestTable table = new TestTable();
		table.setName(name);
		table.setPass(BCrypt.hashpw(pass, BCrypt.gensalt()));
		table.setRole(role);
		table.setRawPass(pass);
		service.createUser(table);
	}

}

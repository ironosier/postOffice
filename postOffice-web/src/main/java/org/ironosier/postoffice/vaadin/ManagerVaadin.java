package org.ironosier.postoffice.vaadin;

import java.util.Arrays;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.service.TableService;
import org.mindrot.jbcrypt.BCrypt;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView("manager")
@RolesAllowed({ "[manager]" })
public class ManagerVaadin extends HorizontalLayout implements View {

	@Inject
	private TableService service;

	private VerticalLayout container;
	private CssLayout menu;

	@Override
	public void enter(ViewChangeEvent event) {

		menu = new CssLayout();

		Label title = new Label("Менеджер");
		title.addStyleName(ValoTheme.MENU_TITLE);

		Label hello = new Label(
				"Здравствуйте, " + JaasAccessControl.getCurrentRequest().getUserPrincipal().getName() + "!");
		hello.addStyleNames(ValoTheme.MENU_SUBTITLE, ValoTheme.MENU_ITEM);

		Button logout = new Button("Выйти из системы", e -> LoginVaadin.logout());
		logout.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.MENU_ITEM);

		Label spaces = new Label();
		Label spaces1 = new Label();
		Label spaces2 = new Label();

		Button createPackage = new Button("Сотрудники", e -> createEmployeeController());
		createPackage.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.MENU_ITEM);

		Button findPackage = new Button("Аналитика", e -> createAnalyticsController());
		findPackage.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.MENU_ITEM);

		menu.addComponents(title, spaces, hello, logout, spaces1, createPackage, spaces2, findPackage);
		menu.addStyleNames(ValoTheme.MENU_ROOT);
		menu.setSizeFull();

		container = new VerticalLayout();

		createEmployeeController();

		addComponents(menu, container);
		setExpandRatio(menu, 1);
		setExpandRatio(container, 5);
		setSizeFull();
	}

	private void createEmployeeController() {

		container.removeAllComponents();

		TextField login = new TextField("Логин");
		login.setWidth(10, Unit.CM);
		TextField pass = new TextField("Пароль");
		pass.setWidth(10, Unit.CM);

		VerticalLayout credentials = new VerticalLayout(login, pass);
		credentials.setSizeUndefined();

		ListSelect<String> roles = new ListSelect<>("Доступные роли", Arrays.asList("cashier", "manager", "charts"));

		TextArea totalEmployee = new TextArea("Новый сотрудник");
		totalEmployee.addStyleNames(ValoTheme.TEXTAREA_HUGE);
		totalEmployee.setReadOnly(true);

		Button register = new Button("Зарегестрировать",
				e -> createUser(login.getValue(), pass.getValue(), roles.getValue().toString()));

		VerticalLayout totalInfo = new VerticalLayout();
		totalInfo.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		totalInfo.addComponents(totalEmployee, register);
		totalInfo.setSizeUndefined();

		HorizontalLayout credentialsExtended = new HorizontalLayout(roles, credentials, totalInfo);

		Grid<TestTable> employees = new Grid<>();
		employees.addColumn(TestTable::getName).setCaption("name");
		employees.addColumn(TestTable::getRawPass).setCaption("raw pass");
		employees.addColumn(TestTable::getPass).setCaption("pass");
		employees.addColumn(TestTable::getRole).setCaption("role");
		employees.setItems(service.getList());
		
		employees.setWidth(25, Unit.CM);
		employees.setHeight(5, Unit.CM);
		
		container.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		container.addComponents(credentialsExtended, employees);
		container.setSizeFull();

	}

	private void createAnalyticsController() {

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

package org.ironosier.postoffice.vaadin;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.rest.AuthRest;
import org.ironosier.postoffice.rest.TestTableRest;
import org.ironosier.postoffice.service.TestTableService;

import com.vaadin.cdi.CDIUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIUI("")
public class EntryVaadin extends UI {

//	@Inject
//	private TestTableRest testTableRest;
//
//	@Inject
//	private AuthRest auth;
	
//	private Label token;
//	private Label securedInfo;
	
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
		
//		Button auth = new Button("Вход", e -> {
//			login(login.getValue(), pass.getValue());
//		});
		
//		token = new Label("token");
//		securedInfo = new Label("первая запись");
		
		FormLayout content = new FormLayout();
//		content.addComponent(securedInfo);
//		content.addComponent(token);
		content.addComponent(label);
		content.addComponent(login);
		content.addComponent(pass);
		content.addComponent(grid);
//		content.addComponent(auth);
		

		Panel panel = new Panel("Авторизация");
		panel.setWidth(null);
		panel.setContent(content);

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(panel);
		layout.setSizeFull();
		layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		getPage().setTitle("Почта");
		setContent(layout);

	}

//	private void login(String name, String pass) {
//		TestTable testTable = new TestTable();
//		testTable.setName(name);
//		testTable.setPass(Integer.parseInt(pass));
//		
//		Response resp = auth.login(testTable);
//		
//		if (resp.getStatus() ==200) {
//			token.setValue(resp.getEntity().toString());
//		}else {
//			token.setValue(resp.getStatusInfo().toString());
//		}
//		TestTable test = (TestTable)testTableRest.getFirst().getEntity();
//		securedInfo.setValue(test.getName() + " " + test.getPass());
//		
//	}
}

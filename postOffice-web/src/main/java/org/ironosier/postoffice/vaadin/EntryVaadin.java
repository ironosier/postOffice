package org.ironosier.postoffice.vaadin;

import java.util.List;

import javax.inject.Inject;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.rest.TestTableRest;

import com.vaadin.cdi.CDIUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIUI("")
@SuppressWarnings("serial")
public class EntryVaadin extends UI {

	@Inject
	private TestTableRest testTableRest;

	@SuppressWarnings("unchecked")
	@Override
	protected void init(VaadinRequest request) {

		Grid<TestTable> grid = new Grid<>("Test Table");
		grid.addColumn(TestTable::getId).setCaption("id");
		grid.addColumn(TestTable::getName).setCaption("name");
		grid.setItems((List<TestTable>)testTableRest.getList().getEntity());
		Label label = new Label("Test FormLayout");
		label.setIcon(VaadinIcons.AIRPLANE);
		
		FormLayout content = new FormLayout();
		content.addComponent(label);
		content.addComponent(grid);

		Panel panel = new Panel("Test Panel");
		panel.setSizeUndefined();
		panel.setContent(content);

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(panel);
		layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		getPage().setTitle("Почта");
		setContent(layout);

	}

}

package org.ironosier.postoffice.vaadin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("cashier")
@RolesAllowed({ "[cashier]" })
@SuppressWarnings("serial")
public class CasherVaadin extends HorizontalLayout implements View {

	private VerticalLayout container;
	private CssLayout menu;

	@Override
	public void enter(ViewChangeEvent event) {

		menu = new CssLayout();

		Label title = new Label("Кассир");
		title.addStyleName(ValoTheme.MENU_TITLE);

		Label hello = new Label(
				"Здравствуйте, " + JaasAccessControl.getCurrentRequest().getUserPrincipal().getName() + "!");
		hello.addStyleNames(ValoTheme.MENU_SUBTITLE, ValoTheme.MENU_ITEM);

		Button logout = new Button("Выйти из системы", e -> LoginVaadin.logout());
		logout.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.MENU_ITEM);

		Label spaces = new Label();
		Label spaces1 = new Label();
		Label spaces2 = new Label();

		Button createPackage = new Button("Новое отправление", e -> createPackageController());
		createPackage.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.MENU_ITEM);
		
		Button findPackage = new Button("Найти посылку", e -> findPackageController());
		findPackage.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.MENU_ITEM);

		menu.addComponents(title, spaces, hello, logout, spaces1, createPackage, spaces2, findPackage);
		menu.addStyleNames(ValoTheme.MENU_ROOT);
		menu.setSizeFull();

		container = new VerticalLayout();
		
		createPackageController();

		addComponents(menu, container);
		setExpandRatio(menu, 1);
		setExpandRatio(container, 5);
		setSizeFull();
	}

	private void createPackageController() {
		// доделать проверку на введенные значения
		container.removeAllComponents();

		Label info = new Label("Создание новой посылки");
		info.addStyleName(ValoTheme.LABEL_HUGE);
		info.setSizeUndefined();

		Label info1 = new Label("Контактная информация");
		info.addStyleName(ValoTheme.LABEL_LARGE);
		info.setSizeUndefined();

		HorizontalLayout actorsNames = new HorizontalLayout();

		TextField senderName = new TextField("ФИО отправителя (фамилия, имя, отчество)");
		senderName.setWidth(15, Unit.CM);
		TextField recipientName = new TextField("ФИО получателя (фамилия, имя, отчество)");
		recipientName.setWidth(15, Unit.CM);

		actorsNames.addComponents(senderName, recipientName);
		actorsNames.setSizeUndefined();

		HorizontalLayout actorsAdresses = new HorizontalLayout();

		TextField senderAdress = new TextField("Адрес отправителя (индекс, населенный пункт, улица, дом, квартира)");
		senderAdress.setWidth(15, Unit.CM);
		TextField recipientAdress = new TextField("Адрес получателя (индекс, населенный пункт, улица, дом, квартира)");
		recipientAdress.setWidth(15, Unit.CM);

		actorsAdresses.addComponents(senderAdress, recipientAdress);
		actorsAdresses.setSizeUndefined();

		Label info2 = new Label("Информация о посылке");
		info.addStyleName(ValoTheme.LABEL_LARGE);
		info.setSizeUndefined();

		HorizontalLayout emails = new HorizontalLayout();

		TextField senderEmail = new TextField("Электронная почта отправителя");
		senderEmail.setWidth(10, Unit.CM);
		TextField recipientEmail = new TextField("Электронная почта получателя");
		recipientEmail.setWidth(10, Unit.CM);

		emails.addComponents(senderEmail, recipientEmail);
		emails.setVisible(false);
		emails.setSizeUndefined();

		HorizontalLayout phones = new HorizontalLayout();

		TextField senderPhone = new TextField("Телефон отправителя");
		senderPhone.setWidth(10, Unit.CM);
		TextField recipientPhone = new TextField("Телефон получателя");
		recipientPhone.setWidth(10, Unit.CM);

		phones.addComponents(senderPhone, recipientPhone);
		phones.setVisible(false);
		phones.setSizeUndefined();

		HorizontalLayout deliveryOptions = new HorizontalLayout();

		RadioButtonGroup<String> packageType = new RadioButtonGroup<>("Тип упаковки", Arrays.asList("Пакет",
				"Маленькая коробка", "Средняя коробка", "Большая коробка", "Индивидуальное решение"));

		RadioButtonGroup<String> isUnbreakablePackage = new RadioButtonGroup<>("Ударопрочная упаковка",
				Arrays.asList("Да", "Нет"));
		isUnbreakablePackage.setValue("Нет");

		CheckBoxGroup<String> deliveryType = new CheckBoxGroup<>("Способ доставки",
				Arrays.asList("Экспресс - доставка", "Курьерская доставка"));

		CheckBoxGroup<String> notifications = new CheckBoxGroup<>("Уведомления", Arrays.asList("Отправителю на E-mail",
				"Получателю на E-mail", "Отправителю на телефон", "Получателю на телефон"));
		// доделать лисенер
		notifications.addValueChangeListener(e -> {
			Set<String> values = e.getValue();
			if (!values.isEmpty()) {
				if (values.contains("Отправителю на E-mail")) {
					emails.setVisible(true);
					senderEmail.setEnabled(true);
					recipientEmail.setEnabled(false);
				}
				if (values.contains("Получателю на E-mail")) {
					emails.setVisible(true);
					senderEmail.setEnabled(true);
					recipientEmail.setEnabled(false);
				}
				if (values.contains("Отправителю на телефон")) {
					phones.setVisible(true);
					senderPhone.setEnabled(true);
					recipientPhone.setEnabled(false);
				}

			} else {
				emails.setVisible(false);
				phones.setVisible(false);
			}
		});

		RadioButtonGroup<String> getCheckCopy = new RadioButtonGroup<>("Получить копию накладной на E-mail",
				Arrays.asList("Да", "Нет"));
		getCheckCopy.setValue("Нет");

		deliveryOptions.addComponents(packageType, isUnbreakablePackage, deliveryType, notifications, getCheckCopy);
		deliveryOptions.setSizeUndefined();

		HorizontalLayout result = new HorizontalLayout();

		TextField weight = new TextField("Вес посылки");
		//прикрутить логику рассчета цены 
		TextArea price = new TextArea("Итоговая цена");
		price.addStyleNames(ValoTheme.TEXTAREA_TINY);
		price.setWidth(3, Unit.CM);
		price.setHeight(1, Unit.CM);
		price.setReadOnly(true);

		result.addComponents(weight, price);
		result.setSizeUndefined();

		Button sendButton = new Button("Зарегестрировать отправление");
		sendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

		container.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		container.addComponents(info, info1, actorsNames, actorsAdresses, emails, phones, info2, deliveryOptions,
				result, sendButton);

		container.setExpandRatio(info, 0.1f);
		container.setExpandRatio(info1, 0.1f);
		container.setExpandRatio(actorsNames, 0.1f);
		container.setExpandRatio(actorsAdresses, 0.1f);
		container.setExpandRatio(emails, 0.1f);
		container.setExpandRatio(phones, 0.1f);
		container.setExpandRatio(info2, 0.1f);
		container.setExpandRatio(deliveryOptions, 0.3f);
		container.setExpandRatio(result, 0.1f);
		container.setExpandRatio(sendButton, 0.1f);
		container.setSizeFull();
	}
	
	private void findPackageController() {
		container.removeAllComponents();
		
		Label info = new Label("Поиск посылки");
		info.addStyleName(ValoTheme.LABEL_HUGE);
		info.setSizeUndefined();
		
		HorizontalLayout searchFields = new HorizontalLayout();
		
		TextField searchParameter = new TextField("Введите трек-номер");
		searchParameter.setWidth(15, Unit.CM);
		
		RadioButtonGroup<String> searchType = new RadioButtonGroup<>("Тип поиска", Arrays.asList("Трек-номер", "Имя", "Адрес", "Телефон", "E-mail"));
		searchType.setValue("Трек-номер");
		searchType.addValueChangeListener(e -> searchParameter.setCaption("Введите " + e.getValue().toLowerCase()));
		
		searchFields.addComponents(searchType, searchParameter);
		searchFields.setSizeUndefined();
		
		Grid<FakeResult> searhResultMatches = new Grid<>();
		
		searhResultMatches.addColumn(FakeResult::getName).setCaption("Имя");
		searhResultMatches.addColumn(FakeResult::getAddress).setCaption("Адрес");
		searhResultMatches.addColumn(FakeResult::isStatus).setCaption("Статус");
		
		List<FakeResult> fakeResults;
		
		fakeResults = Stream.generate(() -> Math.random())
		.map(e -> new FakeResult("FakeName№ " + e, "FakeAddress№ " + e, false))
		.limit(10)
		.collect(Collectors.toList());
		
		TextArea totalResult = new TextArea();
		totalResult.addStyleNames(ValoTheme.TEXTAREA_LARGE);
		totalResult.setReadOnly(true);
		totalResult.setVisible(false);
		
		Button resultButton = new Button("Выдать посылку");
		resultButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		resultButton.setVisible(false);
		
		searhResultMatches.setItems(fakeResults);
//		доделать лисенер
		searhResultMatches.addItemClickListener(e -> {
			totalResult.setValue(e.getItem().name + "\n" + e.getItem().address);
			totalResult.setVisible(true);
			resultButton.setVisible(true);
		});
		searhResultMatches.setWidth(20, Unit.CM);
		searhResultMatches.setHeight(7, Unit.CM);
		
		HorizontalLayout resultFields = new HorizontalLayout();
		
		resultFields.addComponents(searhResultMatches, totalResult);
		resultFields.setSizeUndefined();
		
		container.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		container.addComponents(info, searchFields, resultFields, resultButton);
		
		container.setExpandRatio(info, 0.1f);
		container.setExpandRatio(searchFields, 0.2f);
		container.setExpandRatio(resultFields, 0.4f);
		container.setExpandRatio(resultButton, 0.2f);
		container.setSizeFull();
		
	}
	
	private class FakeResult{
		private String name;
		private String address;
		private boolean status;
		
		private FakeResult(String name, String address, boolean status) {
			this.name = name;
			this.address = address;
			this.status = status;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		
		
	}

}

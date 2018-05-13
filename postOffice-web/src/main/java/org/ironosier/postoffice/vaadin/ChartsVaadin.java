package org.ironosier.postoffice.vaadin;

import java.math.RoundingMode;
import java.net.Inet4Address;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jboss.logging.Logger;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("charts")
@SuppressWarnings("serial")
public class ChartsVaadin extends HorizontalLayout implements View {

	private Logger log = Logger.getLogger(getClass());

	private Label name = new Label("Получатель");
	TextField nameTxt = new TextField();
	private Label theme = new Label("Тема");
	TextField themeTxt = new TextField();
	private Upload upload = new Upload();
	TextArea area = new TextArea();
	Button button = new Button("Отправить");
	

	@Override
	public void enter(ViewChangeEvent event) {

		Label title = new Label("Графики");
		title.addStyleNames(ValoTheme.MENU_TITLE);

		CssLayout container = new CssLayout();
		container.setSizeFull();
		


		Button logout = new Button("Logout", e -> LoginVaadin.logout());
		Button button1 = new Button("Равномерное распределение", e -> {
			container.removeAllComponents();
			VerticalLayout layout = new VerticalLayout(name, nameTxt, theme, themeTxt, upload, area,button);
			container.addComponent(layout);
		});
		Button button2 = new Button("Второй график", e -> {
			container.removeAllComponents();
			container.addComponent(new Label("Ето второй график"));
		});
		Button button3 = new Button("PIE CHART", e -> {
			container.removeAllComponents();
			container.addComponent(createPie());
		});

		logout.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.MENU_ITEM);
		button1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
		button2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
		button3.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

		CssLayout menu = new CssLayout(title, logout, button1, button2, button3);
		menu.addStyleName(ValoTheme.MENU_ROOT);

		addComponents(menu, container);
		setSizeFull();

	}

	private ChartJs createBar(int number, int interval) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);

//		double z, h, y, x, res, res1, res2;
//		z = -7.5;
//		h = 0.1;
//		a = 30;
//		b = 60;
		
		int size, p, a, x;
		p = 2147483647;
		a = 630360016;
		x = 4;
		size = 100000;
		String[] intervals = new String[interval];
		Random random = new Random();
		double[] array = new double[number];
		int[] count = new int[interval];
		
		for (int i = 0; i < array.length; i++) {
			array[i] = (a*x) % p;
		}
		
		System.out.println(Arrays.toString(intervals));
		
//		for (int i = 0; i < number; i++) {
////			res1 = z/2+a/2*h + (Math.random() * ((z/2+b/2*h - z/2+a/2*h) + 1));
////			res2 = z/2+a/2*h + (Math.random() * ((z/2+b/2*h - z/2+a/2*h) + 1));
////			res = res1+res2;
//			//res = ((z / 2 + h * a) + (int)(Math.random() * ((z / 2 + h * (b - a) / 8) - (z / 2 + h * a) + 1)) + (z/2+h * (b - a) / 8 + (int)(Math.random() * ((z/2+h * (b - a) / 4 - z/2+h * (b - a) / 8) + 1))));
//			numbers[i] = df.format(res);
//			for (int j = 1; j < interval; j++) {
//				if (Double.valueOf(numbers[i]) < Double.valueOf(intervals[j])
//						& Double.valueOf(numbers[i]) >= Double.valueOf(intervals[j - 1])) {
//					count[j]++;
//				}
//			}
//		}
		
		for (int i = 0; i < interval; i++) {
			count[24] = 7500;
		}
		System.out.println(Arrays.toString(count));
		
		
		BarChartConfig barConfig = new BarChartConfig();
        barConfig.
            data()
                .labels(intervals)
                .addDataset(
                		  new BarDataset().backgroundColor("rgba(220,220,220)").label("Нормальное распределение/встроенная").yAxisID("y-axis-1"))
                .and();
        barConfig.
            options()
                .responsive(true)
                .hover()
                    .mode(InteractionMode.INDEX)
                    .intersect(true)
                    .animationDuration(400)
                    .and()
                .title()
                    .display(true)
                    .text("Распределение")
                    .and()
                .scales()
                    .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
                    .and()
               .done();

        List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
            		data.add((double) count[i]);
            }
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        
        return chart;
	}

	private ChartJs createPie() {
		PieChartConfig config = new PieChartConfig();
		config.data().labels("Red", "Green", "Yellow", "Grey", "Dark Grey")
				.addDataset(new PieDataset().label("Dataset 1")).and();

		config.options().responsive(true).title().display(true).text("ПИРОГ").and().animation().animateScale(true)
				.animateRotate(true).and().done();

		List<String> labels = config.data().getLabels();
		for (Dataset<?, ?> ds : config.data().getDatasets()) {
			PieDataset lds = (PieDataset) ds;
			List<Double> data = new ArrayList<>();
			List<String> colors = new ArrayList<>();
			for (int i = 0; i < labels.size(); i++) {
				data.add((double) (Math.round(Math.random() * 100)));
				colors.add(ColorUtils.randomColor(0.7));
			}
			lds.backgroundColor(colors.toArray(new String[colors.size()]));
			lds.dataAsList(data);
		}

		ChartJs chart = new ChartJs(config);
		chart.setJsLoggingEnabled(true);

		return chart;
	}
}

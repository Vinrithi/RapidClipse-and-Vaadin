package com.company.myproject1.ui.component;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by elon on 3/17/17.
 */
public class PropertiesStatsPieChart extends VerticalLayout {

    private static final long serialVersionUID = 4894923343920891837L;

    private ChartJs chart;
    private String[] labels;
    private double[] values;
    private String[] colors;

    public  PropertiesStatsPieChart(String[] labels, double[] values, String[] colors){

        this.labels = labels;
        this.values = values;
        this.colors = colors;

        Component _chart = buildPieChart();
        Component refreshButton = buildRefreshChartDataButon();

        //addComponent(refreshButton);
        addComponent(_chart);
        //setComponentAlignment(refreshButton, Alignment.TOP_CENTER);
        setComponentAlignment(_chart, Alignment.MIDDLE_CENTER);
        setExpandRatio(_chart, 1);
        setCaption("Top 5 most populated properties");

    }

    private Component buildPieChart(){
        PieChartConfig config = new PieChartConfig();
        config
                .data()
                .labels(labels[0], labels[1], labels[2], labels[3], labels[4])
                .addDataset(new PieDataset().label("Dataset 1"))
                .and();

        config.
                options()
                .responsive(true)
                /*.title()
                .display(true)
                .text("Chart.js Pie Chart (Data Refresh)")
                .and()*/
                .legend()
                .display(true)
                .fullWidth(true)
                .position(Position.BOTTOM)
                /*.and()
                .animation()
                .animateScale(true)
                .animateRotate(true)*/
                .and()
                .done();

        chart = new ChartJs(config);
        chart.setJsLoggingEnabled(true);
        chart.addClickListener((a,b) -> {
            PieDataset dataset = (PieDataset) config.data().getDatasets().get(a);
            ChartUtils.notification(a, b, dataset);
        });
        refreshChatData();
        //chart.setWidth(100, Unit.PERCENTAGE);

        //chart.setSizeFull();
        chart.setWidthUndefined();
        Responsive.makeResponsive(chart);

        return chart;
    }

    private void refreshChatData() {
        getData(chart.getConfig());
        chart.refreshData();
    }

    private Component buildRefreshChartDataButon(){
        Button refreshButton = new Button("Refresh Data", FontAwesome.REFRESH);
        refreshButton.addClickListener(e -> refreshChatData());

        return refreshButton;
    }

    private void getData(ChartConfig chartConfig) {
        PieChartConfig config = (PieChartConfig) chartConfig;
        List<String> labels = config.data().getLabels();
        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            PieDataset lds = (PieDataset) ds;
            List<Double> data = new ArrayList<>();
            List<String> color = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add(values[i]);
                color.add(colors[i]);
            }
            lds.backgroundColor(color.toArray(new String[color.size()]));
            lds.dataAsList(data);
        }
    }
}

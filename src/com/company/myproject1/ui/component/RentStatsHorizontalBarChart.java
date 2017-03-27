package com.company.myproject1.ui.component;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.options.AnimationEasing;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/*
 * Created by elon on 3/14/2017.
 */
public class RentStatsHorizontalBarChart extends VerticalLayout {

    public RentStatsHorizontalBarChart(String[] labels, String color, List<Double> values){
        /*setSizeFull();*/
        /*addStyleName("spark");*/
        /*setDefaultComponentAlignment(Alignment.TOP_CENTER);*/
        setCaption("Rent payment analysis (KES in millions)");

        addComponent(createRentStatsHorizontalChart(labels, color, values));
    }

    private Component createRentStatsHorizontalChart(String[] labels, String color, List<Double> values){
        BarChartConfig barConfig = new BarChartConfig();
        barConfig.horizontal();
        barConfig.
                data()
                .labels(labels[0], labels[1], labels[2], labels[3], labels[4], labels[5], labels[6], labels[7], labels[8], labels[9])
                .addDataset(new BarDataset().backgroundColor(color))
                .and()
                .options()
                .responsive(true)
                .animation()
                .easing(AnimationEasing.easeInOutCubic)
                .and()
                /*.title()
                .display(true)
                .text("Rent payment analysis (KES in millions)")
                .and()*/
                .elements()
                .rectangle()
                .borderWidth(0)
                /*.borderColor("rgb(0, 255, 0)")
                .borderSkipped(Rectangle.RectangleEdge.LEFT)*/
                .and()
                .and()
                .legend()
                .display(false)
                /*.fullWidth(true)*/
                .position(Position.LEFT)
                .and()
                .done();

        //List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            /*List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((double) (Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100));
            }*/
            lds.dataAsList(values);
        }

        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.addClickListener((a,b) -> {
            BarDataset dataset = (BarDataset) barConfig.data().getDatasets().get(a);
            ChartUtils.notification(a, b, dataset);
        });

        /*chart.setHeight("200px");
        chart.setWidth("450px");*/
        chart.setSizeFull();
        Responsive.makeResponsive(chart);

        return chart;
    }
}

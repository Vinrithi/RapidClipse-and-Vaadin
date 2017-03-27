package com.company.myproject1.ui.component;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.AnimationEasing;
import com.byteowls.vaadin.chartjs.options.scale.*;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class SparklineChart extends VerticalLayout {

    public SparklineChart(final String name, final String unit,
                          final String prefix, final String color,
                          final double[] values, final String[] labels,
                          final int proposedMax, final int proposedMin) {
        setSizeUndefined();
        addStyleName("spark");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);

        Label current = new Label(prefix + values[values.length - 1] + unit);
        current.setSizeUndefined();
        current.addStyleName(ValoTheme.LABEL_HUGE);
        addComponent(current);

        Label title = new Label(name);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_SMALL);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        addComponent(title);

        addComponent(buildChartJS(values, labels, color, proposedMax, proposedMin));

        List<Double> vals = Arrays.asList(ArrayUtils.toObject(values));
        Label highLow = new Label("High <b>" + java.util.Collections.max(vals)
                + "</b> &nbsp;&nbsp;&nbsp; Low <b>"
                + java.util.Collections.min(vals) + "</b>", ContentMode.HTML);
        highLow.addStyleName(ValoTheme.LABEL_TINY);
        highLow.addStyleName(ValoTheme.LABEL_LIGHT);
        highLow.setSizeUndefined();
        addComponent(highLow);

    }

    private Component buildSparkline(final int[] values, final String[] labels, final Color color) {
        Chart spark = new Chart();
        spark.getConfiguration().setTitle("");
        spark.getConfiguration().getChart().setType(ChartType.LINE);
        spark.getConfiguration().getChart().setAnimation(false);
        spark.setWidth("120px");
        spark.setHeight("40px");

        DataSeries series = new DataSeries();
        for (int i = 0; i < values.length; i++) {
            DataSeriesItem item = new DataSeriesItem("", values[i]);
            series.add(item);
        }
        spark.getConfiguration().setSeries(series);
        spark.getConfiguration().getTooltip().setEnabled(false);

        Configuration conf = series.getConfiguration();
        Legend legend = new Legend();
        legend.setEnabled(false);
        conf.setLegend(legend);

        Credits c = new Credits("");
        spark.getConfiguration().setCredits(c);

        PlotOptionsLine opts = new PlotOptionsLine();
        opts.setAllowPointSelect(false);
        opts.setColor(color);
        opts.setDataLabels(new Labels(false));
        opts.setLineWidth(1);
        opts.setShadow(false);
        opts.setDashStyle(DashStyle.SOLID);
        opts.setMarker(new Marker(false));
        opts.setEnableMouseTracking(false);
        opts.setAnimation(false);
        spark.getConfiguration().setPlotOptions(opts);

        XAxis xAxis = spark.getConfiguration().getxAxis();
        YAxis yAxis = spark.getConfiguration().getyAxis();

        SolidColor transparent = new SolidColor(0, 0, 0, 0);

        xAxis.setLabels(new Labels(false));
        xAxis.setTickWidth(0);
        xAxis.setLineWidth(0);

        yAxis.setTitle(new Title(""));
        yAxis.setAlternateGridColor(transparent);
        yAxis.setLabels(new Labels(false));
        yAxis.setLineWidth(0);
        yAxis.setGridLineWidth(0);

        return spark;
    }

    private Component buildChartJS(final double[] values, final String[] labels, final String color, final int proposedMax, final int proposedMin){
        LineChartConfig lineConfig = new LineChartConfig();
        lineConfig.data()
                .labels(labels[0], labels[1], labels[2], labels[3])
                .addDataset(new LineDataset()
                        .borderColor(color)
                        .backgroundColor("rgba(0, 0, 0, 0)")
                        .fill(true))
                .and()
                .options()
                .responsive(true)
                .animation()
                .easing(AnimationEasing.easeInOutCubic)
                .and()
                .legend()
                .display(false)
                .and()
                .scales()
                .add(com.byteowls.vaadin.chartjs.options.scale.Axis.X, new CategoryScale()
                        .display(false)
                        .scaleLabel()
                        .display(false)
                        .and())
                .add(com.byteowls.vaadin.chartjs.options.scale.Axis.Y, new LinearScale()
                        .display(false)
                        .scaleLabel()
                        .display(false)
                        .and()
                        .ticks()
                        .suggestedMin(proposedMin)
                        .suggestedMax(proposedMax)
                        .and())
                .and()
                .done();

        for (Dataset<?, ?> ds : lineConfig.data().getDatasets()) {
            LineDataset lds = (LineDataset) ds;
            lds.data(values[0], values[1], values[2], values[3]);
        }

        ChartJs chart = new ChartJs(lineConfig);
        chart.addClickListener((a,b) -> {
            LineDataset dataset = (LineDataset) lineConfig.data().getDatasets().get(a);
            ChartUtils.notification(a, b, dataset);
        });
        chart.setJsLoggingEnabled(true);
        chart.setSizeFull();
        Responsive.makeResponsive(chart);

        return  chart;
    }
}

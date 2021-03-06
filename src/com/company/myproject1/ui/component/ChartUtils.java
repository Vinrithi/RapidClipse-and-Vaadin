package com.company.myproject1.ui.component;

/*
 * Created by elon on 3/14/2017.
 */
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public abstract class ChartUtils {

    public static final String RGB_RED = "rgb(255, 99, 132)";
    public static final String RGB_ORANGE = "rgb(255, 159, 64)";
    public static final String RGB_YELLOW = "rgb(255, 205, 86)";
    public static final String RGB_GREEN = "rgb(75, 192, 192)";
    public static final String RGB_BLUE = "rgb(54, 162, 235)";
    public static final String RGB_PURPLE = "rgb(153, 102, 255)";
    public static final String RGB_GREY = "rgb(231,233,237)";
    public static final String valoBlue = "rgb(25,124,221)";
    public static final String valoGreen = "rgb(152,223,88)";
    public static final String valoYellow = "rgb(249,221,81)";
    public static final String valoRed = "rgb(236,100,100)";
    public static final String valoCyan = "rgb(61, 245, 237)";
    public static final String GITHUB_REPO_URL = "https://github.com/moberwasserlechner/vaadin-chartjs/tree/master/demo/src/main/java/";

    public static double randomScalingFactor() {
        return (double) (Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100);
    }

    public static void notification(int dataSetIdx, int dataIdx, Dataset<?, ?> dataset) {
        Notification.show("Dataset at Idx:" + dataSetIdx + "; Data at Idx: " + dataIdx + "; Value: " + dataset.getData().get(dataIdx), Type.TRAY_NOTIFICATION);
    }

    public static String getPathToClass(Class<?> clazz) {
        String path = clazz.getCanonicalName().replaceAll("\\.", "/") + ".java";
        return path;
    }

    public static String getGithubPath(Class<?> clazz) {
        return GITHUB_REPO_URL + getPathToClass(clazz);
    }

    public static Color[] chartColors = new Color[] {
            new SolidColor("#3090F0"), new SolidColor("#18DDBB"),
            new SolidColor("#98DF58"), new SolidColor("#F9DD51"),
            new SolidColor("#F09042"), new SolidColor("#EC6464") };
}
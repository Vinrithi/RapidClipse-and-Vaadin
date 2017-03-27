package com.company.myproject1.ui.domain;

import java.util.Date;

/*
 * Created by ochomoswill on 3/3/2017.
 */
public class Bill {

    //"SELECT `id`, `property_id`, `unit_id`, `month`, `year`, `previous_water_reading`, `current_water_reading`, `water_bill`, `service_charge`, `date_recorded`, `biling_status` FROM `billing`"

    private Long id;
    private String propertyId;
    private String unitId;
    private String month;
    private int year;
    private int previousWaterReading;
    private int currentWaterReading;
    private double waterBill;
    private double serviceCharge;
    private Date dateRecorded;
    private String billingStatus;

    public Bill(){}

    public Bill(Long id,String propertyId, String unitId,String month,int year, int previousWaterReading, int currentWaterReading, double waterBill,double serviceCharge, Date dateRecorded,String billingStatus){

        this.id = id;
        this.propertyId =propertyId;
        this.unitId =unitId;
        this.month = month;
        this.year = year;
        this.previousWaterReading =previousWaterReading;
        this.currentWaterReading = currentWaterReading;
        this.waterBill = waterBill;
        this.serviceCharge = serviceCharge;
        this.dateRecorded = dateRecorded;
        this.billingStatus = billingStatus;
    }

    /* Getters */

    public Long getId() {
        return id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getPreviousWaterReading() {
        return previousWaterReading;
    }

    public int getCurrentWaterReading() {
        return currentWaterReading;
    }

    public double getWaterBill() {
        return waterBill;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public String getBillingStatus() {
        return billingStatus;
    }


    /* Setters */

    public void setId(Long id) {
        this.id = id;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPreviousWaterReading(int previousWaterReading) {
        this.previousWaterReading = previousWaterReading;
    }

    public void setCurrentWaterReading(int currentWaterReading) {
        this.currentWaterReading = currentWaterReading;
    }

    public void setWaterBill(double waterBill) {
        this.waterBill = waterBill;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }
}

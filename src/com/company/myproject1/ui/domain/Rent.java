package com.company.myproject1.ui.domain;

import java.util.Date;

/*
 * Created by ochomoswill on 3/3/2017.
 */


public class Rent {

    //"SELECT `id`, `property_id`, `unit_id`, `month`, `year`, `paid_by`, `amount_paid`, `received_by`, `date_paid`, `transaction_code` FROM `rent_payment`"

    private Long id;
    private String propertyId;
    private String unitId;
    private String month;
    private int year;
    private String payee;
    private double amountPaid;
    private String receivedBy;
    private Date datePaid;
    private String transactionCode;


    public Rent(){}

    public Rent(Long id, String propertyId, String unitId, String month, int year , String payee, double amountPaid, String receivedBy, Date datePaid, String transactionCode){

        this.id = id;
        this.propertyId = propertyId;
        this.unitId = unitId;
        this.month = month;
        this.year = year;
        this.payee = payee;
        this.amountPaid = amountPaid;
        this.receivedBy = receivedBy;
        this.datePaid = datePaid;
        this.transactionCode = transactionCode;

    }

    /*Getter*/

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

    public String getPayee() {
        return payee;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public String getTransactionCode() {
        return transactionCode;
    }


    /* Setters*/

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

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}

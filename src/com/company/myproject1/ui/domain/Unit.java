package com.company.myproject1.ui.domain;

import javax.naming.Context;
import java.util.Date;

/*
 * Created by ochomoswill on 3/3/2017.
 */
public class Unit {

    //"SELECT `id`, `unit_id`, `property_id`, `unit_name`, `classification`, `price`, `status`, `date_added` FROM `units` WHERE 1"
    Context mContext;

    private Long id;
    private String unitId;
    private String propertyId;
    private String unitName;
    private String classification;
    private double price = 0.0;
    private String status;
    private Date dateAdded;




    public Unit(){}

    public Unit(Long id, String unitId, String propertyId, String unitName, String classification, double price, String status, Date dateAdded){
        this.id =  id;
        this.unitId = unitId;
        this.propertyId =  propertyId;
        this.unitName =  unitName;
        this.classification = classification;
        this.price = price;
        this.status = status;
        this.dateAdded = dateAdded;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getClassification() {
        return classification;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    /* Settters*/

    public void setId(Long id) {
        this.id = id;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}

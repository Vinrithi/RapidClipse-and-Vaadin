package com.company.myproject1.ui.domain;

import java.util.Date;

/*
 * Created by ochomoswill on 3/3/2017.
 */
public class Tenant {

    //"SELECT `id`, `tenant_id`, `unit_id`, `tenant_name`, `national_id_number`, `phone_number`, `date_added`, `tenancy_status` FROM `tenants` WHERE 1"

    private Long id;
    private String tenantID;
    private String unitID;
    private String tenantName;
    private Long nationalIDnumber;
    private Long phoneNumber;
    private Date dateAdded;
    private String tenancyStatus;

    public Tenant(){}

    public Tenant(Long id, String tenantID, String unitID, String tenantName, Long nationalIDnumber, Long phoneNumber, Date dateAdded, String tenancyStatus){

        this.id = id;
        this.tenantID = tenantID;
        this.unitID = unitID;
        this.tenantName = tenantName;
        this.nationalIDnumber = nationalIDnumber;
        this.phoneNumber = phoneNumber;
        this.dateAdded = dateAdded;
        this.tenancyStatus = tenancyStatus;
    }

    /*Getters*/

    public Long getId() {
        return id;
    }

    public String getTenantID() {
        return tenantID;
    }

    public String getUnitID() {
        return unitID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Long getNationalIDnumber() {
        return nationalIDnumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getTenancyStatus() {
        return tenancyStatus;
    }

    /* Setters*/

    public void setId(Long id) {
        this.id = id;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setNationalIDnumber(Long nationalIDnumber) {
        this.nationalIDnumber = nationalIDnumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setTenancyStatus(String tenancyStatus) {
        this.tenancyStatus = tenancyStatus;
    }
}

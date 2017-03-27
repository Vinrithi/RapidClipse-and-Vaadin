package com.company.myproject1.ui.domain;

import javax.naming.Context;
import java.util.Date;

/*
 * Created by ochomoswill on 3/3/2017.
 */
public class Property {

    //"SELECT `id`, `property_id`, `landlord_id`, `property_name`, `location`, `number_of_units`, `vacant_units`, `date_added`, `status`, `land_registration_number`, `water_bill_rate`, `agency_fee`, `caretaker_fee` FROM `properties` WHERE 1";

    Context mContext;

    private Long id;
    private String property_id;
    private String landlord_id;
    private String property_name;
    private String location;
    private Integer number_of_units = 0;
    private Integer vacant_units = 0;
    private Date date_added;
    private String status;
    private String land_registration_number;
    private Integer water_bill_rate = 0;
    private Double agency_fee = 0.0;
    private Double caretaker_fee = 0.0;

    public Property(){}

    public Property(Long id,String property_id, String landlord_id, String property_name, String location, Integer number_of_units, Integer vacant_units, Date date_added, String status, String land_registration_number, Integer water_bill_rate, Double agency_fee, Double caretaker_fee){

        this.id = id;
        this.property_id = property_id;
        this.landlord_id = landlord_id;
        this.property_name = property_name;
        this.location = location;
        this.number_of_units = number_of_units;
        this.vacant_units = vacant_units;
        this.date_added = date_added;
        this.status = status;
        this.land_registration_number = land_registration_number;
        this.water_bill_rate = water_bill_rate;
        this.agency_fee = agency_fee;
        this.caretaker_fee = caretaker_fee;
    }

    /*Getters*/
    public Long getId(){return id;}

    public String getProperty_id() {
        return property_id;
    }

    public String getLandlord_id() {
        return landlord_id;
    }

    public String getProperty_name() {
        return property_name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumber_of_units() {
        return number_of_units;
    }

    public int getVacant_units() {
        return vacant_units;
    }

    public Date getDate_added() {
        return date_added;
    }

    public String getStatus() {
        return status;
    }

    public String getLand_registration_number() {
        return land_registration_number;
    }

    public int getWater_bill_rate() {
        return water_bill_rate;
    }

    public double getAgency_fee() {
        return agency_fee;
    }

    public double getCaretaker_fee() {
        return caretaker_fee;
    }

    /*Setters*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setLandlord_id(String landlord_id) {
        this.landlord_id = landlord_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }


    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNumber_of_units(int number_of_units) {
        this.number_of_units = number_of_units;
    }

    public void setVacant_units(int vacant_units) {
        this.vacant_units = vacant_units;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLand_registration_number(String land_registration_number) {
        this.land_registration_number = land_registration_number;
    }

    public void setWater_bill_rate(int water_bill_rate) {
        this.water_bill_rate = water_bill_rate;
    }

    public void setAgency_fee(double agency_fee) {
        this.agency_fee = agency_fee;
    }

    public void setCaretaker_fee(double caretaker_fee) {
        this.caretaker_fee = caretaker_fee;
    }
}

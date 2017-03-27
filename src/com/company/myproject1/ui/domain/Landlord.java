package com.company.myproject1.ui.domain;

import javax.naming.Context;
import java.util.Date;

/*
 * Created by ochomoswill on 3/2/2017.
 */
public class Landlord {

   /* "`id`, `landlord_id`, `landlord_name`, `national_id_number`, `phone_number`, `email_address`, `kra_pin`, `banker`, `account_number`, `date_added"*/
    Context mContext;
    private Long id;
    private String landlord_id;
    private String landlord_name;
    private String nationalIDnumber;
    private String phone_number;
    private String email_address;
    private String kra_pin;
    private String banker;
    private String account_number;
    private Date date_added;

    public Landlord(){}

    public Landlord(Long id, String landlord_id, String landlord_name, String nationalIDnumber, String phone_number, String email_address, String kra_pin, String banker, String account_number, Date date_added){

        this.id = id;
        this.landlord_id = landlord_id;
        this.landlord_name = landlord_name;
        this.nationalIDnumber = nationalIDnumber;
        this.phone_number = phone_number;
        this.email_address = email_address;
        this.kra_pin = kra_pin;
        this.banker = banker;
        this.account_number = account_number;
        this.date_added = date_added;

    }

    /* getters and setters */

    //public Long getId(){ return id;}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandlord_id() { return landlord_id; }

    public void setLandlord_id(String landlord_id) {
        this.landlord_id = landlord_id;
    }

    public String getLandlord_name() {return landlord_name; }

    public void setLandlord_name(String landlord_name) {
        this.landlord_name = landlord_name;
    }

    public String getNationalIDnumber(){return nationalIDnumber;}

    public void setNationalIDnumber(String national_id_number) {
        this.nationalIDnumber = national_id_number;
    }

    public String getPhone_number(){ return phone_number;}

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address(){ return email_address;}

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getKra_pin() {return kra_pin;}

    public void setKra_pin(String kra_pin) {
        this.kra_pin = kra_pin;
    }

    public String getBanker(){return banker;}

    public void setBanker(String banker) {
        this.banker = banker;
    }

    public String getAccount_number(){return account_number;}

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Date getDate_added(){return date_added;}

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }
}

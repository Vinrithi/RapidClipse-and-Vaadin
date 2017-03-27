package com.company.myproject1.ui.data.data_from_db;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Landlord;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/2/2017.
 */

@Component
@Service
public class LandlordService {

    JdbcTemplate jdbcTemplate;

    public LandlordService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource2());
    }

    public List<Landlord> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT id, landlord_id, landlord_name, national_id_number, phone_number, email_address, kra_pin, banker, account_number, date_added FROM landlords",
                    (rs, rowNum) -> new Landlord(rs.getLong("id"),
                            rs.getString("landlord_id"),
                            rs.getString("landlord_name"),
                            Long.toString(rs.getLong("national_id_number")),
                            Long.toString(rs.getLong("phone_number")),
                            rs.getString("email_address"),
                            rs.getString("kra_pin"),
                            rs.getString("banker"),
                            Long.toString(rs.getLong("account_number")),
                            rs.getDate("date_added")));

        }
        catch (final InvalidResultSetAccessException e)
        {
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            throw new RuntimeException(e);
        }



    }

    public void update(final Landlord landlord){
        System.out.println("New id to update: "+landlord.getId());
        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE landlords SET landlord_name = ?,national_id_number = ?,phone_number = ?,email_address = ?,kra_pin = ?,banker = ?,account_number = ?,date_added = ? WHERE id =  ?",
                    landlord.getLandlord_name(),
                    landlord.getNationalIDnumber(),
                    landlord.getPhone_number(),
                    landlord.getEmail_address(),
                    landlord.getKra_pin(),
                    landlord.getBanker(),
                    landlord.getAccount_number(),
                    landlord.getDate_added(),
                    landlord.getId());

            Notification.show("Update on Landlord was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Landlord. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Landlord. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public void add(final Landlord landlord){

        try
        {
            // Your Code
            this.jdbcTemplate.update("INSERT INTO landlords (landlord_name, national_id_number, phone_number, email_address, kra_pin, banker, account_number, date_added) VALUES (?,?,?,?,?,?,?,?)",
                    landlord.getLandlord_name(),
                    landlord.getNationalIDnumber(),
                    landlord.getPhone_number(),
                    landlord.getEmail_address(),
                    landlord.getKra_pin(),
                    landlord.getBanker(),
                    landlord.getAccount_number(),
                    landlord.getDate_added());

            Notification.show("New Landlord was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Landlord. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Landlord. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }


    }

}

package com.company.myproject1.ui.data.data_from_db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Property;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/3/2017.
 */

@Component
@Service
public class PropertyService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public PropertyService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource2());

    }

    public List<Property> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT id, property_id, landlord_id, property_name,location, number_of_units, vacant_units, date_added, " +
                            "status, land_registration_number, water_bill_rate, agency_fee, caretaker_fee FROM properties",
                    (rs, rowNum) -> new Property(rs.getLong("id"),
                            rs.getString("property_id"),
                            rs.getString("landlord_id"),
                            rs.getString("property_name"),
                            rs.getString("location"),
                            rs.getInt("number_of_units"),
                            rs.getInt("vacant_units"),
                            rs.getDate("date_added"),
                            rs.getString("status"),
                            rs.getString("land_registration_number"),
                            rs.getInt("water_bill_rate"),
                            rs.getDouble("agency_fee"),
                            rs.getDouble("caretaker_fee")));
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

    public void update(final Property property){

        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE properties SET landlord_id=?,property_name=?,location=?,number_of_units=?," +
                            "date_added=?,status=?,land_registration_number=?,water_bill_rate=?,agency_fee=?,caretaker_fee=? WHERE id=?",
                    property.getLandlord_id(),
                    property.getProperty_name(),
                    property.getLocation(),
                    property.getNumber_of_units(),
                    property.getDate_added(),
                    property.getStatus(),
                    property.getLand_registration_number(),
                    property.getWater_bill_rate(),
                    property.getAgency_fee(),
                    property.getCaretaker_fee(),
                    property.getId());
            Notification.show("Property update was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Property. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);

        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Property. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }



    }

    public void add(final Property property){



        try
        {
            // Your Code
            this.jdbcTemplate.update("INSERT INTO properties(landlord_id, property_name, location,number_of_units,  date_added, status, land_registration_number, water_bill_rate, agency_fee, caretaker_fee)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?)",
                    property.getLandlord_id(),
                    property.getProperty_name(),
                    property.getLocation(),
                    property.getNumber_of_units(),
                    property.getDate_added(),
                    property.getStatus(),
                    property.getLand_registration_number(),
                    property.getWater_bill_rate(),
                    property.getAgency_fee(),
                    property.getCaretaker_fee());

            Notification.show("New Property was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Property. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Property. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }


    }

}

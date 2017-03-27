package com.company.myproject1.ui.data.data_from_db;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Unit;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/3/2017.
 */
@Component
public class UnitService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public UnitService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource2());

    }

    public List<Unit> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT `id`, `unit_id`, `property_id`, `unit_name`, `classification`, `price`, `status`, `date_added` FROM `units`",
                    (rs, rowNum) -> new Unit(rs.getLong("id"),
                            rs.getString("unit_id"),
                            rs.getString("property_id"),
                            rs.getString("unit_name"),
                            rs.getString("classification"),
                            rs.getDouble("price"),
                            rs.getString("status"),
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

    public void update(final Unit unit){


        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE units SET property_id=?,unit_name=?,classification=?,price=?,status=?,date_added=? WHERE id = ?",
                    unit.getPropertyId(),
                    unit.getUnitName(),
                    unit.getClassification(),
                    unit.getPrice(),
                    unit.getStatus(),
                    unit.getDateAdded(),
                    unit.getId());

            Notification.show("Update on Unit was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Unit. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Unit. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    public void add(final Unit unit){



        try
        {

            // Your Code
            this.jdbcTemplate.update("INSERT INTO units( property_id, unit_name, classification, price, status, date_added) VALUES (?,?,?,?,?,?)",
                    unit.getPropertyId(),
                    unit.getUnitName(),
                    unit.getClassification(),
                    unit.getPrice(),
                    unit.getStatus(),
                    unit.getDateAdded());

            Notification.show("New Unit was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Unit. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Unit. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

}

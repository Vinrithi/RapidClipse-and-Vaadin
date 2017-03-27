package com.company.myproject1.ui.data.data_from_db;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Rent;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/3/2017.
 */
@Component
public class RentService {

    //"SELECT `id`, `property_id`, `unit_id`, `month`, `year`, `paid_by`, `amount_paid`, `received_by`, `date_paid`, `transaction_code` FROM `rent_payment`"

    @Autowired
    JdbcTemplate jdbcTemplate;

    public RentService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource1());

    }

    public List<Rent> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT id, property_id, unit_id, month, year, paid_by, amount_paid, received_by, date_paid, transaction_code FROM rent_payment",
                    (rs, rowNum) -> new Rent(rs.getLong("id"),
                            rs.getString("property_id"),
                            rs.getString("unit_id"),
                            rs.getString("month"),
                            rs.getInt("year"),
                            rs.getString("paid_by"),
                            rs.getDouble("amount_paid"),
                            rs.getString("received_by"),
                            rs.getDate("date_paid"),
                            rs.getString("transaction_code")));


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

    public void update(final Rent rent){


        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE rent_payment SET property_id=?,unit_id=?,month=?,year=?,paid_by=?,amount_paid=?,received_by=?,date_paid=?,transaction_code=? WHERE id = ?",
                    rent.getPropertyId(),
                    rent.getUnitId(),
                    rent.getMonth(),
                    rent.getYear(),
                    rent.getPayee(),
                    rent.getAmountPaid(),
                    rent.getReceivedBy(),
                    rent.getDatePaid(),
                    rent.getTransactionCode(),
                    rent.getId());

            Notification.show("Update on Rent was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Rent. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Rent. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    public void add(final Rent rent){


        try
        {
            // Your Code
            this.jdbcTemplate.update("INSERT INTO `rent_payment`(property_id`, `unit_id`, `month`, `year`, `paid_by`, `amount_paid`, `received_by`, `date_paid`, `transaction_code`) VALUES (?,?,?,?,?,?,?,?,?)",
                    rent.getPropertyId(),
                    rent.getUnitId(),
                    rent.getMonth(),
                    rent.getYear(),
                    rent.getPayee(),
                    rent.getAmountPaid(),
                    rent.getReceivedBy(),
                    rent.getDatePaid(),
                    rent.getTransactionCode());

            Notification.show("New Rent was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Rent. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Rent. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }


    }

}

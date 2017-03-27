package com.company.myproject1.ui.data.data_from_db;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Bill;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/3/2017.
 */
@Component
public class BillService {

    //"SELECT `id`, `property_id`, `unit_id`, `month`, `year`, `previous_water_reading`, `current_water_reading`, `water_bill`, `service_charge`, `date_recorded`, `biling_status` FROM `billing`"


    @Autowired
    JdbcTemplate jdbcTemplate;

    public BillService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource2());

    }

    public List<Bill> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT `id`, `property_id`, `unit_id`, `month`, `year`, `previous_water_reading`, `current_water_reading`, `water_bill`, `service_charge`, `date_recorded`, `biling_status` FROM `billing`",
                    (rs, rowNum) -> new Bill(rs.getLong("id"),
                            rs.getString("property_id"),
                            rs.getString("unit_id"),
                            rs.getString("month"),
                            rs.getInt("year"),
                            rs.getInt("previous_water_reading"),
                            rs.getInt("current_water_reading"),
                            rs.getDouble("water_bill"),
                            rs.getDouble("service_charge"),
                            rs.getDate("date_recorded"),
                            rs.getString("biling_status")));


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

    public void update(final Bill bill){


        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE `billing` SET `property_id`=?,`unit_id`=?,`month`=?,`year`=?,`previous_water_reading`=?,`current_water_reading`=?,`water_bill`=?,`service_charge`=?,`date_recorded`=?,`biling_status`=? WHERE id = ?",
                    bill.getPropertyId(),
                    bill.getUnitId(),
                    bill.getMonth(),
                    bill.getYear(),
                    bill.getPreviousWaterReading(),
                    bill.getCurrentWaterReading(),
                    bill.getWaterBill(),
                    bill.getServiceCharge(),
                    bill.getDateRecorded(),
                    bill.getBillingStatus(),
                    bill.getId());

            Notification.show("Update on Bill was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Bill. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Bill. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    public void add(final Bill bill){


        try
        {
            // Your Code
            this.jdbcTemplate.update("INSERT INTO `billing`(`property_id`, `unit_id`, `month`, `year`, `previous_water_reading`, `current_water_reading`, `water_bill`, `service_charge`, `date_recorded`, `biling_status`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    bill.getPropertyId(),
                    bill.getUnitId(),
                    bill.getMonth(),
                    bill.getYear(),
                    bill.getPreviousWaterReading(),
                    bill.getCurrentWaterReading(),
                    bill.getWaterBill(),
                    bill.getServiceCharge(),
                    bill.getDateRecorded(),
                    bill.getBillingStatus());

            Notification.show("New Bill was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Bill. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Bill. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

}

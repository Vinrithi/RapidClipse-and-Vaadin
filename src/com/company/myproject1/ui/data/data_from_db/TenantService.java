package com.company.myproject1.ui.data.data_from_db;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.Tenant;
import com.vaadin.ui.Notification;

/*
 * Created by ochomoswill on 3/3/2017.
 */
@Component
public class TenantService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TenantService(){
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(Conn.dataSource2());

    }

    public List<Tenant> findAll(){


        try
        {
            // Your Code
            return this.jdbcTemplate.query("SELECT `id`, `tenant_id`, `unit_id`, `tenant_name`, `national_id_number`, `phone_number`, `date_added`, `tenancy_status` FROM `tenants`",
                    (rs, rowNum) -> new Tenant(rs.getLong("id"),
                            rs.getString("tenant_id"),
                            rs.getString("unit_id"),
                            rs.getString("tenant_name"),
                            rs.getLong("national_id_number"),
                            rs.getLong("phone_number"),
                            rs.getDate("date_added"),
                            rs.getString("tenancy_status")));


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

    public void update(final Tenant tenant){


        try
        {
            // Your Code
            this.jdbcTemplate.update("UPDATE tenants SET unit_id=?,tenant_name= ?,national_id_number=?,phone_number=?," +
                            "date_added=?,tenancy_status=? WHERE id = ?",
                    tenant.getUnitID(),
                    tenant.getTenantName(),
                    tenant.getNationalIDnumber(),
                    tenant.getPhoneNumber(),
                    tenant.getDateAdded(),
                    tenant.getTenancyStatus(),
                    tenant.getId());

            Notification.show("Update on Tenant was successful!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error updating the Tenant. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error updating the Tenant. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    public void add(final Tenant tenant){
        System.out.println(tenant.getUnitID());
        System.out.println(tenant.getTenantName());
        System.out.println(tenant.getNationalIDnumber());
        System.out.println(tenant.getPhoneNumber());
        System.out.println(tenant.getTenancyStatus());
        System.out.println(tenant.getDateAdded());

        try
        {
            // Your Code
            this.jdbcTemplate.update( "INSERT INTO tenants(unit_id, tenant_name, national_id_number, phone_number, date_added, tenancy_status) VALUES (?,?,?,?,?,?)",
                    tenant.getUnitID(),
                    tenant.getTenantName(),
                    tenant.getNationalIDnumber(),
                    tenant.getPhoneNumber(),
                    tenant.getDateAdded(),
                    tenant.getTenancyStatus());

            Notification.show("New Tenant was successfully added!", Notification.Type.HUMANIZED_MESSAGE);

        }
        catch (final InvalidResultSetAccessException e)
        {
            Notification.show("Error adding the Tenant. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (final DataAccessException e)
        {
            Notification.show("Error adding the Tenant. Check your inputs!", Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

}

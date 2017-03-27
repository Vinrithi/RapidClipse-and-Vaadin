package com.company.myproject1.ui.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.company.myproject1.ui.event.Notifications;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/*
 * Created by elon on 3/8/2017.
 */
public class Conn {
    public Conn(){}

    /*public static DataSource dataSource1(){

        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/kejapay?autoReconnect=true&useSSL=false");
        dataSourceBuilder.username("vicente");
        dataSourceBuilder.password("vincent96");
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        return dataSourceBuilder.build();
    }*/

    @Bean
    @Primary
    public static DriverManagerDataSource dataSource1()
    {
    	final DriverManagerDataSource dr = new DriverManagerDataSource() ;
        dr.setUrl("jdbc:mysql://localhost:3306/kejapay?autoReconnect=true&useSSL=false");
        dr.setUsername("vicente");
        dr.setPassword("vincent96");
        dr.setDriverClassName("com.mysql.jdbc.Driver");
        return dr;
    }

    public static DriverManagerDataSource dataSource2()
    {
    	final DriverManagerDataSource dr = new DriverManagerDataSource() ;
        dr.setUrl("jdbc:mysql://localhost:3306/kejapay?autoReconnect=true&useSSL=false");
        dr.setUsername("vicente");
        dr.setPassword("vincent96");
        return dr;
    }

    public static Connection getConn() throws SQLException {
         Connection conn = null;

         try{
             conn = DriverManager.getConnection(
                     "jdbc:mysql://localhost:3306/kejapay?autoReconnect=true&useSSL=false",
                     "vicente",
                     "vincent96");

             return conn;
         }catch(final Exception e){
             Notifications.showNotification("Error!", "<span>Unable to Connect to our services. Kindly retry later.</span>", Notification.Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
         }

        return null;
    }
}

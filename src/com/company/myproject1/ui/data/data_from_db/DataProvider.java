package com.company.myproject1.ui.data.data_from_db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.company.myproject1.ui.dbconn.Conn;
import com.company.myproject1.ui.domain.User;
import com.company.myproject1.ui.event.Notifications;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/*
 * Created by elon on 3/8/2017.
 */
public class DataProvider implements com.company.myproject1.ui.data.DataProvider {

    @Override
    public User authenticate(final String userName, final String password) {
        try{

            final Connection conn = Conn.getConn();

            final Statement sqlQueryStatement = conn.createStatement();

            final String sqlQuery = "SELECT * FROM user_accounts WHERE password = '"+password+"' AND (username = '"+userName+"' OR email_address = '"+userName+"')";

            final ResultSet rs = sqlQueryStatement.executeQuery(sqlQuery);

            if(!rs.next()) {
                Notifications.showNotification("Error!", "<span>Unable to log in. This may be to incorrect credentials.</span>", Notification.Type.ERROR_MESSAGE, 3000, "", Position.TOP_CENTER);
                return null;
            }

            System.out.println("Executed Query!");

            final User user = new User(
                    Integer.toString(rs.getInt("user_id")),
                    rs.getString("username"),
                    rs.getString("email_address"),
                    rs.getString("phone_number"),
                    rs.getString("password"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("gender"),
                    rs.getString("national_id_number"),
                    rs.getString("status"),
                    rs.getString("date_added"),
                    rs.getString("designation"),
                    rs.getString("authorisation_level"),
                    rs.getString("bio")
            );

            conn.close();

            return user;



        }catch(final Exception e){
            System.out.print("Error in Executing Query! : "+e.toString());
        }

        return null;
    }

    @Override
    public void updateUser(final User user){
        try{
            final Connection conn = Conn.getConn();

            final Statement sqlQueryStatement = conn.createStatement();

            final String sqlQuery = "UPDATE `cedar_kejapay`.`user_accounts` SET " +
                    "`username`='"+user.getUserName()+"',"+
                    "`email_address`='"+user.getEmail()+"'," +
                    "`phone_number`="+user.getPhoneNumben()+"," +
                    "`password`="+user.getPassword()+"," +
                    "`first_name`='"+user.getFirstName()+"'," +
                    "`last_name`='"+user.getLastName()+"', " +
                    "`gender`='"+user.getGender()+"', " +
                    "`national_id_number`="+user.getNationalID()+", " +
                    "`bio`='"+user.getBio()+"'" +
                    " WHERE `user_id`="+user.getUserId();

            System.out.println("UPDATE `cedar_kejapay`.`user_accounts` SET " +
                    "`email_address`='"+user.getEmail()+"'," +
                    "`phone_number`="+user.getPhoneNumben()+"," +
                    "`password`="+user.getPassword()+"," +
                    "`first_name`='"+user.getFirstName()+"'," +
                    "`last_name`='"+user.getLastName()+"', " +
                    "`national_id_number`="+user.getNationalID()+", " +
                    "`bio`='"+user.getBio()+"'" +
                    " WHERE `username`='"+user.getUserId()+"'");

           sqlQueryStatement.execute(sqlQuery);

            System.out.println("Executed Query2!");

            conn.close();
        }catch(final Exception e){
            System.out.print("Error in Executing Query! : "+e.toString());
        }
    }
}

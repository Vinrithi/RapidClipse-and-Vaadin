package com.company.myproject1.ui.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.myproject1.ui.dbconn.Conn;

/*
 * Created by ochomoswill on 3/18/2017.
 */
public class ComboBoxService {

    public ComboBoxService(){}

    // Function code
    public static Object[] populateComboBox(final String idField, final String nameField, final String fieldTable){

        final List<String> fieldID = new ArrayList<>();
        final List<String> fieldName = new ArrayList<>();

        try{

            final Connection conn = Conn.getConn();
            final Statement sqlQueryStatement = conn.createStatement();
            final String sqlQuery = "SELECT "+idField+","+nameField+" FROM "+fieldTable+" ";

            System.out.println(sqlQuery);
            final ResultSet rs = sqlQueryStatement.executeQuery(sqlQuery);

            while(rs.next())
            {
                fieldID.add(rs.getString(idField));
                fieldName.add(rs.getString(nameField));
            }

            conn.close();


        }catch(final Exception e){
            System.out.print("Error in Executing Query! : "+e.toString());
        }

        final String[] list1 = new String[fieldID.size()];
        final String[] fieldIDarray = fieldID.toArray(list1);

        final String[] list2 = new String[fieldName.size()];
        final String[] fieldNamearray = fieldName.toArray(list2);

        return new Object[] {fieldIDarray, fieldNamearray};

    }

}

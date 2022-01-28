package com.hxm.demo1;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        java.sql.Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        ResultSet rs = mystatement.executeQuery("  SELECT * FROM  area");
        while(rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String area_code = rs.getString("area_code");
            String city_code = rs.getString("city_code");
            System.out.println(id+"  "+name+" "+" "+area_code+" "+city_code);
        }
        if(mystatement != null) {
            mystatement.close();
            mystatement=null;
        }
        if(myConnection !=null) {
            myConnection.close();
            myConnection=null;
        }
    }
}

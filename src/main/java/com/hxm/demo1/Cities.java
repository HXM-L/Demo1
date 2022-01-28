package com.hxm.demo1;

import com.hxm.bean.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cities {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*csv文件读取*/
        File csvArea = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\cities.csv");
        csvArea.setReadable(true);
        csvArea.setWritable(true);
        List<City> cityList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvArea))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                City city = new City();
                String[] lineStr = line.split(",");
                city.setCode(lineStr[0]);
                city.setName(lineStr[1].replace("\""," "));
                city.setProvinceCode(lineStr[2]);
//                System.out.println(area);
                cityList.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cityList);

        /*插入数据库*/
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        /*SQL语句要改*/
        String sql = "insert into cities values(?,?,?);";
        for (City city : cityList) {
            PreparedStatement preparedStatement = myConnection.prepareStatement(sql);
            preparedStatement.setString(1,city.getCode());
            preparedStatement.setString(2,city.getName());
            preparedStatement.setString(3,city.getProvinceCode());
            int result=preparedStatement.executeUpdate();
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

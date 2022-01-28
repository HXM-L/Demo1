package com.hxm.demo1;

import com.hxm.bean.City;
import com.hxm.bean.Street;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Streets {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*csv文件读取*/
        File csvStreet = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\streets.csv");
        csvStreet.setReadable(true);
        csvStreet.setWritable(true);
        List<Street> streetList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvStreet))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                Street street = new Street();
                String[] lineStr = line.split(",");
                street.setCode(lineStr[0]);
                street.setName(lineStr[1].replace("\""," "));
                street.setAreaCode(lineStr[2]);
                street.setProvinceCode(lineStr[3]);
                street.setCityCode(lineStr[4]);
                streetList.add(street);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(streetList);

        /*插入数据库*/
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        /*SQL语句要改*/
        String sql = "insert into streets values(?,?,?,?,?);";
        for (Street street : streetList) {
            PreparedStatement preparedStatement = myConnection.prepareStatement(sql);
            preparedStatement.setString(1,street.getCode());
            preparedStatement.setString(2,street.getName());
            preparedStatement.setString(3,street.getAreaCode());
            preparedStatement.setString(4,street.getProvinceCode());
            preparedStatement.setString(5,street.getCityCode());
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

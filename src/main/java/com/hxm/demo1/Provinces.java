package com.hxm.demo1;

import com.hxm.bean.Area;
import com.hxm.bean.Province;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Provinces {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*csv文件读取*/
        File csvArea = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\provinces.csv");
        csvArea.setReadable(true);
        csvArea.setWritable(true);
        List<Province> provinceList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvArea))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                Province province=new Province();
                String[] lineStr = line.split(",");
                province.setCode(lineStr[0]);
                province.setName(lineStr[1].replace("\""," "));
                provinceList.add(province);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(provinceList);

        /*插入数据库*/
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        String sql = "insert into provinces values(?,?);";
        for (Province province : provinceList) {
            PreparedStatement preparedStatement = myConnection.prepareStatement(sql);
            preparedStatement.setString(1,province.getCode());
            preparedStatement.setString(2,province.getName());
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

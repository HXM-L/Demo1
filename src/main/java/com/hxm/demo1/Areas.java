package com.hxm.demo1;

import com.hxm.bean.Area;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Areas {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*csv文件读取*/
        File csvArea = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\areas.csv");
        csvArea.setReadable(true);
        csvArea.setWritable(true);
        List<Area> areaList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvArea))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                Area area=new Area();
                String[] lineStr = line.split(",");
                area.setCode(lineStr[0]);
                area.setName(lineStr[1].replace("\""," "));
                area.setCityCode(lineStr[2]);
                area.setProvinceCode(lineStr[3]);
//                System.out.println(area);
                areaList.add(area);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(areaList);
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        String sql = "insert into areas values(?,?,?,?);";
        for (Area area : areaList) {
            PreparedStatement preparedStatement = myConnection.prepareStatement(sql);
            preparedStatement.setString(1,area.getCode());
            preparedStatement.setString(2,area.getName());
            preparedStatement.setString(3,area.getCityCode());
            preparedStatement.setString(4,area.getProvinceCode());
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

package com.hxm.demo1;

import com.hxm.bean.Street;
import com.hxm.bean.Village;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Villages {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException {
        /*csv文件读取*/
//        File csvStreet = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\villages.csv");
//        csvStreet.setReadable(true);
//        csvStreet.setWritable(true);

        DataInputStream csvStreet = new DataInputStream(new FileInputStream(new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\villages.csv")));
        List<Village> villageList = new ArrayList<>();
        //解决中文乱码
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvStreet,"GBK"))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                Village village = new Village();
                String[] lineStr = line.split(",");
                village.setCode(lineStr[0]);
                village.setName(lineStr[1].replace("\""," "));
                village.setAreaCode(lineStr[5]);
                village.setProvinceCode(lineStr[3]);
                village.setCityCode(lineStr[4]);
                village.setStreetCode(lineStr[2]);
                villageList.add(village);
                System.out.println(village);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(villageList.size());
        System.out.println(villageList.get(1));

        /*插入数据库*/
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/vaccination_system";
        String user = "root";
        String password = "root";
        Connection myConnection = DriverManager.getConnection(url,user,password);
        Statement mystatement = myConnection.createStatement();
        /*SQL语句要改*/
        String sql = "insert into villages values(?,?,?,?,?,?);";
        for (Village village : villageList) {
            PreparedStatement preparedStatement = myConnection.prepareStatement(sql);
            preparedStatement.setString(1,village.getCode());
            preparedStatement.setString(2,village.getName());
            preparedStatement.setString(3,village.getAreaCode());
            preparedStatement.setString(4,village.getProvinceCode());
            preparedStatement.setString(5,village.getCityCode());
            preparedStatement.setString(6,village.getStreetCode());
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

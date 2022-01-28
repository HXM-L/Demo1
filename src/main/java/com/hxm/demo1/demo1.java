package com.hxm.demo1;

import com.hxm.bean.Area;

import java.io.*;
import java.util.UUID;

public class demo1 {
    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 0; i < 20; i++) {
            System.out.println(UUID.randomUUID());
        }
        File csvArea = new File("E:\\GraduationProject\\参考材料\\Administrative-divisions-of-China-master\\dist\\areas.csv");
        csvArea.setReadable(true);
        csvArea.setWritable(true);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvArea))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                System.out.println(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

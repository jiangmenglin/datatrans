package com.hanke.clobrw;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 读取指定文件下sql脚本，执行到数据库
 * 朱行读取分批处理批量插入数据库
 */
public class TestReadFile {
    public static void main(String[] args) {
        System.err.println("begin");
        long start = System.currentTimeMillis();
        File file = new File("C:\\w\\ccclubs_yun_sys");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File item : files) {
                String path = item.getPath();
                getData(path);
            }
        }
        System.err.print((System.currentTimeMillis() - start) / 1000);

    }

    private static void getData(String path) {
        //读取文件
        BufferedReader reader;
        Connection conn = null;
        Statement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.8.188:3306/ccclubs_yun_sys?useUnicode=true&characterEncoding=utf-8", "root", "123456");
            pst = conn.createStatement();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                pst.addBatch(line);
                  /*  System.out.println("-----------------------");
                    System.out.println(line);
                    System.out.println("-----------------------");*/
                if (i % 100 == 0) {
                    System.out.println("执行了：" + i);
                    pst.executeBatch();
                }
                i += 1;
            }
            reader.close();
            // 执行批量更新
            pst.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



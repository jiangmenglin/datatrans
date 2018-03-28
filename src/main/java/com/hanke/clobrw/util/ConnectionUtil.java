package com.hanke.clobrw.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ConnectionUtil {
	
	public static Connection getConn(String type) {
		try {
			FileInputStream fis = new FileInputStream(
					Thread.currentThread().getContextClassLoader()
					.getResource("param.properties").getFile());
			Properties prop = new Properties();
			prop.load(fis);
			String driver = null;
			String url = null;
			String user = null;
			String passwd = null;
			driver = prop.getProperty(type + "Driver");
			url = prop.getProperty(type + "Db");
			user = prop.getProperty(type + "Username");
			passwd = prop.getProperty(type + "Password");
			Class.forName(driver);
			return DriverManager.getConnection(url, user, passwd);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(ResultSet rs, PreparedStatement pm, Connection conn) {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(pm != null) {
				pm.close();
				pm = null;
			}
			if(conn != null) {
				conn.close();
				conn = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}

package com.whyuan.$6utils.db;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * 通过属性文件获取连接信息：通过类加载器ClassLoader获取资源文件URL
 * @author Administrator
 *
 */
public class ConnectionUtils {

	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	static {
		Properties props = new Properties();///Properties是Hashtable的子类。
		try {
			// 从属性文件中读取数据库配置信息
			props.load(ConnectionUtils.class.getClassLoader()
					.getResourceAsStream(
							"com/db_oracle.properties"));
		} catch (IOException e) {
      e.printStackTrace();
		}
		if (props != null) {
			url = props.getProperty("url");
			driver = props.getProperty("driver");
			username = props.getProperty("username");
			password = props.getProperty("password");

			// 装载并注册数据库驱动
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
			}
		}
	}

	/**
	 * 获得连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection openConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
	}

}
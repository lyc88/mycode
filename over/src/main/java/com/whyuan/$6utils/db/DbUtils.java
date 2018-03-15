package com.whyuan.$6utils.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author   袁武辉
 * @see      数据库连接及操作工具类
 * @version  0.9
 * @date 2015-6-30
 */
public class DbUtils {

	/*数据库的连接对象  GC*/
	private static Connection conn = null;
	/*语句对象 */
	private static Statement stmt = null;
	/*结果集对象 */
	private static ResultSet rs = null;



	/*数据库的URL*/
	private static String url = "jdbc:mysql://localhost:3308/filmsale";
	/*数据库的用户名*/
	private static String username = "root";
	/*数据库的密码*/
	private static String password = "mysql";

	//静态代码块  类一加载数据库就能连接上
	//只执行一次
	static{

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}


	}


	/**
	 * @see   更新表中记录
	 * @param @param sql
	 * @param @return
	 * @return int
	 * @data 2015-6-30
	 */
	public int update(String sql){
		try{

			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);

			return i;
		}catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	//一个方法是 查询

	/**
	 * @see   查询
	 * @param @param sql
	 * @param @return
	 * @return ResultSet
	 * @data 2015-6-30
	 */
	public List<Map<String,Object>>  query(String sql){
		try{

			ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//获取表结构信息
			ResultSetMetaData rsmd = rs.getMetaData();

			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				for(int i = 1; i<=rsmd.getColumnCount(); i++){
					String columnName = rsmd.getColumnName(i);
					Object columnValue = rs.getObject(columnName);
			/*		String dataType = rsmd.getColumnTypeName(i);
					if(dataType.equals("int")){
						Object columnValue = rs.getObject(columnName);
					}
					*/

					map.put(columnName, columnValue);
				}
				list.add(map);
			}

			return list;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}




	public ArrayList<String[]> arrQuery(String sql){
		ArrayList<String[]> arrl = new ArrayList<String[]>();
		try {
			rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmt = rs.getMetaData();

			while(rs.next()){
				String[] arr = new String[rsmt.getColumnCount()];//4 4
				for (int i = 1; i <=rsmt.getColumnCount(); i++) {

					arr[i-1] = rs.getString(i);
				}
				arrl.add(arr);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}



		return arrl;
	}








	//一个方法是 关闭

	public void close(){

		try {

			if(rs != null){

				rs.close();
			}
			if(stmt != null){

				stmt.close();

			}if(conn != null){

				conn.close();

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}

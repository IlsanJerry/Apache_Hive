package hiveexam;


import java.sql.*;

public class HiveTest4 {

	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.111.120:10000/edu", "root", "password");
		System.out.println(conn);
		String tableName = "test";
		Statement stmt = conn.createStatement();
		stmt.execute("drop table if exists " + tableName);
		stmt.execute("create table " + tableName + "(clickdate string, pid string) row format delimited fields terminated by ' ' stored as textfile");
		String sql = "show tables '" + tableName + "'";
		System.out.println("-------------------------------");
		System.out.println("Running: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
		sql = "describe " + tableName;
		System.out.println("-------------------------------");
		System.out.println("Running: " + sql);
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString(1)+"\t"+rs.getString(2));
		}
		String filePath = "c:/unico/product_click.log";
		sql = "load data local inpath '" + filePath + "' into table " + tableName;
		System.out.println("-------------------------------");
		System.out.println("Running: " + sql);
		stmt.execute(sql);
		sql = "select * from " + tableName;
		System.out.println("Running: " + sql);
		rs = stmt.executeQuery(sql);
		rs.close();
		stmt.close();
		conn.close();
	}

}

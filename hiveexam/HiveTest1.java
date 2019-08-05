package hiveexam;
import java.sql.*;
public class HiveTest1 {
	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection
				("jdbc:hive2://192.168.111.120:10000/mydb", 
						"root", "password");		
		Statement stmt = conn.createStatement();
		String sql = "show tables";
		System.out.println("-------------------------------");
		System.out.println("Running: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs = stmt.executeQuery("select * from clicklog");
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t" + 
		                           rs.getString(2));
		}
		rs.close();
		stmt.close();
		conn.close();
	}

}

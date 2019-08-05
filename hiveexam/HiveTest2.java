package hiveexam;
import java.sql.*;
public class HiveTest2 {
	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.111.120:10000/mydb", "root", "password");
		/*
		 *  clicklog 테이블에서 pid  별로 클릭갯수를 출력하는 기능
		 *  화면 pid  와 해당 pid의 갯수를 출력
		 */				
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select pid, count(pid) as clickcount from clicklog group by pid");
		while(rs.next()) {
			System.out.println(rs.getString(1)+"\t"+rs.getString(2));
		}
		rs.close();
		stmt.close();
		conn.close();
	}
}

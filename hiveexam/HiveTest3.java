package hiveexam;
import java.sql.*;
public class HiveTest3 {
	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.111.120:10000/mydb", "root", "password");
		/*
		 *   오전 10시 시간대(10시~10시59분)에 클릭된 
		 *   상품 ID 출력 ->   출력 형식 : 시간  상품ID		  
		 */
		
		PreparedStatement pstmt = conn.prepareStatement("select substr(clickdate, 9, 2), pid from clicklog where substr(clickdate, 9, 2) = ?");
		pstmt.setString(1, "10");
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1)+"\t"+rs.getString(2));
		}
		rs.close();
		pstmt.close();
		conn.close();
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import vo.SubwayVO;

@Repository
public class SubwayDAO {
	@Autowired
	@Qualifier("hiveDataSource")	
	DataSource ds;
	
	public List<SubwayVO> select1(int i) {
		System.out.println("select1의 i값 :"+i);
		List<SubwayVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select *from subway where line='line_"+i+"'");
			rs = pstmt.executeQuery();
			
			SubwayVO vo = null;
			while(rs.next()) {
				vo = new SubwayVO();				
				vo.setLine(rs.getString(1));
				vo.setTime(rs.getString(2));
				vo.setRide(rs.getInt(3));
				vo.setTakeoff(rs.getInt(4));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			} catch (Exception e) {
				e.printStackTrace();				
			} 
		}
		return list;		
	}
}



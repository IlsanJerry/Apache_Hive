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

import vo.OnePersonVO;
import vo.SubwayVO;

@Repository
public class OnePersonDAO {
	@Autowired
	@Qualifier("hiveDataSource")	
	DataSource ds;

//1인 가구가 많은 순으로 전체 출력 : select *from oneperson order by oneperson desc;
//구별 1인가구 명수 출력 : select gu, sum(onePerson) from oneperson group by gu;
//1인 가구가 제일 많은 동의 구 이름과, 동 이름 출력:select * from oneperson order by onePerson desc limit 1;
//1인 가구수가 제일 많은 구 이름 출력 select gu, sum(onePerson) aa from oneperson group by gu order by aa desc limit 1;

	
	public List<OnePersonVO> select1(int i) {
		System.out.println("select1의 i값 :"+i);
		List<OnePersonVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select *from oneperson order by oneperson desc");
			rs = pstmt.executeQuery();
			
			OnePersonVO vo = null;
			while(rs.next()) {
				vo = new OnePersonVO();				
				vo.setDong(rs.getString(1));
				vo.setOnePerson(rs.getInt(2));
				vo.setGu(rs.getString(3));
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
	
	public List<OnePersonVO> select2(int i) {
		System.out.println("select2의 i값 :"+i);
		List<OnePersonVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select gu, sum(onePerson) from oneperson group by gu");
			rs = pstmt.executeQuery();
			
			OnePersonVO vo = null;
			while(rs.next()) {
				vo = new OnePersonVO();				
				vo.setGu(rs.getString(1));
				vo.setOnePerson(rs.getInt(2));
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
	
	public List<OnePersonVO> select3(int i) {
		System.out.println("select3의 i값 :"+i);
		List<OnePersonVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from oneperson order by onePerson desc limit 1");
			rs = pstmt.executeQuery();
			
			OnePersonVO vo = null;
			while(rs.next()) {
				vo = new OnePersonVO();				
				vo.setDong(rs.getString(1));
				vo.setOnePerson(rs.getInt(2));
				vo.setGu(rs.getString(3));
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
	
	public List<OnePersonVO> select4(int i) {
		System.out.println("select4의 i값 :"+i);
		List<OnePersonVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select gu, sum(onePerson) aa from oneperson group by gu order by aa desc limit 1");
			rs = pstmt.executeQuery();
			
			OnePersonVO vo = null;
			while(rs.next()) {
				vo = new OnePersonVO();				
				vo.setGu(rs.getString(1));
				vo.setOnePerson(rs.getInt(2));
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
	
	
	
	 public List<OnePersonVO> search(String search){
	      List<OnePersonVO> list = new ArrayList<>();
	      Connection conn = null;
	      OnePersonVO vo = null;
	      Statement stmt=null;
	      ResultSet rs=null;
	      try {
	         conn = ds.getConnection();
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery("select gu, dong, onePerson "
	               + "from oneperson where gu like '%"+search+"%'");
	         while(rs.next()) {
	            vo = new OnePersonVO();
	         vo.setGu(rs.getString(1));
	         vo.setDong(rs.getString(2));
	         vo.setOnePerson(rs.getInt(3));
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



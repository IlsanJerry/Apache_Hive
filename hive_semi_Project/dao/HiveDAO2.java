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

import vo.FruitsVO;

@Repository
public class HiveDAO2 {
	@Autowired
	@Qualifier("hiveDataSource")	
	DataSource ds;
	public boolean create() { 
		Connection conn = null;
		Statement stmt = null;
		boolean result = true;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("create table fruits2 (year string, name string, qty int, price int) "+
						 "row format delimited fields terminated " + 
						 "by ',' lines terminated by '\n' " +
						 "stored as textfile");
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			} catch (Exception e) {
				e.printStackTrace();				
			} 
		}
		return result;
	}
	public boolean drop() {
		Connection conn = null;
		Statement stmt = null;
		boolean result = true;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("drop table fruits2");
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			} catch (Exception e) {
				e.printStackTrace();				
			} 
		}
		return result;
	}
	public boolean load() {
		Connection conn = null;
		Statement stmt = null;
		boolean result = true;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("load data local inpath '/root/sampledata/fruits.csv'  into table fruits2");
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			} catch (Exception e) {
				e.printStackTrace();				
			} 
		}
		return result;
	}
	public boolean insert(FruitsVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("insert into fruits2 values (?,?,?,?)");
			pstmt.setString(1, vo.getYear());
			pstmt.setString(2, vo.getName());
			pstmt.setInt(3, vo.getQty());
			pstmt.setInt(4, vo.getPrice());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if( pstmt != null ) pstmt.close();
				if( conn != null ) conn.close();
			} catch (Exception e) {
				e.printStackTrace();				
			} 
		}
		return result;
	}
	public List<FruitsVO> select1() {
		List<FruitsVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from fruits2");
			FruitsVO vo = null;
			while(rs.next()) {
				vo = new FruitsVO();
				vo.setYear(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setQty(rs.getInt(3));
				vo.setPrice(rs.getInt(4));
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
	public List<FruitsVO> select2() {
		List<FruitsVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select year, sum(qty) from fruits2 group by year");
			FruitsVO vo = null;
			while(rs.next()) {
				vo = new FruitsVO();
				vo.setYear(rs.getString(1));
				vo.setQty(rs.getInt(2));
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
	public List<FruitsVO> select3() {
		List<FruitsVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select year, name, price from fruits2 order by price desc limit 5");
			FruitsVO vo = null;
			while(rs.next()) {
				vo = new FruitsVO();
				vo.setYear(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setPrice(rs.getInt(3));
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



package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	private SqlSession sqlSession;
	
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	private Connection getConnection() throws SQLException {

	    Connection conn = null;

        try {
    	    //1. JDBC Driver 로딩 
			Class.forName("org.mariadb.jdbc.Driver");

	        //2. 연결하기 
			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} 
        
	    return conn;
	}
	
	
	public int insert(GuestbookVo vo) {
		
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into guestbook(name, password, contents, reg_date) values(?, password(?), ?, now())");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		) {
			
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getPassword());
			pstmt1.setString(3, vo.getContents());
			result = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			
			rs.close();
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	    
		return result;
		
	}
	
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public int deleteByNoAndPassword(Long no, String password) {
		int result = 0;
		
		try (
			Connection conn = getConnection();	
			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no = ? and password = password(?)");
		){
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return result;		
	}

}

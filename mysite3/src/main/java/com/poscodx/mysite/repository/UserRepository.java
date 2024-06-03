package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
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

	public int insert(UserVo vo) {

		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into user values(null, ?, ?, password(?), ?, current_date())");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		) {
			
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getGender());
			result = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			
			rs.close();
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	    
		return result;
		
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		
		UserVo result = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select no, name from user where email = ? and password = password(?)");
		) {
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				
				result.setNo(no);
				result.setName(name);
			}
			
			rs.close();
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	    
		return result;
	}

	public UserVo findByNo(Long no) {
		
		UserVo result = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select no, name, email, gender from user where no = ?");
		) {
			
			pstmt.setLong(1, no);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long userNo = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				result = new UserVo();
				
				result.setNo(userNo);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			
			rs.close();
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	    
		return result;
	}

	public int update(UserVo vo) {
		
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("update user set name = ?, gender = ? where no = ?");
			PreparedStatement pstmt2 = conn.prepareStatement("update user set name = ?, password = password(?), gender = ? where no = ?");
		) {
			
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setLong(3, vo.getNo());
				result = pstmt1.executeUpdate();
			} else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getPassword());
				pstmt2.setString(3, vo.getGender());
				pstmt2.setLong(4, vo.getNo());
				result = pstmt2.executeUpdate();
			}
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	    
		return result;
		
	}
	
	

}

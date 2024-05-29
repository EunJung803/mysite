package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class BoardDao {
	
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

	public int insert(BoardVo vo) {
		
		int result = 0;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values(?, ?, ?, now(), ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			) {
				
				pstmt1.setString(1, vo.getTitle());
				pstmt1.setString(2, vo.getContents());
				pstmt1.setInt(3, vo.getHit());
				pstmt1.setInt(4, vo.getGroupNo());
				pstmt1.setInt(5, vo.getOrderNo());
				pstmt1.setInt(6, vo.getDepth());
				pstmt1.setLong(7, vo.getUserNo());
				
				result = pstmt1.executeUpdate();
				
				ResultSet rs = pstmt2.executeQuery();
				
				vo.setNo(rs.next() ? rs.getLong(1) : null);
				
				rs.close();
			     
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		
		return result;
		
	}

	public int findMaxGroupNo() {
		
		int result = 0;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select max(g_no) from board");
				ResultSet rs = pstmt.executeQuery();
			) {
				if(rs.next()) {
					result = rs.getInt(1);
				}
				
				rs.close();
			     
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		
		return result;
	}

	public List<BoardVo> findAll() {
		
		List<BoardVo> result = new ArrayList<>();
		
		try (
		    	Connection conn = getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(
		    					"select a.no, b.name, a.title, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.user_no "
			    				+ "from board a, user b "
			    				+ "where a.user_no = b.no "
			    				+ "order by g_no desc, o_no asc");
	    		ResultSet rs = pstmt.executeQuery();
		    ) {

		    	while(rs.next()) {
		    		Long no = rs.getLong(1);
		    		String name = rs.getString(2);
		    		String title = rs.getString(3);
		    		String regDate = rs.getString(4);
		    		Long userNo = rs.getLong(5);
		    		
		    		BoardVo vo = new BoardVo();
		    		vo.setNo(no);
		    		vo.setUserName(name);
		    		vo.setTitle(title);
		    		vo.setRegDate(regDate);
		    		vo.setUserNo(userNo);
		    		
		    		result.add(vo);
		    	}
			     
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		
		return result;
	}
	
	public BoardVo findByNo(String boardNo) {
		
		BoardVo result = null;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
								"select a.no, b.name, a.title, a.contents, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s') "
								+ "from board a, user b "
								+ "where a.user_no = b.no "
								+ "and a.no = ?");	
			) {
				
				pstmt.setLong(1, Long.parseLong(boardNo));
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					Long no = rs.getLong(1);
					String userName = rs.getString(2);
					String title = rs.getString(3);
					String contents = rs.getString(4);
					String regDate = rs.getString(5);
					
					result = new BoardVo();
					
					result.setNo(no);
					result.setUserName(userName);
					result.setTitle(title);
					result.setContents(contents);
					result.setRegDate(regDate);
					result.setUserNo(Long.parseLong(boardNo));
				}
				
				rs.close();
			     
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		
		return result;
	}

	public int deleteByNo(Long no) {
		
		int result = 0;
		
		try (
			Connection conn = getConnection();	
			PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ?");
		){
			pstmt.setLong(1, no);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return result;
		
	}
	
	
}

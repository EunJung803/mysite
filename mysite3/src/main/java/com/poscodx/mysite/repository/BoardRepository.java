package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
//	private Connection getConnection() throws SQLException {
//
//	    Connection conn = null;
//
//        try {
//    	    //1. JDBC Driver 로딩 
//			Class.forName("org.mariadb.jdbc.Driver");
//
//	        //2. 연결하기 
//			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//			
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패: " + e);
//		} 
//        
//	    return conn;
//	}

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt1 = conn.prepareStatement("insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values(?, ?, ?, now(), ?, ?, ?, ?)");
//			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
//		) {
//			
//			pstmt1.setString(1, vo.getTitle());
//			pstmt1.setString(2, vo.getContents());
//			pstmt1.setInt(3, vo.getHit());
//			pstmt1.setInt(4, vo.getGroupNo());
//			pstmt1.setInt(5, vo.getOrderNo());
//			pstmt1.setInt(6, vo.getDepth());
//			pstmt1.setLong(7, vo.getUserNo());
//			
//			result = pstmt1.executeUpdate();
//			
//			ResultSet rs = pstmt2.executeQuery();
//			
//			vo.setNo(rs.next() ? rs.getLong(1) : null);
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	
//		return result;
		
	}

	public int findMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxGroupNo");
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("select max(g_no) from board");
//			ResultSet rs = pstmt.executeQuery();
//		) {
//			if(rs.next()) {
//				result = rs.getInt(1);
//			}
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	
//		return result;
	}
	
	public BoardVo findByNo(long boardNo) {
		return sqlSession.selectOne("board.findByNo", boardNo);
		
//		BoardVo result = null;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(
//							"select a.no, b.name, a.title, a.contents, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.user_no, a.g_no, a.o_no, a.depth "
//							+ "from board a, user b "
//							+ "where a.user_no = b.no "
//							+ "and a.no = ?");	
//		) {
//			
//			pstmt.setLong(1, boardNo);
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				Long no = rs.getLong(1);
//				String userName = rs.getString(2);
//				String title = rs.getString(3);
//				String contents = rs.getString(4);
//				String regDate = rs.getString(5);
//				Long userNo = rs.getLong(6);
//				int groupNo = rs.getInt(7);
//	    		int orderNo = rs.getInt(8);
//	    		int depth = rs.getInt(9);
//				
//				result = new BoardVo();
//				
//				result.setNo(no);
//				result.setUserName(userName);
//				result.setTitle(title);
//				result.setContents(contents);
//				result.setRegDate(regDate);
//				result.setUserNo(userNo);
//				result.setGroupNo(groupNo);
//				result.setOrderNo(orderNo);
//				result.setDepth(depth);
//			}
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//		
//		return result;
	}

	public int deleteByNoandUserNo(Long boardNo, Long userNo) {
		return sqlSession.delete("board.deleteByNoandUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
//		
//		int result = 0;
//		
//		try (
//				Connection conn = getConnection();	
//				PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ? and user_no = ?");
//			){
//				pstmt.setLong(1, boardNo);
//				pstmt.setLong(2, userNo);
//				result = pstmt.executeUpdate();
//				
//			} catch (SQLException e) {
//				System.out.println("Error:" + e);
//			}
//			
//		return result;
//		
	}

	public int update(BoardVo vo, Long userNo) {
		return sqlSession.update("board.update", Map.of("vo", vo, "userNo", userNo));
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ? where no = ? and user_no = ?");
//		) {
//			
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getNo());
//			pstmt.setLong(4, userNo);
//			
//			result = pstmt.executeUpdate();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	    
//		return result;
		
	}

	public int getTotalCount() {
		return sqlSession.selectOne("board.getTotalCount");
		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("select count(no) from board");
//			ResultSet rs = pstmt.executeQuery();
//		) {
//			
//			if (rs.next()) {
//                return rs.getInt(1);
//            }
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//		    
//		return result;
	}

	public List<BoardVo> findBoards(int limitNum) {
		return sqlSession.selectList("board.findBoards", limitNum);
		
//		List<BoardVo> result = new ArrayList<>();
//		
//		try (
//	    	Connection conn = getConnection();
//    		PreparedStatement pstmt = conn.prepareStatement(
//	    					"select a.no, b.name, a.title, date_format(a.reg_date, '%Y/%m/%d %h:%i:%s'), a.user_no, a.hit, a.g_no, a.o_no, a.depth "
//		    				+ "from board a, user b "
//		    				+ "where a.user_no = b.no "
//		    				+ "order by g_no desc, o_no asc "
//		    				+ "limit ?, 5");
//	    ) {
//			
//			pstmt.setInt(1, limitNum);
//
//			ResultSet rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				Long no = rs.getLong(1);
//	    		String name = rs.getString(2);
//	    		String title = rs.getString(3);
//	    		String regDate = rs.getString(4);
//	    		Long userNo = rs.getLong(5);
//	    		int hit = rs.getInt(6);
//	    		int groupNo = rs.getInt(7);
//	    		int orderNo = rs.getInt(8);
//	    		int depth = rs.getInt(9);
//				
//				BoardVo vo = new BoardVo();
//				
//	    		vo.setNo(no);
//	    		vo.setUserName(name);
//	    		vo.setTitle(title);
//	    		vo.setRegDate(regDate);
//	    		vo.setUserNo(userNo);
//	    		vo.setHit(hit);
//	    		vo.setGroupNo(groupNo);
//	    		vo.setOrderNo(orderNo);
//	    		vo.setDepth(depth);
//	    		
//	    		result.add(vo);
//			}
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	
//		return result;
		
	}

	public int updateOrderNo(BoardVo vo) {
		return sqlSession.update("board.updateOrderNo", vo);
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(
//							"update board set o_no = o_no + 1 "
//							+ "where g_no = ? "
//							+ "and o_no > ?");
//		) {
//			
//			pstmt.setInt(1, vo.getGroupNo());
//			pstmt.setInt(2, vo.getOrderNo());
//			
//			result = pstmt.executeUpdate();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	    
//		return result;
//		
	}

	public int updateBoardHit(Long no) {
		return sqlSession.update("board.updateBoardHit", no);
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(
//							"update board set hit = hit + 1 "
//							+ "where no = ?");
//		) {
//			
//			pstmt.setLong(1, no);
//			
//			result = pstmt.executeUpdate();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	    
//		return result;
//		
	}

	public BoardVo findByNoandUserNo(Long boardNo, Long userNo) {
		return sqlSession.selectOne("board.findByNoandUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
//		
//		BoardVo result = null;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(
//							"select a.no, b.name, a.title, a.contents, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.user_no, a.g_no, a.o_no, a.depth "
//							+ "from board a, user b "
//							+ "where a.user_no = b.no "
//							+ "and a.no = ? "
//							+ "and b.no = ?");	
//		) {
//			
//			pstmt.setLong(1, boardNo);
//			pstmt.setLong(2, userNo);
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				String userName = rs.getString(2);
//				String title = rs.getString(3);
//				String contents = rs.getString(4);
//				String regDate = rs.getString(5);
//				int groupNo = rs.getInt(7);
//	    		int orderNo = rs.getInt(8);
//	    		int depth = rs.getInt(9);
//				
//				result = new BoardVo();
//				
//				result.setNo(boardNo);
//				result.setUserName(userName);
//				result.setTitle(title);
//				result.setContents(contents);
//				result.setRegDate(regDate);
//				result.setUserNo(userNo);
//				result.setGroupNo(groupNo);
//				result.setOrderNo(orderNo);
//				result.setDepth(depth);
//			}
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//		
//		return result;
	}
	
}

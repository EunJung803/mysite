package com.poscodx.mysite.repository;

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

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public int findMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxGroupNo");
	}
	
	public BoardVo findByNo(long boardNo) {
		return sqlSession.selectOne("board.findByNo", boardNo);
	}

	public int deleteByNoandUserNo(Long boardNo, Long userNo) {
		return sqlSession.delete("board.deleteByNoandUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
	}

	public int update(BoardVo vo, Long userNo) {
		return sqlSession.update("board.update", Map.of("vo", vo, "userNo", userNo));
	}

	public int getTotalCount() {
		return sqlSession.selectOne("board.getTotalCount");
	}

	public List<BoardVo> findBoards(int limitNum) {
		return sqlSession.selectList("board.findBoards", limitNum);
	}

	public int updateOrderNo(BoardVo vo) {
		return sqlSession.update("board.updateOrderNo", vo);
	}

	public int updateBoardHit(Long no) {
		return sqlSession.update("board.updateBoardHit", no);
	}

	public BoardVo findByNoandUserNo(Long boardNo, Long userNo) {
		return sqlSession.selectOne("board.findByNoandUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
	}
	
}

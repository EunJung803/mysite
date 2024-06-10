package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	private SqlSession sqlSession;
	
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
//		StopWatch sw = new StopWatch();
//		sw.start();
//		
//		List<GuestbookVo> list = sqlSession.selectList("guestbook.findAll");
//		
//		sw.stop();
//		long totalTime = sw.getTotalTimeMillis();
//		
//		System.out.println(totalTime);
//		
//		return list;
	}
	
	public int deleteByNoAndPassword(Long no, String password) {
		return sqlSession.delete("guestbook.deleteByNoAndPassword", Map.of("no", no, "password", password));	
	}

}

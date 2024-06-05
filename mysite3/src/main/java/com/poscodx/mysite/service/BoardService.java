package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void addContents(BoardVo boardVo, Long userNo) {
		String title = boardVo.getTitle();
		String contents = boardVo.getContents();
		
		Long no = boardVo.getNo();
		
		BoardVo vo = new BoardVo();
		
		if (no == null) {				// 새 글을 쓰는 경우
			int maxGroupNo = boardRepository.findMaxGroupNo();
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(maxGroupNo + 1);
			vo.setOrderNo(1);
			vo.setDepth(0);
			vo.setUserNo(userNo);
		} else {										// 답글을 다는 경우
			BoardVo parentVo = boardRepository.findByNo(no);
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(parentVo.getGroupNo());
			vo.setOrderNo(parentVo.getOrderNo() + 1);
			vo.setDepth(parentVo.getDepth() + 1);
			vo.setUserNo(userNo);
			
			boardRepository.updateOrderNo(parentVo);
		}
		
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no, Cookie[] cookies, HttpServletResponse response) {
		boolean visitFlag = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("hit".equals(cookie.getName())) {
                    if (cookie.getValue().contains("[" + no + "]")) {
                        visitFlag = true;
                        break;
                    } else {
                        cookie.setValue(cookie.getValue() + "[" + no + "]");
                        response.addCookie(cookie);
                        boardRepository.updateBoardHit(no);
                        visitFlag = true;
                    }
                }
            }
        }

        if (!visitFlag) {
            boardRepository.updateBoardHit(no);
            Cookie cookie = new Cookie("hit", "[" + no + "]");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        }
		
		return boardRepository.findByNo(no);
	}
	
	public BoardVo getContents(Long boardNo, Long userNo) {	// 로그인한 사용자들을 위해 글 수정하기를 가져올 때 필요함
		return boardRepository.findByNoandUserNo(boardNo, userNo);
	}
	
	public void updateContents(BoardVo vo, Long userNo) {
		boardRepository.update(vo, userNo);
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
		boardRepository.deleteByNo(boardNo, userNo);
	}
	
	public Map<String, Object> getContentsList(int currPageNum) {
		List<BoardVo> list = null;
		Map<String, Object> map = new HashMap<>();;
		
		int boardsPerPage = 5;										// 한 페이지 게시물 수
		int totalBoardCount = boardRepository.getTotalCount();		// 현재 총 게시물 개수
		int pagesPerBlock = 5;										// 한 블럭에 포함되는 페이지 번호의 개수
		
		int totalPages = (int) Math.ceil((double) totalBoardCount / boardsPerPage);		// 게시물 게수에 따른 총 페이지 수
		
		int limitNum = (currPageNum - 1) * boardsPerPage;			// sql문 limit 시작 기준점
		
		/* 
		 * 블럭 1 == 1, 2, 3, 4, 5 페이지 
		 * 블럭 2 == 6, 7, 8, 9, 10 페이지
		 */
		int currBlockNum = (int) Math.ceil((double) currPageNum / pagesPerBlock);		// 현재 화면의 블럭 번호

        int blockStartPageNo = (currBlockNum - 1) * pagesPerBlock + 1;		// 현재 블럭에서 시작하는 페이지 번호
        int blockEndPageNo = blockStartPageNo + pagesPerBlock - 1;			// 현재 블럭에서 끝나는 페이지 번호
        if (blockEndPageNo > totalPages) {
        	blockEndPageNo = totalPages;
        }
        
        list = boardRepository.findBoards(limitNum);
		map.put("list", list);
		
		map.put("totalPages", totalPages);
		map.put("currPageNum", currPageNum);
		map.put("totalBoardCount", totalBoardCount);
		
		map.put("pagesPerBlock", pagesPerBlock);
		map.put("currBlockNum", currBlockNum);
		map.put("blockStartPageNo", blockStartPageNo);
		map.put("blockEndPageNo", blockEndPageNo);
		
		return map;
	}
	
}

package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

public class ListAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currPageNum = 1;	// 시작 페이지 (현재 페이지 번호)
		
		if (request.getParameter("p") != null) {
			currPageNum = Integer.parseInt(request.getParameter("p"));
		}
		
		int boardsPerPage = 5;										// 한 페이지 게시물 수
		int totalBoardCount = new BoardDao().getTotalCount();		// 현재 총 게시물 개수
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
		
		request.setAttribute("list", new BoardDao().findBoards(limitNum));
		request.setAttribute("currPageNum", currPageNum);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalBoardCount", totalBoardCount);
		
		request.setAttribute("pagesPerBlock", pagesPerBlock);
		request.setAttribute("currBlockNum", currBlockNum);
		request.setAttribute("blockStartPageNo", blockStartPageNo);
		request.setAttribute("blockEndPageNo", blockEndPageNo);
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}
	
}

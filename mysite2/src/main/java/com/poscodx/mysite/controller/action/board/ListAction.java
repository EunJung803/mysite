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
		
//		request.setAttribute("list", new BoardDao().findAll());
		
		int currPageNum = 1;	// 시작 페이지 (현재 페이지 번호)
		
		if (request.getParameter("p") != null) {
			currPageNum = Integer.parseInt(request.getParameter("p"));
		}
		
		int boardsPerPage = 5;		// 한 페이지 게시물 수
		int totalBoardCount = new BoardDao().getTotalCount();		// 현재 총 게시물 개수
		
		int totalPages = (int) Math.ceil((double) totalBoardCount / boardsPerPage);		// 나타나게 될 총 페이지 수

		int limitNum = (currPageNum - 1) * boardsPerPage;
		
		request.setAttribute("list", new BoardDao().findBoards(limitNum));
		request.setAttribute("currPageNum", currPageNum);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalBoardCount", totalBoardCount);

		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}
}

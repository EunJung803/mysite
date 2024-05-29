package com.poscodx.mysite.action.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class ListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("list", new BoardDao().findAll());
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}
}

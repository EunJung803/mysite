package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		//////
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		String no = request.getParameter("no");
//		System.out.println(no);
		
		BoardVo vo = new BoardVo();
		BoardDao dao = new BoardDao();
		
		if (no == null || no.isEmpty()) {				// 새 글을 쓰는 경우
			int maxGroupNo = dao.findMaxGroupNo();
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(maxGroupNo + 1);
			vo.setOrderNo(1);
			vo.setDepth(0);
			vo.setUserNo(authUser.getNo());
		} else {										// 답글을 다는 경우
			BoardVo parentVo = dao.findByNo(no);
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(parentVo.getGroupNo());
			vo.setOrderNo(parentVo.getOrderNo() + 1);
			vo.setDepth(parentVo.getDepth() + 1);
			vo.setUserNo(authUser.getNo());
			
			dao.updateOrderNo(parentVo);
		}
		
		
		dao.insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board");
		
	}
}
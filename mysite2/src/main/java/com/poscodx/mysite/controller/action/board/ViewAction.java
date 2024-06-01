package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

public class ViewAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		
		boolean visitFlag = false;		// 기존 쿠키로 방문했는지 확인하기 위한 flag
		
		// 쿠키 읽기
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				
				if("hit".equals(cookie.getName())) {		// 쿠키 중에 hit 있는지 확인
					
					// 해당 페이지를 접속한 적이 있는지 확인
					if(cookie.getValue().contains("/" + no + "/")) {
						visitFlag = true;
						break;
					} else {
						cookie.setValue(cookie.getValue() + "/" + no + "/");		// 쿠키에 해당 게시물 번호가 없으면 추가
						response.addCookie(cookie);
						new BoardDao().updateBoardHit(no);		// 조회수 += 1
						visitFlag = true;
					}
				}
				
			}
		}
		
		// 쿠키 쓰기
		if(!visitFlag) {		// 기존 쿠키로 방문한 적 없으면 -> 쿠키 생성
			new BoardDao().updateBoardHit(no);		// 조회수 += 1
			
			Cookie cookie = new Cookie("hit", "/" + no + "/");
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
		}
		
		request.setAttribute("vo", new BoardDao().findByNo(no));
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	}
}

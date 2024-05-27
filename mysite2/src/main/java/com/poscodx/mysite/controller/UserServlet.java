package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.JoinAction;
import com.poscodx.mysite.controller.action.user.JoinFormAction;
import com.poscodx.mysite.controller.action.user.JoinSuccess;
import com.poscodx.mysite.controller.action.user.LoginFormAction;

public class UserServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"joinform", new JoinFormAction(),
			"join", new JoinAction(),
			"joinsuccess", new JoinSuccess(),
			"loginform", new LoginFormAction()
	);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new MainAction());	// key가 없는 경우에는 Default값
		
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		
//		String action = request.getParameter("a");
//		
//		if("joinform".equals(action)) {
//			request
//				.getRequestDispatcher("WEB-INF/views/user/joinform.jsp")
//				.forward(request, response);
//		}
//		else if("join".equals(action)) {
//			String name = request.getParameter("name");
//			String email = request.getParameter("email");
//			String password = request.getParameter("password");
//			String gender = request.getParameter("gender");
//			
//			UserVo vo = new UserVo();
//			
//			vo.setName(name);
//			vo.setEmail(email);
//			vo.setPassword(password);
//			vo.setGender(gender);
//			
//			System.out.println(vo);
//			
//			new UserDao().insert(vo);
//			
//			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
//			
//		}
//		else if("joinsuccess".equals(action)) {
//			request
//			.getRequestDispatcher("WEB-INF/views/user/joinsuccess.jsp")
//			.forward(request, response);
//		}
//		else if("".equals(action)) {
//			
//		}
//		else if("".equals(action)) {
//			
//		}
//		else if("".equals(action)) {
//			
//		}
//		else {
//			// main으로 돌려보내기
//			response.sendRedirect(request.getContextPath());
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

}

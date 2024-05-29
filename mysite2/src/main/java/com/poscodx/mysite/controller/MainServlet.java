package com.poscodx.mysite.controller;

import javax.servlet.ServletException;

import com.poscodx.mysite.controller.action.main.MainAction;

public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		String config = getServletConfig().getInitParameter("config");
		System.out.println("MainController.init() called:" + config);
		
		super.init();		// 서비스를 호출
	}

	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}

}


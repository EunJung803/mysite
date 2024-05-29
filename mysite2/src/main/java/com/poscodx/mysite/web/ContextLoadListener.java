package com.poscodx.mysite.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextLoadListener implements ServletContextListener {
	/**
     * 로딩이 될 때 실행됨 
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	ServletContext sc = sce.getServletContext();			// 이벤트를 일으킨 당사자 가져오기
    	String contextConfigLoaction = sc.getInitParameter("contextConfigLocation");			// 파라미터 가져오기 -> param의 이름으로
		System.out.println("Application[Mysite2] starts... " + contextConfigLoaction);
    }
	
	/**
     * 톰캣이 내려갈 때 실행됨
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    }
    
}

package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model) {
		Map map = boardService.getContentsList(1);
		model.addAllAttributes(map);			// 얘가 이 안에서 map을 풀어서 안에껄 전부다 addAttribute 해줌 !
		
		return "board/list";
	}
	
	@RequestMapping("/{currPageNum}")
	public String index(@PathVariable("currPageNum") int currPageNum, Model model) {
		Map map = boardService.getContentsList(currPageNum);
		model.addAllAttributes(map);	
		
		return "board/list";
	}
	
//	@RequestMapping("/view/{no}")
//	public String view(@PathVariable("no") Long no, Model model) {
//		model.addAttribute("vo", boardService.getContents(no));
//		
//		return "board/view";
//	}
	
	// 쿠키 조회수 추가
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model, HttpServletResponse response, 
						@CookieValue(value = "hit", defaultValue = "") String hitCookieValue) {
		
		Cookie[] cookies = new Cookie[]{new Cookie("hit", hitCookieValue)};
		model.addAttribute("vo", boardService.getContents(no, cookies, response));
		
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session, Model model) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}

		boardService.addContents(boardVo, authUser.getNo());
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@PathVariable("no") Long no, HttpSession session, Model model) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		model.addAttribute("vo", boardService.getContents(no, authUser.getNo()));
		
		return "board/update";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@PathVariable("no") Long no, HttpSession session, BoardVo boardVo) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}

		boardService.updateContents(boardVo, authUser.getNo());
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		boardService.deleteContents(no, authUser.getNo());
		
		return "redirect:/board";
	}
	
}

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
	
	/* read */
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model, HttpServletResponse response, 
						@CookieValue(value = "hit", defaultValue = "") String hitCookieValue) {
		
		// 쿠키 조회수
		Cookie[] cookies = new Cookie[]{new Cookie("hit", hitCookieValue)};
		model.addAttribute("vo", boardService.getContents(no, cookies, response));
		
		return "board/view";
	}
	
	/* 새글 */
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		
		return "redirect:/board";
	}
	
	/* 답글 */
	@Auth
	@RequestMapping(value="/write/{no}", method=RequestMethod.GET)
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		model.addAttribute("vo", boardService.getContents(no, authUser.getNo()));
		
		return "board/reply";
	}
	
	@Auth
	@RequestMapping(value="/write/{no}", method=RequestMethod.POST)
	public String reply(HttpSession session, @PathVariable("no") Long no, BoardVo boardVo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		boardVo.setUserNo(authUser.getNo());
		boardService.addReply(boardVo, no, authUser.getNo());
		
		return "redirect:/board";
	}
	
	/* 수정 */
//	@Auth
//	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
//	public String update(@PathVariable("no") Long no, HttpSession session, Model model) {
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		
//		model.addAttribute("vo", boardService.getContents(no, authUser.getNo()));
//		
//		return "board/update";
//	}
	
	// argument resolver 로 횡단관심 제거하기
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@PathVariable("no") Long no, @AuthUser UserVo authUser, Model model) {
		model.addAttribute("vo", boardService.getContents(no, authUser.getNo()));
		
		return "board/update";
	}
	
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@PathVariable("no") Long no, HttpSession session, BoardVo boardVo, RedirectAttributes redirectAttributes) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		boardService.updateContents(boardVo, authUser.getNo());
		redirectAttributes.addFlashAttribute("message", "수정이 완료되었습니다.");
		
		return "redirect:/board/view/" + boardVo.getNo();
	}
	
	/* 삭제 */
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		boardService.deleteContents(no, authUser.getNo());
		
		return "redirect:/board";
	}
	
}

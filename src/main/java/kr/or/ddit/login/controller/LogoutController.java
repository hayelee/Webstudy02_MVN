package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class LogoutController {
	// a태그를 사용했으니까 doGet(아무리 용을 써도 post 사용 못해 form을 사용해야 post 사용 가능)
	// handler adapter를 통해 필요한 거 받아오면 돼~!
	@RequestMapping(value="/login/logout.do", method=RequestMethod.POST)
	public String process(HttpSession session) {
		// 세션속성 지워줘야해~~
//		session.removeAttribute("authMember");
		session.invalidate();
		
		return "redirect:/";
	}
} 

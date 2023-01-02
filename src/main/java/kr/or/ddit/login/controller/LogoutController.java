package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.AbstractController;

public class LogoutController implements AbstractController {
	// a태그를 사용했으니까 doGet(아무리 용을 써도 post 사용 못해 form을 사용해야 post 사용 가능)
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// 세션속성 지워줘야해~~
//		session.removeAttribute("authMember");
		session.invalidate();
		
		return "redirect:/";
	}
}

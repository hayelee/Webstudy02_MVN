package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login/logout.do")
public class LogoutControllerServlet extends HttpServlet{
	// a태그를 사용했으니까 doGet(아무리 용을 써도 post 사용 못해 form을 사용해야 post 사용 가능)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// 세션속성 지워줘야해~~
//		session.removeAttribute("authMember");
		session.invalidate();
		
		String viewName = "redirect:/";
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			// 디스패치로 포워딩
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
}

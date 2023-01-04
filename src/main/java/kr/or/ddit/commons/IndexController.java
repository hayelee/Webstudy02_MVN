package kr.or.ddit.commons;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index.do")
	public String process(HttpServletRequest req) {
		req.setAttribute("contentPage", "/WEB-INF/views/index.jsp");
		return "layout";
	}
}

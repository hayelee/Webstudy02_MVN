package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.commons.IndexController;
import kr.or.ddit.login.controller.LoginProcessController;
import kr.or.ddit.login.controller.LogoutController;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.member.controller.MemberListController;
import kr.or.ddit.member.controller.MemberViewController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.mvc.view.ViewResolver;
import kr.or.ddit.prod.controller.ProdListController;

public class DispatcherServlet extends HttpServlet{
	private ViewResolver viewResolver;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 정문에 웨이터 세우는거~! 정문의 웨이터가 실질적인 주문을 받지 않기 때문에 do계열의 메소드 필요 없어요~
		req.setCharacterEncoding("UTF-8");
		
//		String requestURI = req.getRequestURI(); //contextPath가 포함되어 있다~
//		requestURI = requestURI.substring(req.getContextPath().length());
		String requestURI = req.getServletPath();
		
		AbstractController controller = null;
		if("/member/memberList.do".equals(requestURI)) {
			controller = new MemberListController();
		}else if("/prod/prodList.do".equals(requestURI)) {
			controller = new ProdListController();
		}else if("/member/memberView.do".equals(requestURI)) {
			controller = new MemberViewController();
		}else if("/index.do".equals(requestURI)) {
			controller = new IndexController();
		}else if("/member/memberInsert.do".equals(requestURI)) {
			controller = new MemberInsertController();
		}else if("/login/loginProcess.do".equals(requestURI)) {
			controller = new LoginProcessController();
		}else if("/login/logout.do".equals(requestURI)) {
			controller = new LogoutController();
		}
		
		if(controller==null) {
			// 우리가 처리할 수 없는 요청입니다~! 상태코드 404
			resp.sendError(404, requestURI+"는 처리할 수 없는 자원임(Not found).");
			return;
		}
		
		String viewName = controller.process(req, resp);
		if(viewName==null) {
			// 개발자가 잘못만들어 준 거니까 500~
			if(!resp.isCommitted())
				resp.sendError(500, "논리적인 뷰 네임은 null일 수 없음!");
		}else {
			viewResolver.resolveView(viewName, req, resp);
		}
	}
}

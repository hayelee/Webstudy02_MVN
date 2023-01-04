package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerAdapter;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.mvc.view.ViewResolver;

public class DispatcherServlet extends HttpServlet{
	// 실제 작업은 얘네가 다해!
	private ViewResolver viewResolver;
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
		handlerMapping = new RequestMappingHandlerMapping("kr.or.ddit");
		handlerAdapter = new RequestMappingHandlerAdapter();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 정문에 웨이터 세우는거~! 정문의 웨이터가 실질적인 주문을 받지 않기 때문에 do계열의 메소드 필요 없어요~
		req.setCharacterEncoding("UTF-8");
		
//		String requestURI = req.getRequestURI(); //contextPath가 포함되어 있다~
//		requestURI = requestURI.substring(req.getContextPath().length());
		String requestURI = req.getServletPath();
		
		RequestMappingInfo mappingInfo = handlerMapping.findCommandHandler(req); // 다 가지고 있어!
		
		// 이만큼을HandlerMapping이 가져갔다! 책임을 쪼갰어요~
//		AbstractController controller = null;
//		else if("/prod/prodList.do".equals(requestURI)) {
//			controller = new ProdListController();
//		}else if("/member/memberView.do".equals(requestURI)) {
//			controller = new MemberViewController();
//		}else if("/index.do".equals(requestURI)) {
//			controller = new IndexController();
//		}else if("/login/loginProcess.do".equals(requestURI)) {
//			controller = new LoginProcessController();
//		}else if("/login/logout.do".equals(requestURI)) {
//			controller = new LogoutController();
//		}else if("/prod/prodInsert.do".equals(requestURI)) {
//			controller = new ProdInsertController();
//		}
		
		if(mappingInfo==null) {
			// 우리가 처리할 수 없는 요청입니다~! 상태코드 404
			resp.sendError(404, requestURI+"는 처리할 수 없는 자원임(Not found).");
			return;
		}
		
//		String viewName = controller.process(req, resp);
		String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp);
		
		if(viewName==null) {
			// 개발자가 잘못만들어 준 거니까 500~
			if(!resp.isCommitted())
				resp.sendError(500, "논리적인 뷰 네임은 null일 수 없음!");
		}else {
			viewResolver.resolveView(viewName, req, resp);
		}
	}
}

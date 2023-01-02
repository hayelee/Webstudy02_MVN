package kr.or.ddit.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InternalResourceViewResolver implements ViewResolver {
	private String prefix;
	private String suffix;
	
	public InternalResourceViewResolver() {
		this("", "");
	}

	public InternalResourceViewResolver(String prefix, String suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public void resolveView(String viewName, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		5. 컨트롤러에서 뷰로 이동할때 이 코드 이용하자(약속~!)
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else if(viewName.startsWith("forward:")) {
			viewName = viewName.substring("forward:".length());
			req.getRequestDispatcher(viewName).forward(req, resp);
		}else {
			// 디스패치로 포워딩
			req.getRequestDispatcher(prefix + viewName + suffix).forward(req, resp);
		}
	}
}

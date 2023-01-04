package kr.or.ddit.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 보호자원에 대한 요청인 경우, 신원 확인(session authMember 속성)을 한 사용자인지 판단
 *
 */
@Slf4j
public class AuthenticationFilter implements Filter {

	private Map<String, String[]> securedResources;
	public static final String SECUREDNAME = "securedResources";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		securedResources = new LinkedHashMap<>();
		filterConfig.getServletContext().setAttribute(SECUREDNAME, securedResources);
		String filePath = filterConfig.getInitParameter("filePath");
		try(
			InputStream is = this.getClass().getResourceAsStream(filePath);
		){
			Properties props = new Properties();
			props.load(is);
//			1. map안에 들어갈 엔트리 생성
			props.keySet().stream()
					.map(Object::toString) //stream의 값 하나하나를 변환할때 사용
//					.collect(Collectors.toList())
					.forEach(key->{
						String value = props.getProperty(key);
						securedResources.put(key, value.split(","));
						log.info("보호 자원[{} : {}]", key, securedResources.get(key));
					});
			
		}catch (IOException e) {
			throw new ServletException(e); // wrapper
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String uri = req.getServletPath();
		
		boolean pass = true;
//		보호자원인지 여부 판단
		if(securedResources.containsKey(uri)) {
//			신원확인
			Object authMember = req.getSession().getAttribute("authMember");
			if(authMember == null) {
//				이 케이스는 통과 되면 안됨(pass만 결정하자)
				pass = false;
			}
//			else면 통과시키면 됨 할 일 없어~
		}
//			보호자원이 아니면 통과해야해 할일 없어~
			
		if(pass) {
//			pass가 true일때(통과시켜)
			chain.doFilter(request, response);
		}else {
			// 보호자원 요청했는데 신원확인(session에 authMember 없는 경우)을 거치지 않은 경우
			// loginform이동, redirect
			String viewName =  req.getContextPath() + "/login/loginForm.jsp";
			resp.sendRedirect(viewName);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}

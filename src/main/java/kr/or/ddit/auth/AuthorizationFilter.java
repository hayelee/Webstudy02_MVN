package kr.or.ddit.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

/**
 * 보호 자원에 대한 요청인 경우,
 * 자원에 설정된 역할 정보와 사용자에게 부여된 역할 정보가 일치할 때 접근 허용.
 *
 */
public class AuthorizationFilter implements Filter {

	private ServletContext applecation;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		applecation = filterConfig.getServletContext();
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Map<String, String[]> securedResources = (Map)applecation.getAttribute(AuthenticationFilter.SECUREDNAME);
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
//		HttpSession session = req.getSession();
		
		boolean pass = true;
		
		String uri = req.getServletPath();
		
		if(securedResources.containsKey(uri)) {
			String[] resRoles = securedResources.get(uri);
//			MemberVO authMember = (MemberVO) session.getAttribute("authMember"); // 정상적인 인증 시스템이 아니야~ 톰캣을 속이는중~
			MemberVOWrapper principal = (MemberVOWrapper) req.getUserPrincipal();
			MemberVO authMember = principal.getRealMember();
			String memRole = authMember.getMemRole();
			pass = Arrays.stream(resRoles)
							.anyMatch(ele->ele.equals(memRole)); // resRoles가 가지고 있는 엘리먼트 하나씩 비교
		}
		
		if(pass) {
			chain.doFilter(request, response);
		}else {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, "권한 없는 자원에 대한 요청");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

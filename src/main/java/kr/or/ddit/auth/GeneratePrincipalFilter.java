package kr.or.ddit.auth;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

public class GeneratePrincipalFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		if(authMember!=null) {
//			MemberVOWrapper wrapper = new MemberVOWrapper(authMember); //principal생성! 가능하면 여기서 지역변수 사용하지 않으려고해 그래서 없앤다!
			//request에 넣어야 front, handler, controller..에 다 영향
			HttpServletRequest modifiedReq = new HttpServletRequestWrapper(req) { //익명객체 생성(HttpServletRequestWrapper얘를 상속)
				@Override
				public Principal getUserPrincipal() { //getUserPrincipal얘가 null이라서 여기까지 온 거야~ 길을 잃으면 안돼~
					HttpServletRequest adaptee = (HttpServletRequest) getRequest(); // adapte 에서 adaptee 꺼냈다!
					MemberVO realMember = (MemberVO) adaptee.getSession().getAttribute("authMember"); // 위의 getSession과 같은 객체야!
					return new MemberVOWrapper(realMember);
				}
			};
			chain.doFilter(modifiedReq, response); // 중간에 필터링을 한 것
		}else {
			chain.doFilter(request, response); // 필터링을 전혀 안한 것
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

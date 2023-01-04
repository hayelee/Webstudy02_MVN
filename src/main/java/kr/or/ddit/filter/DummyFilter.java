package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyFilter implements Filter{

	//필터도 생명주기가 있다! container에 의해 관리가 된다~
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("{} 초기화", this.getClass().getName());
	}

	// 필터링 역할을 담당하는 메소드
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 요청 필터링
		log.info("=====================요청 필터링======================");
		chain.doFilter(request, response); //------------------ 다음 필터에게 제어권 이동
		// 응답 필터링
		log.info("=====================응답 필터링======================");
	}

	@Override
	public void destroy() {
		log.info("{} 초기화", this.getClass().getName());
	}

}

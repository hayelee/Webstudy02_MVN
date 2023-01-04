package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlindFilter implements Filter {

	private Map<String, String> blindMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		blindMap = new LinkedHashMap<>();
		blindMap.put("127.0.0.1", "나니까 숨겨버리자~(블라인드)");
		blindMap.put("0:0:0:0:0:0:0:1", "나니까 숨겨버리자~(블라인드)");
		blindMap.put("192.168.35.29", "나니까 숨겨버리자~(블라인드)");
		blindMap.put("192.168.35.47", "서연언니 숨겨버리자~(블라인드)");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("blind filter 동작시작");
		// 1. 클라이언트 ip address
		String ipAddress = request.getRemoteAddr();
		// 2. ip address로 블라인드 대상자 확인
		if(blindMap.containsKey(ipAddress)) {
			// 4. 블라인드 대상자라면 블라인드 타입을 알려주면서 메시지 띄워주기
			String reason = blindMap.get(ipAddress);
			String message = String.format("당신은 %s 사유로 블라인드 처리 됐써용..", reason);
			request.setAttribute("message", message);
			String viewName = "/WEB-INF/views/commons/messageView.jsp";
			request.getRequestDispatcher(viewName).forward(request, response);
		}else {
			// 3. 블라인드 대상자가 아니면 정상적 서비스
			chain.doFilter(request, response);
		}
		
		
		log.info("blind filter 동작종료");
	}

	@Override
	public void destroy() {
		log.info("{} 소멸", this.getClass().getName());
		
	}
}

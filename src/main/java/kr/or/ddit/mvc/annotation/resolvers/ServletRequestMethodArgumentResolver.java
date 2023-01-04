package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * (@link HttpServletRequest), (@link HttpSession) 타입의 핸들러 메소드 인자 해결.
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override // 내가 만들 수 있는 객체의 타입 결정
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType(); // handler메소드에 설정해 놓은 파라미터 중 하나
		boolean support = HttpServletRequest.class.equals(parameterType) //이제부터 넣어줘야 할 파라미터는 request라는 뜻!
							||
						  HttpSession.class.equals(parameterType); //request와 session은 내가 처리해야 할 파라미터라는 뜻!
		return support;
	}

	@Override // 필요한 객체 만들기? request와  session만 만들 수 있어!
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		Object argumentObject = null;
		if(HttpServletRequest.class.equals(parameterType)) {
			argumentObject = req;
		}else {
			//세션이 필요해요
			argumentObject = req.getSession();
		}
		return argumentObject;
	}

}

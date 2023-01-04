package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.HandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeMethodProcessor;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver.BadRequestException;
import kr.or.ddit.mvc.annotation.resolvers.ServletRequestMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletResponseMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	private List<HandlerMethodArgumentResolver> argumentResolvers;
	{
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletRequestMethodArgumentResolver());
		argumentResolvers.add(new ServletResponseMethodArgumentResolver());
		argumentResolvers.add(new RequestParamMethodArgumentResolver());
		argumentResolvers.add(new ModelAttributeMethodProcessor());
	}
	
	// 갖고있는 resolver가 여러개일때 선택하는 작업을 여기서 해요~
	private HandlerMethodArgumentResolver findArgumentResolver(Parameter param) {
		HandlerMethodArgumentResolver finded = null;
		for(HandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.supportsParameter(param)) { //true가 돌아오면 내가 처리할 수 있다는 뜻!
				finded = resolver;
				break;
			} 
		}
		return finded;
	}
	
	@Override
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object handlerObject = mappingInfo.getCommandHandler(); // controller의 객체!
		Method handlerMethod = mappingInfo.getHandlerMethod();
		int parameterCount = handlerMethod.getParameterCount();
		try {
			String viewName = null;
			if(parameterCount>0) {
				Parameter[] parameters = handlerMethod.getParameters();
				Object[] arguments = new Object[parameterCount];
				// 한개한개 파라미터를 만들어야해요(argument resolver를 사용)
				for(int i=0; i<parameterCount; i++) {
					Parameter param = parameters[i];
					HandlerMethodArgumentResolver findedReolver = findArgumentResolver(param);
					if(findedReolver==null) {
						// rewponse 상태코드 500
						throw new RuntimeException(String.format("%s 타입의 메소드 인자는 현재 처리 가능한 resolver가 없음.",param.getType()));
					}else {
						arguments[i] = findedReolver.resolveArgument(param, req, resp);
					}
				}
				viewName = (String) handlerMethod.invoke(handlerObject, arguments);
			}else {
				viewName = (String) handlerMethod.invoke(handlerObject);
			}
			return viewName;
			
		}catch(BadRequestException e) {
			log.error("handler adapter가 handler method argument resolver 사용중 문제 발생", e);
			resp.sendError(400, e.getMessage());
			return null;
		}catch (Exception e) {
			throw new ServletException(e);
		}
	}
}

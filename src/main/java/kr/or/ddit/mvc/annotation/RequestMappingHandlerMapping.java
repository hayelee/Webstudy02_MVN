package kr.or.ddit.mvc.annotation;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMappingHandlerMapping implements HandlerMapping {
	Map<RequestMappingCondition, RequestMappingInfo> handlerMap;
	
	public RequestMappingHandlerMapping(String...basePackages) { // 이 생성자 안에서 최종으로 만들어야 하는 건 위의 Map
		handlerMap = new LinkedHashMap<>();
		scanBasePackages(basePackages);
	}
	
	private void scanBasePackages(String[] basePackages) {
		//annotation tracing을 해봅시다~
		ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages)
			.forEach((handlerClass, controller)->{
				try {
					Object commandHandler = handlerClass.newInstance();
					ReflectionUtils.getMethodsWithAnnotationAtClass(
						handlerClass, RequestMapping.class, String.class
					).forEach((handlerMethod, requestMapping)->{
						RequestMappingCondition mappingCondition = 
								new RequestMappingCondition(requestMapping.value(), requestMapping.method()); // key 만들었어~
						RequestMappingInfo mappingInfo = 
								new RequestMappingInfo(mappingCondition, commandHandler, handlerMethod); // value 만들었어~
						handlerMap.put(mappingCondition, mappingInfo); // 저 위의 맵을 채워주기 위해 여기까지 온거야~ 
						log.info("수집된 핸들러 정보 : {}", mappingInfo);
					});
				}catch (Exception e) {
					log.error("핸들러 클래스 스캔 중 문제 발생", e);
				}
			});
	}

	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest request) { //response가 없어서 404를 표현할 수 없어!
		String url = request.getServletPath(); //DispatcherServlet에서 책임을 덜어내는중~!
		RequestMethod method = RequestMethod.valueOf(request.getMethod().toUpperCase());
		RequestMappingCondition mappingCondition = 
				new RequestMappingCondition(url, method);
		return handlerMap.get(mappingCondition);
	}
}

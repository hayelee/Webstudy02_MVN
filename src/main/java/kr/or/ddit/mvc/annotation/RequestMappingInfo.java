package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.ToString;

@Getter // 수정하지 않겠다~
@ToString
// equals가 필요 없는 이유?
public class RequestMappingInfo { // 
	private RequestMappingCondition mappingCondition;
	private Object commandHandler; 
	private Method handlerMethod;
	
	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handlerMethod = handlerMethod;
	} 
	
	
}

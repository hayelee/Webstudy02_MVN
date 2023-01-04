package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

/**
 * @ModelAttribute 어노테이션을 가진 command object(not 기본형) 인자 하나를 해결.
 * 	ex) @ModelAttribute MemberVO member (o);
 * 		@ModelAttribute int cp (x);
 *
 */
public class ModelAttributeMethodProcessor implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		ModelAttribute modelAttribute = parameter.getAnnotation(ModelAttribute.class);
		boolean support = modelAttribute != null
				&&
				!(
						parameterType.isPrimitive() //retrun으로 true가 돌아오면 기본형타입이여요
						||
						String.class.equals(parameterType) //기본형이거나 string타입이라면
						||
						(
								parameterType.isArray() 
								&& (
										parameterType.getComponentType().isPrimitive() 
										|| 
										parameterType.getComponentType().equals(String.class)
									)
						)
					);
		
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		ModelAttribute modelAttribute = parameter.getAnnotation(ModelAttribute.class);
		try {
//		MemberVO member = new MemberVO();
			Object commandObject = parameterType.newInstance(); //commandObject가 member
			
//		req.setAttribute("member", member); //이 이름을 어떻게 공유할 것인지는 우리가 결정하는 거야!
			String attrName = modelAttribute.value();
//			CoC (Convention over Configuration)
			if(StringUtils.isBlank(attrName)) {
				attrName = CaseUtils.toCamelCase(parameterType.getSimpleName(), false, ' '); //true=>첫문자 대문자
			}
			req.setAttribute(attrName, commandObject);

//		Map<String, String[]> parameterMap = req.getParameterMap();
//		
//		try {
//			BeanUtils.populate(member, parameterMap); // set어쩌고 10몇줄 안써도 됨~
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new ServletException(e);
//		}
			BeanUtils.populate(commandObject, req.getParameterMap());
			
		return commandObject;
		
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

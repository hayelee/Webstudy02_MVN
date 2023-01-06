package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class RequestPartMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		log.info("parameterType : {}",parameterType);
		RequestPart requestPart = parameter.getAnnotation(RequestPart.class);
		log.info("requestPart : {}",requestPart);
		boolean support = requestPart!=null //support의 첫번째 조건 : requestPart가 null이 아니어야함!
							&&
						  MultipartFile.class.equals(parameterType);
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		RequestPart requestPart = parameter.getAnnotation(RequestPart.class);
		
		if(req instanceof MultipartHttpServletRequest) {
			MultipartFile file = ((MultipartHttpServletRequest) req).getFile(requestPart.value());
			log.info("file : {}",file);
			if(requestPart.required() && (file==null || file.isEmpty())) { //꼭 있어야 하는데 없으면
				throw new RequestParamMethodArgumentResolver.BadRequestException("필수 파일 누락");
			}
			return file;
		}else {
			throw new RequestParamMethodArgumentResolver.BadRequestException("멀티 파트 요청이 아님");
		}
		
	}

}

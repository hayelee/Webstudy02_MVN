package kr.or.ddit.mvc.annotation.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.or.ddit.mvc.annotation.RequestMethod;

/**
 * single value(GET handler), multi value
 * command handler 내의 핸들러 메소드에 
 * 어떤 요청(주소, 메소드)에 대해 동작하는지를 표현.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	String value(); // 보기에는 메소드같지만 속성이다!(single value annotation)
	RequestMethod method() default RequestMethod.GET; // 기본값을 설정해줘야함! get메소드를 굳이 ???????할 필요 없다~
}

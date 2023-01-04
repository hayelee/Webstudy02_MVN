package kr.or.ddit.mvc.annotation;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * immutable 객체 형태로 값을 변경하지 않음
 *
 */
@Getter // setter 생성 안한 이유? 한번 객체를 생성한 이후에 수정하지 않을 것이라는 뜻~
@EqualsAndHashCode //key는 식별성이 필요해서 Equals가 필요해요~
public class RequestMappingCondition {
	private String url;
	private RequestMethod method;
	public RequestMappingCondition(String url, RequestMethod method) { // 기본생성자가 없어졌어요~
		super();
		this.url = url;
		this.method = method;
	}
	@Override
	public String toString() {
		return String.format("%s[%s]", url, method);
	}
}

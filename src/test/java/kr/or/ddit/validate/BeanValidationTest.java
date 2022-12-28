package kr.or.ddit.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

public class BeanValidationTest {
	private static final Logger log = LoggerFactory.getLogger(BeanValidationTest.class);
	private static Validator validator;
	
	@BeforeClass // 테스트케이스가 여러개 있어도 딱 한번만 사용됨! @Before은 여러번 실행됨
	public static void setUpBeforeClass() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		String messageBaseName = "kr.or.ddit.msgs.errorMessage";
		ValidatorFactory factory = Validation.byDefaultProvider()
							        .configure()
							        .messageInterpolator(
							                new ResourceBundleMessageInterpolator(
							                        new PlatformResourceBundleLocator( messageBaseName )
							                )
							        )
							        .buildValidatorFactory();
				validator = factory.getValidator();
	}
	@Test
	public void memberVOTest() {
		MemberVO member = new MemberVO();
//		member.setMemId("b001");
		member.setMemBir("2000/01/01");
//		member.setMemMail("aa@mail.com");
//		member.setMemPass("abcd");
//		member.setMemMileage(1000);
		
		
		Set<ConstraintViolation<MemberVO>> constraintViolations = validator.validate(member, InsertGroup.class); //(member)-> 기본그룹만 검증하겠다~ (member, InsertGroup.class) vo에 설정한 검증결과만 사용하겠다~
		constraintViolations.stream()
						.forEach(singleViolation->{
							Path propertyPath = singleViolation.getPropertyPath();
							String errorMessage = singleViolation.getMessage();
							log.error("{} : {}", propertyPath, errorMessage);
						});
		
	}
}

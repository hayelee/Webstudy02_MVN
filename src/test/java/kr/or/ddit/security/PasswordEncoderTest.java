package kr.or.ddit.security;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {

	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	String password = "java";
	String mem_pass = "{bcrypt}$2a$10$KrvCsABo0YJaQ28lCaINueN5jidp.SXJd0O/SAp/0TGJMkfcGTlPa"; // 단방향이라서 원문복원이 안됨!
	
	public void encoderTest() {
		String encoded = encoder.encode(password); 
		log.info("mem_pass : {}", encoded);
	}
	
	@Test
	public void matchTest() {
//		String encoded = encoder.encode(password);  // 이전에 암호화 했던 것과 다른 암호화가 만들어짐~
//		log.info("match result : {}", encoded.equals(mem_pass));
		encoder.matches(password, mem_pass); // 회원이 입력한 비밀번호, DB에 저장된 비밀번호
		log.info("match result : {}", encoder.matches(password, mem_pass));
	}
}

package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	private MemberDAO MemberDAO = new MemberDAOImpl(); // 하나 이상의 다오를 두개 이상의 서비스로 사용 가능
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Override
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = MemberDAO.selectMember(member.getMemId());
		if(savedMember==null || savedMember.isMemDelete())
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass();
		
		
		ServiceResult result = null;
		if(encoder.matches(inputPass, savedPass)) {
			
//			member.setMemName(savedMember.getMemName()); // 인증에 성공했을때 저장된 이름 가져와~
			try {
				BeanUtils.copyProperties(member, savedMember);
				result = ServiceResult.OK;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			
		}else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}


}

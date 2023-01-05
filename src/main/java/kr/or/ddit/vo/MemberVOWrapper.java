package kr.or.ddit.vo;

import java.security.Principal;

public class MemberVOWrapper implements Principal{
	private MemberVO realMember;

	public MemberVOWrapper(MemberVO realMember) { // 이 생성자가 만들어지는 순간 기본생성자가 사라짐(어댑터는 기본생성자X)
		super();
		this.realMember = realMember;
	}
	
	public MemberVO getRealMember() {
		return realMember;
	}

	@Override
	public String getName() { //한 사람의 개인이나 기관을 식별하는 식별자.
		
		return realMember.getMemId();
	}
}

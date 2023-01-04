package kr.or.ddit.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberUpdateController {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberUpdate.do")
	public String updateForm(
			HttpSession session
//			, @SessionAttribute("authMember") MemberVO authMember // 나중에 resolver만들어보기~
			, HttpServletRequest req, HttpServletResponse resp
	) {
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		
		// 상세조회
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		// 모델로 공유
		req.setAttribute("member", member);
		
		return "member/MemberForm";
		
	}
	
	@RequestMapping(value="/member/memberUpdate.do", method=RequestMethod.POST)
	public String updateProcess(
			@ModelAttribute("member") MemberVO member //필요한 파라미터 다 넣어줌
			, HttpServletRequest req
		){
		
		String viewName = null;
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, UpdateGroup.class);
		
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				// 비번 오류~
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/MemberForm";	
				break;
			case FAIL:
				// 서버 문제 발생~
				req.setAttribute("message", "서버 오류~ 쫌따 다시~");
				viewName = "member/MemberForm";	
				break;
			default:
				// 성공적으로 수정 완료~
				viewName = "redirect:/mypage.do";
				break;
			}
		}else {
			viewName = "member/MemberForm";	
		}
		
		return viewName;
	}
}

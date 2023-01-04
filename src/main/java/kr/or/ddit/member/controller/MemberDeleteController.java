package kr.or.ddit.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberDeleteController {
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping(value="/member/memberDelete.do", method=RequestMethod.POST)
	public String memberDelete(
			@RequestParam("memPass") String memPass
			, HttpSession session
			) {
//		1.
		MemberVO authMember = (MemberVO)session.getAttribute("authMember"); // 차후에 필터를 붙여야한다~
		String memId = authMember.getMemId();
		
		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		boolean valid = ValidationUtils.validate(inputData, errors, DeleteGroup.class);
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.removeMember(inputData);
			switch (result) {
			case INVALIDPASSWORD:
				session.setAttribute("message", "비번 오류");
				viewName = "redirect:/mypage.do";
				break;
			case FAIL:
				session.setAttribute("message", "서버 오류");
				viewName = "redirect:/mypage.do";
				break;
			default:
				// 세션을 invalidate시키고 로그인폼으로 돌아가라~
				session.invalidate();
				viewName = "redirect:/";
				break;
			}
		}else {
			session.setAttribute("message", "아이디나 비밀번호 누락");
			viewName = "redirect:/mypage.do";
		}
		return viewName;
	}
}

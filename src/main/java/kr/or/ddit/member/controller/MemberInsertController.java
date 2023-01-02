package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 *  Backend controller(command handler) --> Plain Old Java Object
 */
public class MemberInsertController implements AbstractController {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
	
	@Override
		public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String method = req.getMethod();
			RequestMethod requestMethod = RequestMethod.valueOf(method.toUpperCase());
			String viewName = null;
			if(requestMethod==RequestMethod.GET) {
				viewName = memberForm(req, resp);
			}else if(requestMethod==RequestMethod.POST) {
				viewName = memberInsert(req, resp);
			}else {
				//우리가 처리할 수 없는 요청!
				resp.sendError(405, method + "는 지원하지 않음.");
			}
			return viewName;
		}
	
	public String memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "member/MemberForm";
		
	}
	
	public String memberInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// command object - 검증 대상
		MemberVO member = new MemberVO();
		// 가입 실패하면 입력한 정보 초기화하지 않고 살려두기~(성공하면 자동 삭제)
		req.setAttribute("member", member); 
		
//		member.setMemId(req.getParameter("memId"));
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		try {
			BeanUtils.populate(member, parameterMap); // set어쩌고 10몇줄 안써도 됨~
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
		// 여기서 검증~
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
			case PKDUPLICATED:
				// 아이디 중복됐으니 다시 입력해라~
				req.setAttribute("message", "아이디 중복");
				viewName = "member/MemberForm";
				break;
			case FAIL:
				// 다시 가입버튼을 누를 수 있는 곳으로 돌아가라~
				req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
				viewName = "member/MemberForm";
				break;
				
			default:
				// 성공했으니 돌아가라~
				viewName = "redirect:/";
				break;
			}
		}else {
			viewName = "member/MemberForm";
		}
		return viewName;
	}
}

package kr.or.ddit.member.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 *  Backend controller(command handler) --> Plain Old Java Object
 */
@Controller
public class MemberInsertController {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
  
	@RequestMapping("/member/memberInsert.do")
	public String memberForm() {
		return "member/MemberForm";
		
	}
	
	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST) // 이 주소로 받은 것 중에 post요청반 받을 수 있다!
	public String memberInsert(
		HttpServletRequest req
		, @ModelAttribute("member") MemberVO member
		, @RequestPart(value="memImage", required=false) MultipartFile memImage
	) throws ServletException, IOException {
//		2번째 방법
//		if(req instanceof MultipartHttpServletRequest) {
//			MultipartFile memImage = ((MultipartHttpServletRequest) req).getFile("memImage");
			member.setMemImage(memImage);
//		}
		
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

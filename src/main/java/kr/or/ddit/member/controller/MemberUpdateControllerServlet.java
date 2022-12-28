package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberUpdate.do")
public class MemberUpdateControllerServlet extends HttpServlet {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//누구라는 정보와 누구의 상세정보, 그것을 전달할 정보 필요
//		1.
		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		
		// 상세조회
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		// 모델로 공유
		req.setAttribute("member", member);
		
		String viewName = "member/MemberForm";
		
		new InternalResourceViewResolver("/WEB-INF/views/", ".jsp").resolveView(viewName, req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		req.setCharacterEncoding("UTF-8");
		
		// 회원 정보를 담을 그릇~
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member, req.getParameterMap()); // set어쩌고 10몇줄 안써도 됨~
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
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
		
		
		new InternalResourceViewResolver("/WEB-INF/views/", ".jsp").resolveView(viewName, req, resp);
	}
}

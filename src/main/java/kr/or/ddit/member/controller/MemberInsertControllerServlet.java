package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")
public class MemberInsertControllerServlet extends HttpServlet {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String viewName = "member/MemberForm";
		
		new InternalResourceViewResolver("/WEB-INF/views/", ".jsp").resolveView(viewName, req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 검증 과정도 포함시키자~
		req.setCharacterEncoding("UTF-8");
		
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
		
		new InternalResourceViewResolver("/WEB-INF/views/", ".jsp").resolveView(viewName, req, resp);
	}
}

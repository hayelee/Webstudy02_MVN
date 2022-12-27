package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")
public class MemberInsertControllerServlet extends HttpServlet {
//	서비스와의 의존관계 코드
	private MemberService service = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String viewName = "/WEB-INF/views/member/MemberForm.jsp";
		
//		5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			// 디스패치로 포워딩
			req.getRequestDispatcher(viewName).forward(req, resp);
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		req.setCharacterEncoding("UTF-8");
		
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
		
		String viewName = null;
		
		ServiceResult result = service.createMember(member);
		switch (result) {
		case PKDUPLICATED:
			// 아이디 중복됐으니 다시 입력해라~
			req.setAttribute("message", "아이디 중복");
			viewName = "/WEB-INF/views/member/MemberForm.jsp";
			break;
		case FAIL:
			// 다시 가입버튼을 누를 수 있는 곳으로 돌아가라~
			req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
			viewName = "/WEB-INF/views/member/MemberForm.jsp";
			break;
			
		default:
			// 성공했으니 돌아가라~
			viewName = "redirect:/";
			break;
		}
		
//		5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			// 디스패치로 포워딩
			req.getRequestDispatcher(viewName).forward(req, resp);
			
		}
	}
}

package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;


@WebServlet("/member/memberView.do")
public class MemberViewControllerServlet extends HttpServlet {
   
   private MemberService service = new MemberServiceImpl();
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//   	1.
      String memId = req.getParameter("who");
      //요청에 대한 검증
      if(StringUtils.isBlank(memId)) {
    	  resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    	  return;
      }
//      2.
      MemberVO memberWho = service.retrieveMember(memId);
//      3.
      req.setAttribute("member", memberWho);
//      4.
      String viewName = "/WEB-INF/views/member/memberView.jsp";
//      5.
      if(viewName.startsWith("redirect:")) {
         viewName = viewName.substring("redirect:".length());
         System.out.println("viewName : "+viewName);
         resp.sendRedirect(req.getContextPath()+viewName);
      }
      else {
         req.getRequestDispatcher(viewName).forward(req, resp);
      }
   
   }
}
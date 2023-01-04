package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberListController { // 완전한 POJO가 되었어요~
	private MemberService service = new MemberServiceImpl();

	@RequestMapping("/member/memberList.do") // single annotation 메소드가 get으로 숨어있어요~
	public String memberList( // 개발자의 자유도가 높아짐~~
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage
		, @ModelAttribute SearchVO simpleCondition // CoC
		, HttpServletRequest req
	)  {
		
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);
		pagingVO.setCurrentPage(currentPage);// 첫번째 setter 호출
		pagingVO.setSimpleCondition(simpleCondition);
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO); // 받아올 필요 없어! service에서 다 받아놨으니까!
		req.setAttribute("pagingVO", pagingVO);
		
		log.info("paging data : {}", pagingVO);
		
		String viewName = "member/memberList";
		
		return viewName;
	}
}

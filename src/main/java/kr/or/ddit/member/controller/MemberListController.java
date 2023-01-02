package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ctc.wstx.util.StringUtil;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberListController implements AbstractController {
	private MemberService service = new MemberServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pageParam = req.getParameter("page");
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		
		SearchVO simpleCondition = new SearchVO(searchType, searchWord);
		
		int currentPage = 1;
		if(StringUtils.isNumeric(pageParam)){
			currentPage = Integer.parseInt(pageParam);
		}
		
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

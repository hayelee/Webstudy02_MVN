package kr.or.ddit.prod.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProdListController {// POJO가 됐다!(제약이 없어짐)
   private ProdService service = new ProdServiceImpl(); // 컨테이너를 통해 싱글톤 구현
   private OthersDAO othersDAO = new OthersDAOImpl();
   
   private void addAttribute(HttpServletRequest req) {
      req.setAttribute("lprodList", othersDAO.selectLprodList());// view단으로 전송하는 모델 하나씩 늘리기
      req.setAttribute("buyerList", othersDAO.selectBuyerList(null));// 전체 리스트 싹 보내기
   }
   
   private String listUI(HttpServletRequest req) { // UI 요청했을때 
      addAttribute(req);
      return "prod/prodList";
   }
   
   private String listData(
         int currentPage// 문자열 파라미터를 가져와서 파싱하는 책임까지 덜어내기~
         , HttpServletRequest req
   ) throws ServletException {
	   // 데이터 요청했을때 
//      String pageParam = req.getParameter("page");
      ProdVO detailCondition = new ProdVO();
      req.setAttribute("detailCondition", detailCondition);
//      detailCondition.setProdLgu(req.getParameter("prodLgu"));
//      detailCondition.setProdBuyer(req.getParameter("prodBuyer"));
//      detailCondition.setProdName(req.getParameter("prodName"));
      try {
         BeanUtils.populate(detailCondition, req.getParameterMap()); // 어차피 ProdVO에서 놀 거니까 이렇게 받으면 훨씬 편함
      } catch (IllegalAccessException | InvocationTargetException e) {
         throw new ServletException(e);
      }
         
//      int currentPage = 1;
//      if (StringUtils.isNumeric(pageParam)) {
//         currentPage = Integer.parseInt(pageParam);
//      }
      
      PagingVO<ProdVO> pagingVO = new PagingVO<>(5, 2);
      pagingVO.setCurrentPage(currentPage);
      pagingVO.setDetailCondition(detailCondition);
      
      service.retrieveProdList(pagingVO);
      
      req.setAttribute("pagingVO", pagingVO);
      log.info("paging data : {}", pagingVO);
      
      return "forward:/jsonView.do"; // 서블릿에서 또다른 서블릿으로 가는 거 식별
   }
   
   @RequestMapping("/prod/prodList.do")
   public String prodList(@RequestParam(value="page", required=false, defaultValue="1") int currentPage, HttpServletRequest req) throws ServletException { // 상위가 없어져서 예외에 대한 제약이 좀 사라짐
      String accept = req.getHeader("Accept");
      String viewName = null;
      if (accept.contains("json")) {
    	  // 데이터 요구
         viewName = listData(currentPage, req);
      } else {
    	  // UI요구
         viewName = listUI(req);
      }
      
      return viewName;
   }
}
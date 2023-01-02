package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdListController implements AbstractController {
   
   private ProdService service = new ProdServiceImpl();
   private OthersDAO othersDAO = new OthersDAOImpl();
   
   private void addAttribute(HttpServletRequest req) {
      req.setAttribute("lprodList", othersDAO.selectLprodList()); // view단으로 전송하는 모델 하나씩 늘리기
      req.setAttribute("buyerList", othersDAO.selectBuyerList(null)); // 전체 리스트 싹 보내기
   }
   
   private String listUI(HttpServletRequest req, HttpServletResponse resp) {
	   // UI 요청했을때 
	   addAttribute(req);
	   return "prod/prodList";
   }
   private String listData(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
	   // 데이터 요청했을때 
      String pageParam = req.getParameter("page");
      ProdVO detailCondition = new ProdVO();
      req.setAttribute("detailCondition", detailCondition);
//	  detailCondition.setProdLgu(req.getParameter("prodLgu"));
//	  detailCondition.setProdBuyer(req.getParameter("prodBuyer"));
//	  detailCondition.setProdName(req.getParameter("prodName"));
      
      try {
         BeanUtils.populate(detailCondition, req.getParameterMap());
      } catch (IllegalAccessException | InvocationTargetException e) {
         throw new ServletException(e);
      }
      
      int currentPage = 1;
      if(StringUtils.isNumeric(pageParam)) {
         currentPage = Integer.parseInt(pageParam);
      }
      
      PagingVO<ProdVO> pagingVO = new PagingVO<>(5,2);
      pagingVO.setCurrentPage(currentPage);
      pagingVO.setDetailCondition(detailCondition);
      
      service.retrieveProdList(pagingVO);
      req.setAttribute("pagingVO", pagingVO);
      return "forward:/jsonView.do"; // 서블릿에서 또다른 서블릿으로 가는 거 식별
   }
   
   @Override
   public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   
      String accept = req.getHeader("Accept");
      String viewName = null;
      
      if(accept.contains("json")) {
    	  // 데이터 요구
    	  viewName = listData(req, resp);
      }else {
    	  // UI요구
    	  viewName = listUI(req, resp);
      }
      return viewName;
   }
   
}
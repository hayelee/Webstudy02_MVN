package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdInsertController {
	private ProdService service = new ProdServiceImpl();
    private OthersDAO othersDAO = new OthersDAOImpl();
   
    private void addAttribute(HttpServletRequest req) {
       req.setAttribute("lprodList", othersDAO.selectLprodList());// view단으로 전송하는 모델 하나씩 늘리기
       req.setAttribute("buyerList", othersDAO.selectBuyerList(null));// 전체 리스트 싹 보내기
    }
	@RequestMapping("/prod/prodInsert.do")
	public String process(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodInsert.do", method=RequestMethod.POST)
	public String insertProcess(
		HttpServletRequest req
		, @ModelAttribute("prod") ProdVO prod //command Object ->  동작하려면 @ModelAttribute가 필요하다!
	) {
		addAttribute(req);
		
		//검증 
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK == result) {
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
			}else {
				req.setAttribute("message", "서버 문제있음");
				viewName = "prod/prodForm";
			}
		}else {
			viewName = "prod/prodForm";
		}
		
		return viewName;
	}
}

package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
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
		HttpServletRequest req // 이게 원본인지 wrapper인지 확인해야해! 
		, @ModelAttribute("prod") ProdVO prod //command Object ->  동작하려면 @ModelAttribute가 필요하다!
		, @RequestPart("prodImage") MultipartFile prodImage // required true가 생략되어 있음~
	) throws IOException, ServletException {
		addAttribute(req);
		
		prod.setProdImage(prodImage); // mime타입까지 다 체킹
		
//		String saveFileName = prod.getProdImg(); //이게 있으면 이미지 등록완, 없으면 등록 안된 것
		
//		if(saveFileName!=null) {// prodImage가 있으면~
//			1. 저장
			String saveFolderURL = "/resources/prodImages";
			ServletContext application = req.getServletContext(); // application 기본 객체가 들어감!
			String saveFolderPath = application.getRealPath(saveFolderURL);
			File saveFolder = new File(saveFolderPath);
			if(!saveFolder.exists()) // savefolder가 없으면~
					saveFolder.mkdirs(); //mkdirs로 해야 계층구조로 쫙 만들어줘!
			
			prod.saveTo(saveFolder);
//		}
		
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

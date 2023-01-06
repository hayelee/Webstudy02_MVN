package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

//    /prod/prodUpdate.do(GET, POST)

@Controller
public class ProdUpdateController {
   private ProdService service = new ProdServiceImpl();
   private OthersDAO othersDAO = new OthersDAOImpl();
   
   private void addAttribute(HttpServletRequest req) {
      req.setAttribute("lprodList", othersDAO.selectLprodList());// view단으로 전송하는 모델 하나씩 늘리기
      req.setAttribute("buyerList", othersDAO.selectBuyerList(null));// 전체 리스트 싹 보내기
   }
   @RequestMapping("/prod/prodUpdate.do")
   public String updateForm(
      @RequestParam("what") String prodId
      , HttpServletRequest req
   ) {
      ProdVO prod = service.retrieveProd(prodId);
      req.setAttribute("prod", prod);
      addAttribute(req);
      
      return "prod/prodForm";
   }
   
   @RequestMapping(value="/prod/prodUpdate.do", method=RequestMethod.POST)
   public String updateProd(
      @ModelAttribute("prod") ProdVO prod
      , @RequestParam("what") String prodId
      , HttpServletRequest req
      , @RequestPart(value="prodImage", required=false) MultipartFile prodImage
   ) throws IOException {
	   
		prod.setProdImage(prodImage); // mime타입까지 다 체킹
		
//			1. 저장
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext(); // application 기본 객체가 들어감!
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) // savefolder가 없으면~
				saveFolder.mkdirs(); //mkdirs로 해야 계층구조로 쫙 만들어줘!
		
		prod.saveTo(saveFolder);
	   
	   String viewName = null;
	   Map<String, List<String>> errors = new LinkedHashMap<>();
	   req.setAttribute("errors", errors);
	   boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);
	   
	   if(valid) {
		   ServiceResult result = service.modifyProd(prod);
		   if(ServiceResult.OK == result) {
			   viewName = "redirect:/prod/prodView.do?what=" + prod.getProdId();
		   }else {
			   req.setAttribute("message", "서버오류");
			   viewName = "prod/prodForm";
		   }
	   }else {
		   viewName = "prod/prodForm";
	   }
	   return viewName;
//      if(prodImage!=null && !prodImage.isEmpty()) {// prodImage가 있으면~
////			1. 저장
//			String saveFolderURL = "/resources/prodImages";
//			ServletContext application = req.getServletContext(); // application 기본 객체가 들어감!
//			String saveFolderPath = application.getRealPath(saveFolderURL);
//			File saveFolder = new File(saveFolderPath);
//			if(!saveFolder.exists()) // savefolder가 없으면~
//					saveFolder.mkdirs(); //mkdirs로 해야 계층구조로 쫙 만들어줘!
//			
////			2. metadata(저장한 파일의 url) 추출
//			String saveFileName = UUID.randomUUID().toString();
//			prodImage.transferTo(new File(saveFolder, saveFileName));
////			3. DB저장 : prodImg
//			prod.setProdImg(saveFileName);

   }
}
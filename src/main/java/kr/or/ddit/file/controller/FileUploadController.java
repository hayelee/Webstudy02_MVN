package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileUploadController {
	@RequestMapping(value="/file/upload.do", method=RequestMethod.POST)
//	@PostMapping("/file/upload.do") 스프링은 이거 가능!
	public String Upload(HttpServletRequest req, HttpSession session) throws IOException, ServletException { // 5개의 body를 가지고 있는 request
		String textPart = req.getParameter("textPart");
		String numPart = req.getParameter("numPart");
		String dataPart = req.getParameter("dataPart");
		log.info("textPart : {}", textPart);
		log.info("numPart : {}", numPart);
		log.info("dataPart : {}", dataPart);
		session.setAttribute("textPart", textPart);
		session.setAttribute("numPart", numPart);
		session.setAttribute("dataPart", dataPart);
		
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext(); // application 기본 객체가 들어감!
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) // savefolder가 없으면~
				saveFolder.mkdirs(); //mkdirs로 해야 계층구조로 쫙 만들어줘!
		
		List<String> metadata = req.getParts().stream() // 전부다 꺼내서 가지고 놀아요~DB에 넣었다 가정하고 session에 넣자!@
		 			.filter((p)-> // p -> parts 중 하나 (상품의 이미지) 여기서 part를 2개로 걸렀어요~
		 			p.getContentType() != null && p.getContentType().startsWith("image/"))//조건에 맞는 엘리먼트를 거를 수 있음! null이 돌아오면 string파일이 아니어요! filter엔 file만 들어있어요
					.map((p)->{ // p는 filefilter 하나씩의 값을 가지고 있어요~ map을 사용하면 그 안의 엘리먼트를 다른 타입으로 바꿀 수 있음@
						// Meta date -> 나중에 DB에 넣게됨!
						String originalFilename = p.getSubmittedFileName(); // 원본파일명으로 저장
						String saveFilename = UUID.randomUUID().toString(); // 웬만하면 겹치지 않는 16진수의 긴 이름
						
						File saveFile = new File(saveFolder, saveFilename);
						try {
							p.write(saveFile.getCanonicalPath());//전체 경로 꺼내와
							String saveFileURL = saveFolderURL + "/" + saveFilename;
							return saveFileURL;
						} catch (IOException e) {
							throw new RuntimeException(e);
						} 
					}).collect(Collectors.toList()); // 총 5개 데이터를 갖게 됨!
		
		session.setAttribute("fileMetadata", metadata);
		
		return "redirect:/fileupload/uploadForm.jsp";
		
	}
}

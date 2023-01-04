package kr.or.ddit.prod.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class ProdInsertController {

	@RequestMapping("/prod/prodInsert.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		return "prod/prodForm";
	}

}

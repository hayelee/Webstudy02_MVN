package kr.or.ddit.memo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.dao.MemoDAOImpl;
import kr.or.ddit.vo.MemoVO;

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet{
   private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);
   
//   private MemoDAO dao = FileSystemMemoDAOImpl.getInstance(); 
//   private MemoDAO dao = DataBaseMemoDAOImpl.getInstance(); //구현체 만들기
   //하드코딩 되어있으므로 결합력 늘어난다. (private MemoDAO dao; <-이렇게 쓰려면 컨테이너 필요: Spring)
   private MemoDAO dao = new MemoDAOImpl();
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String accept = req.getHeader("Accept");
      log.info("accept header : {}", accept); // +, string format쓰지 않고도 원하는 형태 사용 가능
      if(accept.contains("xml")) {
         resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
         return;
      }
      
      //2.모델확보
      List<MemoVO> memoList = dao.selectMemoList();
      //3.모델공유
      req.setAttribute("memoList", memoList);
      String viewName = "/jsonView.do";
      req.getRequestDispatcher(viewName).forward(req, resp);
   }
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      Post-Redirect-Get : PRG pattern
      req.setCharacterEncoding("UTF-8");
      
      MemoVO memo = getMemoFromRequest(req);
      dao.insertMemo(memo);
      resp.sendRedirect(req.getContextPath()+"/memo");
   }
   
   private MemoVO getMemoFromRequest(HttpServletRequest req) throws IOException {
      //1.역직렬화
      //2.언마샬링
      
      String contentType = req.getContentType();
      MemoVO memo = null;
      if(contentType.contains("json")) {
         try(
            BufferedReader br = req.getReader();//body content read용 입력 스트림
               ){
            memo = new ObjectMapper().readValue(br, MemoVO.class);
            return memo;
         }
      } else if(contentType.contains("xml")) {
         try(
            BufferedReader br = req.getReader();//body content read용 입력 스트림
               ){
            memo = new XmlMapper().readValue(br, MemoVO.class);
            return memo;
         }
      } else { //파라미터로 옴
         //getParameter
         memo = new MemoVO();
         memo.setWriter(req.getParameter("writer"));
         memo.setDate(req.getParameter("date"));
         memo.setContent(req.getParameter("content"));
      }
      return memo;
   }

   @Override
   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

   }
   
   @Override
   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

   }
}
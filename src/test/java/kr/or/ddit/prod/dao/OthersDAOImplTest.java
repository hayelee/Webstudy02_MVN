package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OthersDAOImplTest {
	private OthersDAO dao = new OthersDAOImpl();
	private PagingVO<BuyerVO> pagingVO;
	
   @Test
   public void testSelectBuyerList() {
      List<BuyerVO> buyerList = dao.selectBuyerList(null);
      assertEquals(10, buyerList.size());
      log.info("pagingVO : {}", buyerList);
   }
}

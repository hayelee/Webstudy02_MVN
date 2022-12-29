package kr.or.ddit.prod.service;

import static org.junit.Assert.*;

import org.junit.Test;

import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProdServiceImplTest {

	private ProdDAO dao = new ProdDAOImpl();
	
	@Test
	public void testRetrieveProd() {
		ProdVO prod = dao.selectProd("P101000001");
		assertNotNull(prod);
		log.info("buyer : {}", prod.getBuyer());
		prod.getMemberSet().stream()
				.forEach(user->{
					log.info("구매자 : {}", user);
				});
	}

}

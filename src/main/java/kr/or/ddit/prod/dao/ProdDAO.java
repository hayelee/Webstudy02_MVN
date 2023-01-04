package kr.or.ddit.prod.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public interface ProdDAO {
	/**
	 * @param prodId
	 * @return 존재하지 않으면, null 반환
	 */
	public ProdVO selectProd(String prodId);
	
	/**
	 * 검색 조건에 맞는 레코드 수 반환
	 * @param pagingVO
	 * @return
	 */
	public int selectTotalRecord(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 검색 조건과 현재 페이지에 맞는 상품 목록 조회
	 * @param pagingVO
	 * @return
	 */
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 신규 상품 등록
	 * @param prod
	 * @return 등록된 상품 수
	 */
	public int insertProd(ProdVO prod);
	
	/** 상품 수정
	 * @param prod
	 * @return 수정된 상품 수
	 */
	public int updateProd(ProdVO prod);
	
	
//	public int deleteProd(String prodId);
}

package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemoVO;

public class MemoDAOImpl implements MemoDAO {
	
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	
	//지역변수로만 사용해야해 그래서 매번 try문으로 만들어줬어
	@Override
	public List<MemoVO> selectMemoList() {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			return mapperProxy.selectMemoList();
//			return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList");
		}
	}

	@Override
	public int insertMemo(MemoVO memo) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); // 트랜잭션(ACID) 시작
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.insertMemo(memo);
//			memo를 넘기지 않아도 에러가 나지 않아(시그니처 제약이 없기 때문) 위에 방법(mapperProxy)을 사용하면 오류가 한번에 보여서 좋아요~
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo", memo);
			sqlSession.commit(); // 트랜잭션 종료 (쿼리를 실행하고 나면 항상 이 과정이 필요해)
			return rowcnt;
		}
	}

	@Override
	public int updateMemo(MemoVO memo) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.updateMemo(memo);
//			int rowcnt = sqlSession.update("kr.or.ddit.memo.dao.MemoDAO.updateMemo", memo);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int deleteMemo(int code) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.deleteMemo(code);
//			int rowcnt = sqlSession.delete("kr.or.ddit.memo.dao.MemoDAO.deleteMemo", code);
			sqlSession.commit();
			return rowcnt;
		}
	}
}

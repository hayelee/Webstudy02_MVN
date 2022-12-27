package kr.or.ddit.member.dao;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	
	@Override
	public int insertMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				int rowcnt = mapperProxy.insertMember(member);
				sqlSession.commit(); // 트랜잭션 종료 (쿼리를 실행하고 나면 항상 이 과정이 필요해)
				return rowcnt;
			}
	
	}

	@Override
	public List<MemberVO> selectMemberList() {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				return mapperProxy.selectMemberList();
			}
	}

	@Override
	public MemberVO selectMember(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				return mapperProxy.selectMember(memId);
			}
	}

	@Override
	public int updateMember(MemberVO member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String memId) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MemoVO;

public interface MemoDAO {
	public List<MemoVO> selectMemoList();
	public int insertMemo(MemoVO memo); // 신규로 작성, 코드값 필요 x
	public int updateMemo(MemoVO memo); // 프라이머리키 역할하는 코드값 필요 o
	public int deleteMemo(@Param("code") int code); // 이름이 없었지만 이름이 존재하는 값으로 바뀜
}

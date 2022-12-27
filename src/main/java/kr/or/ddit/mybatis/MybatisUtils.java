package kr.or.ddit.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	private static SqlSessionFactory sqlSessionFactory;
	
	static { // static 코드블럭 -> 클래스가 메모리에 로딩될 때  딱 한번 실행됨
		String configPath="kr/or/ddit/mybatis/mybatis-config.xml";
		try(
			Reader reader = Resources.getResourceAsReader(configPath);
		) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}

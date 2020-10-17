package com.ljh.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.CategoryVO;

@Repository
public class AdminDAOImpl implements AdminDAO{

	@Inject
	private SqlSession sqlSession;
	
	// 매퍼.
	private static String namespace = "com.ljh.mappers.adminMapper";
	
	@Override
	public List<CategoryVO> category() throws Exception {
		return sqlSession.selectList(namespace + ".category");
	}

}

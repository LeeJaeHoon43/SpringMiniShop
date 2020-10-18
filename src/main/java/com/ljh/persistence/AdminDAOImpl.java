package com.ljh.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.CategoryVO;
import com.ljh.domain.GoodsVO;
import com.ljh.domain.GoodsViewVO;

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

	@Override
	public void register(GoodsVO vo) throws Exception {
		sqlSession.insert(namespace + ".register", vo);
	}

	@Override
	public List<GoodsVO> goodslist() throws Exception {
		return sqlSession.selectList(namespace + ".goodslist");
	}

	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return sqlSession.selectOne(namespace + ".goodsView", gdsNum);
	}

	@Override
	public void goodsModify(GoodsVO vo) throws Exception {
		sqlSession.update(namespace + ".goodsModify", vo);
	}

	@Override
	public void goodsDelete(int gdsNum) throws Exception {
		sqlSession.delete(namespace + ".goodsDelete", gdsNum);
	}
}

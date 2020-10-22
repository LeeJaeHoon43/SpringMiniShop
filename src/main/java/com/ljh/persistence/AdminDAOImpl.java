package com.ljh.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.CategoryVO;
import com.ljh.domain.GoodsVO;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.OrderListVO;
import com.ljh.domain.OrderVO;
import com.ljh.domain.ReplyListVO;

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
	public List<GoodsViewVO> goodslist() throws Exception {
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

	@Override
	public List<OrderVO> orderList() throws Exception {
		return sqlSession.selectList(namespace + ".orderList");
	}

	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return sqlSession.selectList(namespace + ".orderView", order);
	}

	@Override
	public void delivery(OrderVO order) throws Exception {
		sqlSession.update(namespace + ".delivery", order);
	}

	@Override
	public void changeStock(GoodsVO goods) throws Exception {
		sqlSession.update(namespace + ".changeStock", goods);
	}

	@Override
	public List<ReplyListVO> allReply() throws Exception {
		return sqlSession.selectList(namespace + ".allReply");
	}

	@Override
	public void deleteReply(int repNum) throws Exception {
		sqlSession.delete(namespace + ".deleteReply", repNum);
	}
}

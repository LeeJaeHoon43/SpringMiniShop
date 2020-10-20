package com.ljh.persistence;

import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.ReplyListVO;
import com.ljh.domain.ReplyVO;

@Repository
public class ShopDAOImpl implements ShopDAO{

	@Inject
	private SqlSession sqlSession;
	
	// 매퍼.
	private static String namespace = "com.ljh.mappers.shopMapper";

	// 카테고리 별 상품 리스트 : 1차 분류.
	@Override
	public List<GoodsViewVO> list(int cateCode, int cateCodeRef) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cateCode", cateCode);
		map.put("cateCodeRef", cateCodeRef);
		return sqlSession.selectList(namespace + ".list_1", map);
	}

	// 카테고리 별 상품 리스트 : 2차 분류.
	@Override
	public List<GoodsViewVO> list(int cateCode) throws Exception {
		return sqlSession.selectList(namespace + ".list_2", cateCode);
	}

	// 상품 조회.
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return sqlSession.selectOne("com.ljh.mappers.adminMapper.goodsView", gdsNum);
	}

	// 상품 댓글 작성.
	@Override
	public void registReply(ReplyVO reply) throws Exception {
		sqlSession.insert(namespace + ".registReply", reply);
	}

	// 상품 댓글 목록.
	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return sqlSession.selectList(namespace + ".replyList", gdsNum);
	}

	// 상품 댓글 삭제.
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		sqlSession.delete(namespace + ".deleteReply", reply);
	}

	// 아이디 체크.
	@Override
	public String idCheck(int repNum) throws Exception {
		return sqlSession.selectOne(namespace + ".replyUserIdCheck", repNum);
	}

	@Override
	public void modifyReply(ReplyVO reply) throws Exception {
		sqlSession.update(namespace + ".modifyReply", reply);
	}
}

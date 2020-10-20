package com.ljh.service;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.ReplyListVO;
import com.ljh.domain.ReplyVO;
import com.ljh.persistence.ShopDAO;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Inject
	private ShopDAO dao;

	// 카테고리 별 상품 목록.
	@Override
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception {
		int cateCodeRef = 0; // 카테고리 참조 코드. 없어도 무관.
		if (level == 1) { // 1차 분류.
			cateCodeRef = cateCode;
			return dao.list(cateCode, cateCodeRef);
		}else { // 2차 분류.
			return dao.list(cateCode);			
		}
	}

	// 상품 조회.
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return dao.goodsView(gdsNum);
	}

	// 상품 소감(댓글) 작성.
	@Override
	public void registReply(ReplyVO reply) throws Exception {
		dao.registReply(reply);
	}

	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return dao.replyList(gdsNum);
	}

	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		dao.deleteReply(reply);
	}

	@Override
	public String idCheck(int repNum) throws Exception {
		return dao.idCheck(repNum);
	}

	@Override
	public void modifyReply(ReplyVO reply) throws Exception {
		dao.modifyReply(reply);
	}
}

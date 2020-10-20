package com.ljh.service;

import java.util.List;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.ReplyListVO;
import com.ljh.domain.ReplyVO;

public interface ShopService {
	// 카테고리 별 상품 리스트.
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception;
	
	// 상품 조회.
	public GoodsViewVO goodsView(int gdsNum) throws Exception;
	
	// 상품 소감(댓글) 작성.
	public void registReply(ReplyVO reply) throws Exception;
	
	// 상품 소감 목록.
	public List<ReplyListVO> replyList(int gdsNum) throws Exception;
	
	// 상품 댓글 삭제.
	public void deleteReply(ReplyVO reply) throws Exception;
		
	// 아이디 체크.
	public String idCheck(int repNum) throws Exception;
	
	// 상품 댓글 수정.
	public void modifyReply(ReplyVO reply) throws Exception;
}

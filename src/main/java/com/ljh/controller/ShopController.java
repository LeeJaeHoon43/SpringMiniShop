package com.ljh.controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.MemberVO;
import com.ljh.domain.ReplyListVO;
import com.ljh.domain.ReplyVO;
import com.ljh.service.ShopService;

@Controller
@RequestMapping("/shop/*")
public class ShopController {

	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Inject
	ShopService shopService;

	// 카테고리 별 상품 목록.
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getList(@RequestParam("c") int cateCode, @RequestParam("l") int level, Model model) throws Exception{
		logger.info("get list");
		List<GoodsViewVO> list = null;
		list = shopService.list(cateCode, level);
		model.addAttribute("list", list);
	}

	// 상품 조회.
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception{
		logger.info("get view");
		GoodsViewVO view = shopService.goodsView(gdsNum);
		model.addAttribute("view", view);
	}

	// 상품 댓글 작성.
	@ResponseBody
	@RequestMapping(value = "/view/registReply", method = RequestMethod.POST)
	public void registReply(ReplyVO reply, HttpSession session) throws Exception {
		logger.info("regist reply");
		MemberVO member = (MemberVO)session.getAttribute("member");
		reply.setUserId(member.getUserId());
		shopService.registReply(reply);
	}	

	// 상품 댓글 목록.
	@ResponseBody
	@RequestMapping(value = "/view/replyList", method = RequestMethod.GET)
	public List<ReplyListVO> getReplyList(@RequestParam("n") int gdsNum) throws Exception {
		logger.info("get reply list");
		List<ReplyListVO> reply = shopService.replyList(gdsNum);
		return reply;
	}
	
	// 상품 댓글 삭제.
	@ResponseBody
	@RequestMapping(value = "/view/deleteReply", method = RequestMethod.POST)
	public int getReplyList(ReplyVO reply, HttpSession session) throws Exception{
		logger.info("post delete reply");
		int result = 0;
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = shopService.idCheck(reply.getRepNum());
		if (member.getUserId().equals(userId)) {
			reply.setUserId(member.getUserId());
			shopService.deleteReply(reply);
			result = 1;
		}
		return result;
	}
	
	// 상품 댓글 수정.
	@ResponseBody
	@RequestMapping(value = "/view/modifyReply", method = RequestMethod.POST)
	public int modifyReply(ReplyVO reply, HttpSession session)throws Exception{
		logger.info("modify reply");
		int result = 0;
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = shopService.idCheck(reply.getRepNum());
		if (member.getUserId().equals(userId)) {
			reply.setUserId(member.getUserId());
			shopService.modifyReply(reply);
			result = 1;
		}
		return result;
	}
}


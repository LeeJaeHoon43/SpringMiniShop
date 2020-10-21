package com.ljh.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ljh.domain.CartListVO;
import com.ljh.domain.CartVO;
import com.ljh.domain.GoodsViewVO;
import com.ljh.domain.MemberVO;
import com.ljh.domain.OrderDetailVO;
import com.ljh.domain.OrderListVO;
import com.ljh.domain.OrderVO;
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

	// 카트 담기.
	@ResponseBody
	@RequestMapping(value = "/view/addCart", method = RequestMethod.POST)
	public int addCart(CartVO cart, HttpSession session)throws Exception{
		int result = 0;
		MemberVO member = (MemberVO)session.getAttribute("member");
		if (member != null) {
			cart.setUserId(member.getUserId());
			shopService.addCart(cart);
			result = 1;
		}
		return result;
	}

	// 카트 목록.
	@RequestMapping(value = "/cartList", method = RequestMethod.GET)
	public void getCartList(HttpSession session, Model model) throws Exception{
		logger.info("get cart list");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		List<CartListVO> cartList = shopService.cartList(userId);
		model.addAttribute("cartList", cartList);
	}

	// 카트 삭제.
	@ResponseBody
	@RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
	public int deleteCart(HttpSession session, @RequestParam(value = "chbox[]") List<String> chArr, CartVO cart) throws Exception{
		logger.info("delete cart");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		int result = 0;
		int cartNum = 0;
		if (member != null) {
			cart.setUserId(userId);
			for (String i : chArr) {
				cartNum = Integer.parseInt(i);
				cart.setCartNum(cartNum);
				shopService.deleteCart(cart);
			}
			result = 1;
		}
		return result;
	}

	// 주문.
	@RequestMapping(value = "/cartList", method = RequestMethod.POST)
	public String order(HttpSession session, OrderVO order, OrderDetailVO orderDetail)throws Exception{
		logger.info("order");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum = "";

		for (int i = 1; i <= 6 ; i++) {
			subNum += (int)(Math.random() * 10);
		}

		String orderId = ymd + "_" + subNum;
		order.setOrderId(orderId);
		order.setUserId(userId);
		shopService.orderInfo(order);
		orderDetail.setOrderId(orderId);
		shopService.orderInfo_Details(orderDetail);
		shopService.cartAllDelete(userId);
		return "redirect:/shop/orderList";
	}

	// 주문 목록.
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public void getOrderList(HttpSession session, OrderVO order, Model model) throws Exception{
		logger.info("get order list");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		order.setUserId(userId);
		List<OrderVO> orderList = shopService.orderList(order);
		model.addAttribute("orderList", orderList);
	}

	// 주문 상세 목록.
	@RequestMapping(value = "/orderView", method = RequestMethod.GET)
	public void getOrderList(HttpSession session, @RequestParam("n") String orderId, OrderVO order, Model model) throws Exception{
		logger.info("get order view");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		order.setUserId(userId);
		order.setOrderId(orderId);
		List<OrderListVO> orderView = shopService.orderView(order);
		model.addAttribute("orderView", orderView);
	}
}

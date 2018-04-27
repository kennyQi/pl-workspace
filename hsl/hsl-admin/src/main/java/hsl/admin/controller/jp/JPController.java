package hsl.admin.controller.jp;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.model.auth.AuthUser;
import hsl.admin.common.ArraysUtil;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.domain.model.jp.JPOrder;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.dto.jp.RefundActionTypeDTO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.api.jp.JPService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.api.v1.request.command.jp.JPCancelTicketCommand;

@Controller
@RequestMapping(value="/jp")
public class JPController extends BaseController {

	@Autowired
	private JPService jpService;
	
	@Autowired
	private JPOrderLocalService jpOrderLocalService;
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * 机票订单列表
	 * @param request
	 * @param model
	 * @param jpOrderQO
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/order/list")
	public String orderList(HttpServletRequest request, Model model,
			@ModelAttribute HslJPOrderQO jpOrderQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize) {
		
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(jpOrderQO);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination = jpService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("jpOrderQO", jpOrderQO);
		model.addAttribute("statusMap", JPOrderStatusConstant.SHOP_JPORDER_STATUS_MAP);
		model.addAttribute("payMap", JPOrderStatusConstant.SHOP_JPORDER_PAY_STATUS_MAP);
		model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
		model.addAttribute("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);
		model.addAttribute("BACK_SUCC_REFUND", JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC);
		model.addAttribute("DISCARD_SUCC_REFUND", "");//废票成功已退款,先保留
		model.addAttribute("nowDate", new Date());
		
		return "/jp/order/list.html";
	}
	
	/**
	 * 订单详情
	 * @return
	 */
	@RequestMapping(value = "/order/detail")
	public String orderDetail(Model model, @RequestParam(value = "id", required = false) String id,
			 @RequestParam(value = "payStatus", required = false) String payStatus) {
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setDealerOrderCode(id);
		JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);
		
		if (jpOrder != null) {
//			jpOrder.setFlightDTO(JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightDTO.class));
			
			if (jpOrder.getWastWorkTime() != null) {
				Date nowDate = new Date();
				if (nowDate.getTime() < jpOrder.getWastWorkTime().getTime()) {
					model.addAttribute("IS_WAST_WORK_TIME", true);
				}
			}
			
			model.addAttribute("jpOrder", jpOrder);
			HslCouponQO cqo = new HslCouponQO();
			if(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC.equals( jpOrder.getPayStatus().toString())){
				cqo.setUseEvent(true);
				HgLogger.getInstance().info("zhuxy", newLog("机票详情方法","查询订单详情", "已退款的情况查询卡券"));
			}
			cqo.setOrderId(jpOrder.getDealerOrderCode());
			List<CouponDTO> couponDTOs = couponService.queryList(cqo);
			Double couponPrice = 0d ;
			if(couponDTOs!=null&&couponDTOs.size()>0){
				for(CouponDTO coupon : couponDTOs){
					try{
						couponPrice = couponPrice + coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					}catch(NullPointerException e){
						HgLogger.getInstance().debug("zhuxy", "机票订单详细页面：卡券id为"+coupon.getId()+"的面值查不出来");
					}
				}
			}
			model.addAttribute("couponPrice", couponPrice);
		}
		model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);//未付款
		model.addAttribute("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);//待确认
		model.addAttribute("statusMap", JPOrderStatusConstant.SHOP_JPORDER_STATUS_MAP);
		model.addAttribute("payMap", JPOrderStatusConstant.SHOP_JPORDER_PAY_STATUS_MAP);
		model.addAttribute("PAY_SUCC", JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);//支付成功
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);//已出票
		
		return "/jp/order/order_detail.html";
	}
	
	/**
	 * 返回订单取消页面
	 * @return
	 */
	@RequestMapping(value = "/order/toCancel")
	public String toCancel(Model model, @RequestParam(value = "id", required = false) String id) {
		model.addAttribute("id", id);
		return "/jp/order/order_cancel.html";
	}
	
	/**
	 * 返回申请退票页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/order/toRefundBack")
	public String toRefundBack(Model model, @RequestParam(value = "id", required = false) String id) {
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setId(id);
		JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);
		
		if (jpOrder != null) {
			List<RefundActionTypeDTO> refundActionTypeDTOs = jpService.queryCancelOrderTicketReason(jpOrder.getPlatCode());
			//区分废票原因，退票原因 tandeng 20140922
			if(refundActionTypeDTOs != null && refundActionTypeDTOs.size() > 0){
				
				String tempRefundType = null;
				
				int i = refundActionTypeDTOs.size() -1;
				
				for (; i >= 0; i--) {
					
					tempRefundType = refundActionTypeDTOs.get(i).getRefundType().trim();
					
					if(tempRefundType.equalsIgnoreCase("T")){
						continue;
					}else{
						refundActionTypeDTOs.remove(i);
					}
				}			
			}
			model.addAttribute("refundTypes", refundActionTypeDTOs);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("jpOrder", jpOrder);
		return "/jp/order/order_refund_back.html";
	}
	
	/**
	 * 返回申请废票页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/order/toRefundDiscard")
	public String toRefundDiscard(Model model, @RequestParam(value = "id", required = false) String id) {
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setId(id);
		JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);
		
		if (jpOrder != null) {
			List<RefundActionTypeDTO> refundActionTypeDTOs = jpService.queryCancelOrderTicketReason(jpOrder.getPlatCode());
			//区分废票原因，退票原因 tandeng 20140922
			if(refundActionTypeDTOs != null && refundActionTypeDTOs.size() > 0){
				
				String tempRefundType = null;
				
				int i = refundActionTypeDTOs.size() -1;
				
				for (; i >= 0; i--) {
					
					tempRefundType = refundActionTypeDTOs.get(i).getRefundType().trim();
					
					if(tempRefundType.equalsIgnoreCase("F")){
						continue;
					}else{
						refundActionTypeDTOs.remove(i);
					}
				}			
			}
			model.addAttribute("refundTypes", refundActionTypeDTOs);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("jpOrder", jpOrder);
		return "/jp/order/order_refund_discard.html";
	}
	
	/**
	 * 取消订单（包括退废票）
	 * @return
	 */
	@RequestMapping(value = "/order/cancel")
	@ResponseBody
	public String orderCancel(@RequestParam(value = "id", required = false) String id, 
							  @RequestParam(value = "ticketNumbers", required = false) String[] ticketNumbers, 
							  @RequestParam(value = "refundType", required = false) String refundType, 
							  @RequestParam(value = "backPolicy", required = false) String backPolicy, 
							  @RequestParam(value = "backAmount", required = false) String backAmount, 
							  @RequestParam(value = "actionType", required = false) String actionType,
							  @RequestParam(value = "reason", required = false) String reason,
							  @RequestParam(value = "op", required = false) String op
			) {
		String success = "";
		String fail = "";
		if(StringUtils.isBlank(op)){
			success = "订单取消成功";
			fail = "订单取消失败";
		}else if(op.equals("1")){
			success = "废票成功";
			fail = "废票失败";
		}else if(op.equals("2")){
			success = "退票成功";
			fail = "退票失败";
		}
		
		AuthUser authUser = super.getAuthUser();
		if (authUser != null) {
			
			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setId(id);
			JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);
			
			if (jpOrder != null) {
				JPCancelTicketCommand command = new JPCancelTicketCommand();
				command.setOrderId(jpOrder.getOrderCode());
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				command.setTicketNumbers(ArraysUtil.toStringWithSlice(ticketNumbers, ","));
				command.setName(authUser.getLoginName());
				command.setRefundType(refundType);
				command.setBackPolicy(backPolicy);
				command.setBackAmount(backAmount);
				command.setActionType(actionType);
				command.setReason(reason);
				JPOrderDTO jpOrderDTO = jpService.cancelTicket(command);
				
				if (jpOrderDTO == null) {
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, fail, "", "");
				} else {			
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, success, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "jp");
				}
			} else {
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "查询不到相应订单", "", "");
			}
		} else {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "请登录", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "");
		}
	}
	
	/**
	 * 退款信息
	 * @return
	 */
	@RequestMapping(value = "/order/rt")
	public String rt(@RequestParam(value = "id", required = false) String id) {
		return "";
	}
	
}

package hsl.admin.controller.jp;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.model.auth.AuthUser;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.jp.FlightOrderService;
import hsl.domain.model.jp.FlightOrder;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.jp.plfx.CancelTicketGNCommand;
import hsl.pojo.command.jp.plfx.RefundTicketGNCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.RefundTicketGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.exception.GNFlightException;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.jp.JPService;

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

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping(value="/jp")
public class JPController extends BaseController {
	@Autowired
	private JPService jpService;
	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
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
			@ModelAttribute FlightOrderQO jpOrderQO,
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
		model.addAttribute("jpStatusMap", JPOrderStatusConstant.JPORDER_STATUS_MAP);
		model.addAttribute("statusMap", JPOrderStatusConstant.SHOP_JPORDER_STATUS_MAP);
		model.addAttribute("payMap", JPOrderStatusConstant.SHOP_JPORDER_PAY_STATUS_MAP);
		model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
		model.addAttribute("PAY_SUCC", JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);
		model.addAttribute("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);
		model.addAttribute("TICKET_REFUND_FAIL", JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL);
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
	public String orderDetail(HttpServletRequest request,Model model, @RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "payStatus", required = false) String payStatus) {
		FlightOrderQO qo = new FlightOrderQO();
		String type=request.getParameter("queryType");
		if(StringUtils.isNotBlank(type)&&type.equals("c")){
			qo.setOrderNO(id);
			qo.setIsCouponOrderQuery(true);
		}else{
			qo.setId(id);
		}
		FlightOrder jpOrder = flightOrderService.queryUnique(qo);
		if (jpOrder != null) {
			model.addAttribute("jpOrder", jpOrder);
			HslCouponQO cqo = new HslCouponQO();
			if(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC.equals( jpOrder.getPayStatus().toString())){
				cqo.setUseEvent(true);
				HgLogger.getInstance().info("zhuxy", newLog("机票详情方法","查询订单详情", "已退款的情况查询卡券"));
			}
			cqo.setOrderId(jpOrder.getOrderNO());
			List<CouponDTO> couponDTOs = couponService.queryList(cqo);
			HgLogger.getInstance().info("zhuxy", "机票订单详细页面：couponDTOs"+JSON.toJSONString(couponDTOs));
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
			//税款
			Double faxMoney=jpOrder.getFlightPriceInfo().getBuildFee()+jpOrder.getFlightPriceInfo().getOilFee();
			model.addAttribute("faxMoney", faxMoney);
			//查询余额使用
			AccountConsumeSnapshotQO bQo=new AccountConsumeSnapshotQO();
			//查询帐号消费记录时判断机票支付状态 如果是 已退款 则按照 退款订单ID即实体的id查询
			if(jpOrder.getPayStatus()==Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC)){
				bQo.setRefundOrderId(jpOrder.getId());
			} else {
				bQo.setOrderId(jpOrder.getOrderNO());
			}
			bQo.setOrderId(jpOrder.getOrderNO());
			bQo.setOrderType(AccountConsumeSnapshot.ORDERTYPE_JP);
			bQo.setStatus(jpOrder.getPayStatus());
			List<AccountConsumeSnapshotDTO> accountList=accountConsumeSnapshotService.queryList(bQo);
			double balance=0d;
			double refundPrice=0d;
			if(accountList!=null&&accountList.size()>0){
				for(AccountConsumeSnapshotDTO account:accountList){
					balance+=account.getPayPrice();
					if(account.getRefundPrice()!=null){
						refundPrice+=account.getRefundPrice();	
					}
				}
			}
			model.addAttribute("balance", balance);
			model.addAttribute("refundPrice", refundPrice);
		}
		model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);//未付款
		model.addAttribute("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);//待确认
		model.addAttribute("jpStatusMap", JPOrderStatusConstant.JPORDER_STATUS_MAP);
		model.addAttribute("statusMap", JPOrderStatusConstant.SHOP_JPORDER_STATUS_MAP);
		model.addAttribute("payMap", JPOrderStatusConstant.SHOP_JPORDER_PAY_STATUS_MAP);
		model.addAttribute("PAY_SUCC", JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);//支付成功
		model.addAttribute("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);//已出票
		model.addAttribute("TICKET_REFUND_FAIL", JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL);//退票失败
		return "/jp/order/order_detail.html";
	}

	/**
	 * 返回订单取消页面
	 * @return
	 */
	@RequestMapping(value = "/order/toCancel")
	public String toCancel(Model model, @RequestParam(value = "id", required = false) String id) {
		model.addAttribute("id", id);
		FlightOrderQO qo = new FlightOrderQO();
		qo.setId(id);
		qo.setOrderType(FlightOrder.ORDERTYPE_NORMAL);//正常订单
		FlightOrder jpOrder = flightOrderService.queryUnique(qo);
		model.addAttribute("jpOrder", jpOrder);
		return "/jp/order/order_cancel.html";
	}
	/**
	 * 取消订单
	 * @return
	 */
	@RequestMapping(value = "/order/cancel")
	@ResponseBody
	public String orderCancel(@RequestParam(value = "id", required = false) String id, 
			@RequestParam(value = "jpOrderPassengerNames", required = false) String[] jpOrderPassengerNames, 
			@RequestParam(value = "op", required = false) String op
			) {
		String success = "";
		String fail = "";
		if(StringUtils.isBlank(op)){
			success = "订单取消成功";
			fail = "订单取消失败";
		}
		String passengerNames="";
		if(jpOrderPassengerNames!=null){
			for(String psgName:jpOrderPassengerNames){
				passengerNames+=psgName+"^";
			}
			passengerNames=passengerNames.substring(0, passengerNames.length()-1);


			AuthUser authUser = super.getAuthUser();
			if (authUser != null) {

				FlightOrderQO qo = new FlightOrderQO();
				qo.setId(id);
				qo.setOrderType("1");
				FlightOrderDTO jpOrder = jpService.queryOrderDetail(qo);

				if (jpOrder != null) {
					CancelTicketGNCommand command = new CancelTicketGNCommand();
					command.setDealerOrderId(jpOrder.getOrderNO());
					command.setPassengerName(passengerNames);
					FlightOrderDTO cancelTicketGNDTO=null;
					try {
						cancelTicketGNDTO = jpService.cancelTicket(command);
					 } catch (Exception e) {
						 e.printStackTrace();
						 return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, fail, "", "");
					 }

					 if (cancelTicketGNDTO == null) {
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



	 }else{
	 	return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "旅客为空", "", "");
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
	/**
	 * 返回订单退票页面
	 * @return
	 */
	@RequestMapping(value = "/order/toRefund")
	public String toRefund(Model model, @RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "orderType", required = true) String orderType) {

		model.addAttribute("id", id);
		model.addAttribute("orderType", orderType);

		FlightOrderQO qo = new FlightOrderQO();
		qo.setId(id);
		qo.setOrderType(orderType);
		FlightOrder jpOrder = flightOrderService.queryUnique(qo);
		model.addAttribute("jpOrder", jpOrder);
		return "/jp/order/order_refund_back.html";
	}

	/**
	 * 退票订单
	 * @return
	 */
	@RequestMapping(value = "/order/refund")
	@ResponseBody
	public String orderRefund(@RequestParam(value = "id", required = false) String id, 
			@RequestParam(value = "orderType", required = true) String orderType,
			@RequestParam(value = "jpOrderPassengerNames", required = false) String[] jpOrderPassengerNames, 
			@RequestParam(value = "refundType", required = false) String refundType,
			@RequestParam(value = "refundMemo", required = false) String refundMemo,
			@RequestParam(value = "op", required = false) String op) {
		String success = "";
		String fail = "";
		if(StringUtils.isBlank(op)){
			success = "订单退票成功";
			fail = "订单退票失败";
		}
		String passengerNames="";
		String airIds="";
		if(jpOrderPassengerNames!=null){
			for(String psgName:jpOrderPassengerNames){
				String[] name_airId=psgName.split(",");
				passengerNames+=name_airId[0]+"^";
				airIds+=name_airId[1]+"^";
			}
			passengerNames=passengerNames.substring(0, passengerNames.length()-1);
			airIds=airIds.substring(0, airIds.length()-1);

			AuthUser authUser = super.getAuthUser();
			if (authUser != null) {

				FlightOrderQO qo = new FlightOrderQO();
				qo.setId(id);
				qo.setOrderType(orderType);
				FlightOrderDTO jpOrder = jpService.queryOrderDetail(qo);

				if (jpOrder != null) {
					RefundTicketGNCommand command = new RefundTicketGNCommand();
					command.setDealerOrderId(jpOrder.getOrderNO());
					command.setOrderType(orderType);//可能是退费中订单再次申请退费     也可能是原始订单申请退费
					command.setPassengerName(passengerNames);
					command.setAirId(airIds);
					command.setRefundType(refundType);
					command.setRefundMemo(refundMemo);
					RefundTicketGNDTO dto;
					try {
						dto = jpService.refundTicket(command);
					} catch (Exception e) {
						e.printStackTrace();
						HgLogger.getInstance().info("chenxy","退票时异常信息"+HgLogger.getStackTrace(e));
						return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, fail, "", "");
					}

					if (dto == null) {
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
		}else{
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "旅客为空", "", "");
		}

	}
}


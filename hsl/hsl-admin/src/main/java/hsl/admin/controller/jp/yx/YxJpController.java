package hsl.admin.controller.jp.yx;

import com.alibaba.fastjson.JSON;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.app.service.local.yxjp.YXJPOrderOperateLogLocalService;
import hsl.app.service.local.yxjp.YXJPOrderPayRecordLocalService;
import hsl.domain.model.yxjp.*;
import hsl.pojo.command.yxjp.order.ApplyRefundYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.CancelNoPayYXJPOrderCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.yxjp.YXJPOrderOperateLogQO;
import hsl.pojo.qo.yxjp.YXJPOrderPassengerQO;
import hsl.pojo.qo.yxjp.YXJPOrderPayRecordQO;
import hsl.pojo.qo.yxjp.YXJPOrderQO;
import hsl.pojo.util.HSLConstants;
import hsl.spi.inter.Coupon.CouponService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import slfx.jp.pojo.dto.order.Passenger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 易行机票Controller
 * Created by huanggg on 2015/12/4.
 */
@Controller
@RequestMapping(value="/yxjp")
public class YxJpController extends BaseController{

	private String                          		devName = "hgg";
	@Autowired
	private YXJPOrderLocalService 					orderLocalService;

	@Autowired
	private CouponService 							couponService;

	@Autowired
	private YXJPOrderOperateLogLocalService 		operateLogLocalService;

	@Autowired
	private YXJPOrderPayRecordLocalService 			orderPayRecordLocalService;


	/**
	 * 易行机票列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/order/list")
	public String list(Model model,Integer numPerPage,Integer pageNum,YXJPOrderQO qo,HttpServletRequest request){

		//设置订单列表查询条件QO
		setYXJPOrderQO(qo, request, model);
		
		Pagination pagination = new Pagination();
		qo.setLinkNameLike(true);
		qo.setCreateDateOrder(-1);
		qo.setFetchPassengers(true);
		pagination.setCondition(qo);
		pagination.setPageNo(pageNum == null ? 1 : pageNum);
		pagination.setPageSize(numPerPage == null ? 20 : numPerPage);

		pagination = orderLocalService.queryPagination(pagination);
		List<YXJPOrder> orders = (List<YXJPOrder>) pagination.getList();

		HgLogger.getInstance().info(devName,"易行机票列表,查询条件Qo:"+JSON.toJSONString(qo)+"pageNum:"+pageNum+",numPerPage:"+numPerPage);

		model.addAttribute("pagination",pagination);
		model.addAttribute("qo",qo);
		model.addAttribute("orders",orders);
		model.addAttribute("yxOrderStatusMap",HSLConstants.YXJPOrderPassengerTicket.yxOrderStatusMap);

		return "/jp/yx-order/list.html";
	}

	/**
	 * 机票详情
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/order/detail")
	public String detail(Model model,String orderNo){

		if(StringUtils.isNotBlank(orderNo)){

			HgLogger.getInstance().info(devName,"易行机票详，订单ID："+orderNo);
			//查询订单
			YXJPOrderQO qo = new YXJPOrderQO();
			qo.setOrderNo(orderNo);
			qo.setFetchPassengers(true);
			YXJPOrder order = orderLocalService.queryUnique(qo);
			model.addAttribute("order",order);
			model.addAttribute("yxOrderStatusMap",HSLConstants.YXJPOrderPassengerTicket.yxOrderStatusMap);

			if(order != null){
				//查询用户下单已使用的卡券总值
				HslCouponQO cqo = new HslCouponQO();
				cqo.setOrderId(order.getBaseInfo().getOrderNo());
				if(order.getPassengers().get(0).getTicket().getStatus() == HSLConstants.YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_SUCC){
					cqo.setUseEvent(true);
				}
				List<CouponDTO> couponDTOs = couponService.queryList(cqo);
				Double couponPrice = 0d ;
				if(couponDTOs!=null&&couponDTOs.size()>0){
					for(CouponDTO coupon : couponDTOs){
						try{
							couponPrice = couponPrice + coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
						}catch(NullPointerException e){
							HgLogger.getInstance().debug("hgg", "机票订单详细页面：卡券id为"+coupon.getId()+"的面值查不出来");
						}
					}
					model.addAttribute("couponDTOs",couponDTOs);
				}
				model.addAttribute("couponPrice", couponPrice);

				//查询操作日志
				List<YXJPOrderOperateLog> operateLogs = new ArrayList<YXJPOrderOperateLog>();
				YXJPOrderOperateLogQO operateLogQO = new YXJPOrderOperateLogQO();
				operateLogQO.setOprateDateOrder(1);
				operateLogQO.setFromOrderId(order.getId());
				operateLogs = operateLogLocalService.queryList(operateLogQO);
				model.addAttribute("operateLogs",operateLogs);

				//查询支付记录(只查询支付成功了的记录)
				YXJPOrderPayRecordQO orderPayRecordQO = new YXJPOrderPayRecordQO();
				orderPayRecordQO.setFromOrderId(order.getId());
				orderPayRecordQO.setPaySuccess(true);
				List<YXJPOrderPayRecord> payRecords = orderPayRecordLocalService.queryList(orderPayRecordQO);
				model.addAttribute("payRecords",payRecords);
			}

		}else{
			HgLogger.getInstance().error(devName, "易行机票详情，orderNo为空");
			return "/error/error.jsp";
		}
		return "/jp/yx-order/order-detail.html";
	}

	/**
	 * 跳转到取消订单页面
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value="/order/cancel")
	public String cancel(Model model,String orderNo){
		//订单状态为待支付的游客列表
		List<YXJPOrderPassenger> canCancelPr = new ArrayList<YXJPOrderPassenger>();

		//查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(orderNo);
		qo.setFetchPassengers(true);
		YXJPOrder order = orderLocalService.queryUnique(qo);
		if(order == null){
			return "";
		}

		model.addAttribute("id", order.getId());

		List<YXJPOrderPassenger> passengers = order.getPassengers();

		for (YXJPOrderPassenger passenger:passengers){
			//筛选游客订单状态为待支付的
			if(passenger.getTicket().getStatus() == HSLConstants.YXJPOrderPassengerTicket.STATUS_PAY_WAIT){
				canCancelPr.add(passenger);
			}
		}

		model.addAttribute("canCancelPrs",canCancelPr);

		return "/jp/yx-order/order-cancel.html";
	}

	/**
	 * 取消订单
	 * @param model
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/order/cancel-order")
	public String cancelOrder(Model model,CancelNoPayYXJPOrderCommand command){

		String status = DwzJsonResultUtil.STATUS_CODE_200;

		String message = "取消成功";
		try {

			command.setFromAdminId(getAuthUser().getId());
			HgLogger.getInstance().info(devName,"易行机票取消订单，command："+JSON.toJSONString(command));

			orderLocalService.cancelNoPayOrder(command);

		}catch (ShowMessageException e){
			message = e.getMessage();
			status = DwzJsonResultUtil.STATUS_CODE_200;
			HgLogger.getInstance().info(devName,"易行机票取消订单，发生异常："+message);
		}

		return DwzJsonResultUtil.createJsonString(status,message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,"yxjp");
	}

	/**
	 * 跳转到申请退废票页面
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value="/order/refund")
	public String refund(Model model,String orderNo){

		//订单状态为已出票的游客列表
		List<YXJPOrderPassenger> canRefundPrs = new ArrayList<YXJPOrderPassenger>();

		HgLogger.getInstance().info(devName,"易行机票跳转到申请废票页面，订单ID:"+orderNo);

		//查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(orderNo);
		qo.setFetchPassengers(true);
		YXJPOrder order = orderLocalService.queryUnique(qo);

		if(order == null){
			return "";
		}

		model.addAttribute("id", order.getId());
		List<YXJPOrderPassenger> passengers = order.getPassengers();

		List<Integer> canRefundStatus = Arrays.asList(YXJPOrderPassengerTicket.STATUS_TICKET_SUCC, YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL);

		for (YXJPOrderPassenger passenger:passengers){
			//筛选游客订单状态为已出票的
			if(canRefundStatus.contains(passenger.getTicket().getStatus())){
				canRefundPrs.add(passenger);
			}
		}

		model.addAttribute("canRefundPrs",canRefundPrs);


		return "/jp/yx-order/order-refund.html";
	}

	/**
	 * 退废票,退票
	 * @param model
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/order/refund-order")
	public String refundOrder(Model model,ApplyRefundYXJPOrderCommand command){

		String status = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "退票成功";
		try{

			HgLogger.getInstance().info(devName,"易行机票退废票,command："+JSON.toJSONString(command));

			command.setFromAdminId(getAuthUser().getId());
			orderLocalService.applyRefundOrder(command);

		}catch (ShowMessageException e){
			message = e.getMessage();
			status = DwzJsonResultUtil.STATUS_CODE_500;
			HgLogger.getInstance().info(devName, "易行机票退票，发生异常：" + message);
		}

		return DwzJsonResultUtil.createJsonString(status, message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "yxjp");
	}

	/**
	 * 设置订单列表查询条件QO
	 * @param qo
	 * @param request
	 * @param model
	 */
	private void setYXJPOrderQO(YXJPOrderQO qo,HttpServletRequest request,Model model){

		String name = request.getParameter("name");
		String status = request.getParameter("status");
		YXJPOrderPassengerQO pQo = new YXJPOrderPassengerQO();
		if(StringUtils.isNotBlank(name)){
			pQo.setName(name);
			pQo.setNameLike(true);
		}
		if(StringUtils.isNotBlank(status)){
			pQo.setStatus(Integer.parseInt(status));
		}

		qo.setPassengerQO(pQo);

		String createDateBegin = request.getParameter("createBegin");
		String createDateEnd = request.getParameter("createEnd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(StringUtils.isNotBlank(createDateBegin)) {
				Date beginDate = sdf.parse(createDateBegin);
				qo.setCreateDateBegin(beginDate);
				model.addAttribute("createDateBegin",createDateBegin);
			}
			if(StringUtils.isNotBlank(createDateEnd)){
				Date endDate = sdf.parse(createDateEnd);
				//传入日期为 2015-12-22 00：00:00 变成 2015-12-22 23:59:59
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.add(13, -1);
				cal.add(5,1);
				qo.setCreateDateEnd(cal.getTime());
				model.addAttribute("createDateEnd", createDateEnd);
			}
		} catch (ParseException e) {
			HgLogger.getInstance().error(devName,"设置订单列表查询条件QO时候发生异常，原因："+e.getMessage());
			e.printStackTrace();
		}
	}

}

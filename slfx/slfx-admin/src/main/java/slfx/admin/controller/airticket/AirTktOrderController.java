package slfx.admin.controller.airticket;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.domainevent.DomainEventDao;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;
import slfx.api.util.HttpUtil;
import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.ApplyRefundCommand;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderLogDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.pojo.system.OrderLogConstants;
import slfx.jp.qo.admin.JPOrderLogQO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.common.JpEnumConstants;
import slfx.jp.spi.inter.JPOrderLogService;
import slfx.jp.spi.inter.JPPlatformApplyRefundService;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.PlatformGetRefundActionTypeService;
import slfx.jp.spi.inter.RefundJPOrderService;
import slfx.jp.spi.inter.TimeoutCancelJPOrderService;
import slfx.jp.spi.inter.UpdateJPOrderStatusService;
import slfx.jp.spi.inter.supplier.SupplierService;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：admin机票管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年7月31日下午4:33:25
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/airtkt/order")
public class AirTktOrderController extends BaseController{
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	private PlatformGetRefundActionTypeService jpPlatformGetRefundActionTypeService;
	
	@Autowired
	private JPPlatformApplyRefundService jpPlatformApplyRefundService;

	@Autowired
	private RefundJPOrderService refundJPOrderService;
	
	@Autowired
	private JPOrderLogService jpOrderLogService;
	
	@Autowired
	private TimeoutCancelJPOrderService timeoutCancelJPOrderService;
	
	@Autowired
	private UpdateJPOrderStatusService updateJPOrderStatusService;
	
	@Autowired
	SupplierService jpSupplierService;
	@Autowired
	private DomainEventDao domainEventDao;
	
	/**
	 * 订单列表
	 * @param request
	 * @param model
	 * @returns
	 */
	@RequestMapping(value = "/list")
	public String queryOrderList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		List<SupplierDTO> supplierDtoList = jpSupplierService.queryList(new SupplierQO());
		model.addAttribute("supplierDtoList", supplierDtoList);
		
		
		if (null !=platformOrderQO && StringUtils.isBlank(platformOrderQO.getType())) {
			platformOrderQO.setType(JPOrderStatusConstant.COMMON_TYPE);
		}
		if (null != platformOrderQO && platformOrderQO.getTempStatus() != null) {
			platformOrderQO.setStatus(platformOrderQO.getTempStatus());
		}

		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 添加查询条件
		pagination.setCondition(platformOrderQO);

		pagination = jpPlatformOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		//model.addAttribute("STATUS_MAP", JPOrderStatus.JPORDER_STATUS_MAP);
		model.addAttribute("statusList", JPOrderStatusConstant.SLFX_JPORDER_STATUS_LIST);
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.SLFX_JPORDER_PAY_STATUS_LIST);
		model.addAttribute("platformOrderQO", platformOrderQO);
		model.addAttribute("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
		model.addAttribute("EXCEPTION_TYPE", JPOrderStatusConstant.EXCEPTION_TYPE);
		model.addAttribute("CURRENT_DATE", new Date());
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SLFX_TICKET_SUCC);
		model.addAttribute("PAY_WAIT", JPOrderStatusConstant.SLFX_PAY_WAIT);
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-订单管理-查询订单列表成功");
		return "/airticket/order/order_list.html";
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping(value="/detail")
	public String queryOrderDetail(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		//根据QO查询一个订单详情
		JPOrderDTO dto = jpPlatformOrderService.queryUnique(platformOrderQO);
		model.addAttribute("orderDetail", dto);
		
		model.addAttribute("STATUS_MAP", JPOrderStatusConstant.SLFX_JPORDER_STATUS_MAP);
		model.addAttribute("platformOrderQO", platformOrderQO);
		model.addAttribute("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
		model.addAttribute("EXCEPTION_TYPE", JPOrderStatusConstant.EXCEPTION_TYPE);
		model.addAttribute("PAY_WAIT", JPOrderStatusConstant.SLFX_PAY_WAIT);
		Date wastWorkTime = dto.getWastWorkTime();
		if (null == wastWorkTime || wastWorkTime.before(new Date())) {//废票在当前时间之前
			model.addAttribute("IS_WAST_WORK_TIME", "false"); //tui
		} else {
			model.addAttribute("IS_WAST_WORK_TIME", "true");//fei
		}
		JPOrderLogQO qo = new JPOrderLogQO();
		qo.setOrderId(dto.getId());
		List<JPOrderLogDTO> logs = jpOrderLogService.queryList(qo);
		model.addAttribute("LOGS", logs);
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SLFX_TICKET_SUCC);
		model.addAttribute("PAY_WAIT", JPOrderStatusConstant.SLFX_PAY_WAIT);
		
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-订单管理-查询订单详情成功");
		return "/airticket/order/order_detail.html";
	}
		
	
	/**
	 * 跳转到取消订单页面
	 */
	@RequestMapping(value="tocancel")
	public String toCancelOrder(
			HttpServletRequest request, 
			Model model,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="dealerOrderCode",required=false) String dealerOrderCode
			){
		
		model.addAttribute("platformOrderCode", id);
		model.addAttribute("dealerOrderCode", dealerOrderCode);
		//查询取消原因列表
		model.addAttribute("cancelReason", JpEnumConstants.OrderCancelReason.cancelReasonMap_2);
		
		return "/airticket/order/order_cancel.html";
	}
	
	/**
	 * 取消订单
	 */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public String adminCancelOrder(
			HttpServletRequest request, 
			Model model,
			@RequestParam(value="platformOrderCode",required=true) String platformOrderCode,
			@RequestParam(value="dealerOrderCode",required=false) String dealerOrderCode,
			@RequestParam(value="cancelRemark",required=true) String cancelRemark){
		
		AdminCancelOrderCommand adminCancelOrderCommand=new AdminCancelOrderCommand();
		adminCancelOrderCommand.setId(platformOrderCode);
		adminCancelOrderCommand.setOrderId(platformOrderCode);
		adminCancelOrderCommand.setCancel(true);
		adminCancelOrderCommand.setCancelRemark(cancelRemark);
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			adminCancelOrderCommand.setFromAdminId(au.getLoginName());
		}
		
		try {
			boolean bool = jpPlatformOrderService.adminCancelOrder(adminCancelOrderCommand);
			if(bool){
				HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-订单管理-取消订单成功");
				//同步给商城
				String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
				HttpUtil.reqForPost(notifyUrl, "dealerOrderCode="+dealerOrderCode+"&status="+JPOrderStatusConstant.SHOP_TICKET_CANCEL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_NO_PAY, 60000);
				
			}
			
		
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-订单管理-取消订单失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "order");
		}

		
		return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "order");
	}
	
	/**
	 * 查看订单取消原因
	 */
	@RequestMapping(value="/reason")
	public String queryCancelOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		return  "/airticket/order/order_cancel_view.html";
	}
	
	/**
	 * 返回退废票页面
	 * @param refundType 退废种类：退票（T），废票（F）
	 * @param platCode 三字编码（例如：001）见附件一、平台代码表
	 * @param id 平台订单号
	 * @return
	 */
	@RequestMapping(value = "/toRefundTicket")
	public String toRefundTicket(Model model, 
					 @RequestParam(value = "refundType", required = false) String refundType, 
					 @RequestParam(value = "platCode", required = false) String platCode, 
					 @RequestParam(value = "id", required = false) String id,
					 @RequestParam(value = "pnr", required = false) String pnr
					 ) {
		
		//查询退废票申请种类
		YGRefundActionTypesDTO dto = jpPlatformGetRefundActionTypeService.getAdminRefundActionType(platCode);
		if (dto != null) {
			List<YGRefundActionTypeDTO> actionTypeList = dto.getActionTypeList();
			//区分废票原因，退票原因 tandeng 20140922
			if(actionTypeList != null && actionTypeList.size() > 0){
				
				String tempRefundType = null;
				
				int i = actionTypeList.size() -1;
				
				for (; i >= 0; i--) {
					
					tempRefundType = actionTypeList.get(i).getRefundType().trim();
					
					if(tempRefundType.equalsIgnoreCase(refundType)){
						continue;
					}else{
						actionTypeList.remove(i);
					}
				}			
			}

			model.addAttribute("actionTypeList", actionTypeList);
		}
		model.addAttribute("id", id);
		//更具ID查询乘客信息
		//根据QO查询一个订单详情
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setId(id);
		JPOrderDTO orderDetail = jpPlatformOrderService.queryUnique(platformOrderQO);
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("pnr",pnr);
		
		if (refundType.equals("T")) {
			return "/airticket/order/order_refund_back.html";
		} else if (refundType.equals("F")) {
			return "/airticket/order/order_refund_discard.html";
		}
		
		return "";
		
	}
	
	/**
	 * 退废票处理
	 * @param id 平台订单号
	 * @param actionType 申请种类编号（根据平台不同，获取的种类也不同）
	 * @param refundType 退废种类：退票（T），废票（F）
	 * @param reason 退废票原因
	 * @return
	 */
	@RequestMapping(value = "/refundTicket")
	@ResponseBody
	public String refundTicket(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id, 
			@RequestParam(value = "pnr", required = false) String pnr, 
			@ModelAttribute YGApplyRefundCommand command) {
		
		//查询订单详情
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setId(id);
		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		
		//处理退废票逻辑
		if (jpOrderDTO != null) {
			command.setOrderNo(jpOrderDTO.getYgOrderNo());
			command.setCommandId(id);//供记录日志使用，是平台的订单id。
			//组装退票号
			String ticketNos = command.getTicketNos();
			if(ticketNos == null || "".equals(ticketNos)){
				return DwzJsonResultUtil.createJsonString("300", "请选择乘机人！", "closeCurrent", "order");
			}else{
				if(ticketNos.startsWith(",")){
					ticketNos = ticketNos.substring(1,ticketNos.length());
				}
				ticketNos = ticketNos.replaceAll(",","\\|");
			}
			command.setTicketNos(ticketNos);
			
			if (StringUtils.isNotBlank(jpOrderDTO.getFlightSnapshotJSON())) {
				try {
					SlfxFlightDTO flightDto = JSON.parse(jpOrderDTO.getFlightSnapshotJSON(),SlfxFlightDTO.class);
					command.setSegment(flightDto.getStartPort()+flightDto.getEndPort());
				} catch (com.alibaba.dubbo.common.json.ParseException e) {
					HgLogger.getInstance().error("tuhualiang", "AirTktOrderController->refundTicket->exception[退废票处理异常]:" + HgLogger.getStackTrace(e));
				}
			}

			//command.setRefundType(refundType_01);
			command.setNoticeUrl(SysProperties.getInstance().get("http_domain") + "/slfx/api/backOrDiscardTicket/notify");//退废票完成通知地址
			
			// 设置姓名
			Subject currentUser = SecurityUtils.getSubject();
			AuthUser au = (AuthUser) currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			if(au!=null){
				command.setFromAdminId(au.getLoginName());
			}
			
			try {
				//调用易购接口，删除pnr
				ABEDeletePnrCommand deleteCommand=new ABEDeletePnrCommand();
				deleteCommand.setCommandId(id);
				deleteCommand.setPnr(pnr);
				ABEDeletePnrDTO abeDeletePnrDTO = jpPlatformApplyRefundService.deletePnr(deleteCommand);
				HgLogger.getInstance().info("tuhualiang", "JPOrderLocalService->adminCancelOrder->删除pnr,dto="+JSONObject.toJSON(abeDeletePnrDTO));
				/*
				if(abeDeletePnrDTO != null && abeDeletePnrDTO.getStatus() != null ){
					
					if("Y".equalsIgnoreCase(abeDeletePnrDTO.getStatus().trim())){
						HgLogger.getInstance().info("tuhualiang", "JPOrderLocalService->adminCancelOrder->退废票删除pnr成功,pnr="+pnr+",orderId="+id);
					}else if("N".equalsIgnoreCase(abeDeletePnrDTO.getStatus().trim())){
						return DwzJsonResultUtil.createJsonString("300", "删除PNR失败，退废票失败", "closeCurrent", "order");						
					}
					
				}else{
					return DwzJsonResultUtil.createJsonString("300", "删除PNR异常，退废票失败", "closeCurrent", "order");
				}
				*/
			} catch (Exception e) {
				HgLogger.getInstance().error("tuhualiang", "JPOrderLocalService->adminCancelOrder->exception:" + HgLogger.getStackTrace(e));
				//return DwzJsonResultUtil.createJsonString("300", "删除PNR异常，退废票失败", "closeCurrent", "order");
			}
			AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			try{
				YGApplyRefundDTO ygApplyRefundDTO = jpPlatformApplyRefundService.applyRefund(command);
				if (ygApplyRefundDTO != null) {
					if ("0".equals(ygApplyRefundDTO.getErrorCode().trim())) {
						//更新平台订单状态
						ApplyRefundCommand applyRefundCommand = new ApplyRefundCommand();
						applyRefundCommand.setReason(command.getReason());
						applyRefundCommand.setOrderNo(id);
						applyRefundCommand.setRefundOrderNo(ygApplyRefundDTO.getRefundOrderNo());
						applyRefundCommand.setTicketNos(ticketNos);
						applyRefundCommand.setRefundType(command.getRefundType());						
						applyRefundCommand.setActionType(command.getActionType());						
						// 1. 更新现有订单状态
						jpPlatformOrderService.adminRefundOrder(applyRefundCommand);
						if(user != null){
							if ("F".equals(command.getRefundType())) {
								//添加操作日志
								CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
								logCommand.setLogName(OrderLogConstants.DESTROY_LOG_NAME);
								logCommand.setWorker(user.getLoginName());
								logCommand.setLogInfo(OrderLogConstants.DESTROY_FX_LOG_INFO);
								logCommand.setJpOrderId(jpOrderDTO.getId());
								jpOrderLogService.create(logCommand);
							} else {
								//添加操作日志
								CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
								logCommand.setLogName(OrderLogConstants.RETURN_LOG_NAME);
								logCommand.setWorker(user.getLoginName());
								logCommand.setLogInfo(OrderLogConstants.RETURN_FX_LOG_INFO);
								logCommand.setJpOrderId(jpOrderDTO.getId());
								jpOrderLogService.create(logCommand);
							}
						}
						return DwzJsonResultUtil.createJsonString("200", "退废票成功", "closeCurrent", "order");
					}
				}
			}catch(Exception e){
				HgLogger.getInstance().error("zhangka", "AirTktOrderController->refundTicket->exception:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", "调用接口退废票异常，退废票失败", "closeCurrent", "order");
			}
		}
		return DwzJsonResultUtil.createJsonString("300", "退废票失败", "closeCurrent", "order");
	}
	
	
	/**
	 * 对可退款的订单执行退款任务
	 * 
	 * @return
	 */
	@RequestMapping("/refundJPOrderTask")
	public void refundJPOrderTask(HttpServletRequest request,HttpServletResponse response){
		
		//对可退款的订单执行退款任务
		refundJPOrderService.refundJPOrderTask();
		
	}
	
	/**
	 * 定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
	 * 
	 * @return
	 */
	@RequestMapping("/timeoutCancelJPOrderTask")
	public void timeoutCancelJPOrderTask(HttpServletRequest request,HttpServletResponse response){
		
		//定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
		timeoutCancelJPOrderService.timeoutCancelJPOrderTask();
		
	}
	
	/**
	 * 定时同步易购订单状态
	 * 
	 * @return
	 */
	@RequestMapping("/updateJPOrderStatusTask")
	public void updateJPOrderStatusTask(HttpServletRequest request,HttpServletResponse response){
		
		//定时同步易购订单状态
		updateJPOrderStatusService.updateJPOrderStatusTask();
	}
}

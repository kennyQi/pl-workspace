package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.util.HttpUtil;
import slfx.jp.app.common.util.ArraysUtil;
import slfx.jp.app.dao.ABEOrderDAO;
import slfx.jp.app.dao.ComparePriceDAO;
import slfx.jp.app.dao.FlightPolicyDAO;
import slfx.jp.app.dao.FlightTicketDAO;
import slfx.jp.app.dao.JPOrderDAO;
import slfx.jp.app.dao.PassengerDAO;
import slfx.jp.app.dao.YGOrderDAO;
import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.ApplyRefundCommand;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.command.admin.jp.JPAskOrderTicketSpiCommand;
import slfx.jp.command.admin.jp.JPCancelTicketSpiCommand;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.ABELinkerInfoCommand;
import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.command.client.ABEOrderInfoCommand;
import slfx.jp.command.client.ABEPassengerCommand;
import slfx.jp.command.client.ABEPriceDetailCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.command.client.YGFlightCommand;
import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.domain.model.order.ABEOrder;
import slfx.jp.domain.model.order.ComparePrice;
import slfx.jp.domain.model.order.FlightPolicy;
import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.domain.model.order.JPOrderStatus;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.domain.model.order.YGOrder;
import slfx.jp.domain.model.order.YGOrderStatus;
import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.order.PlatformOrderDetailDTO;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEXmlRtPnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.pojo.system.OrderLogConstants;
import slfx.jp.pojo.system.PolicyConstants;
import slfx.jp.qo.admin.PassengerQO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.YGOrderQO;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.qo.admin.policy.PolicySnapshotQO;
import slfx.jp.qo.api.JPOrderSpiQO;
import slfx.jp.qo.client.PatFlightQO;
import slfx.jp.spi.common.JpEnumConstants;
import slfx.jp.spi.inter.JPPlatformApplyRefundService;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.WebFlightService;
import slfx.jp.spi.inter.dealer.DealerService;
import slfx.jp.spi.inter.policy.PolicyService;
import slfx.yg.open.inter.YGFlightService;
//import slfx.jp.domain.model.order.ABEPassangerInfo;
//import slfx.jp.domain.model.order.Linker;
//import slfx.jp.domain.model.order.PriceItem;
//import slfx.jp.spi.command.client.ABELinkerInfoCommand;
//import slfx.jp.spi.command.client.ABEPassengerCommand;
//import slfx.jp.spi.command.client.ABEPriceDetailCommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:42:43
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class JPOrderLocalService extends BaseServiceImpl<JPOrder, PlatformOrderQO, JPOrderDAO>{
	
	@Autowired
	private DealerService dealerService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private YGFlightService ygFlightService;
	@Autowired
	private WebFlightService webFlightService;
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private JPFlightTicketLocalService jpFlightTicketLocalService;
	@Autowired
	private JPPlatformApplyRefundService jpPlatformApplyRefundService;
	@Autowired
	private JPOrderDAO jpOrderDAO;
	@Autowired
	private JPOrderLogLocalService jpOrderLogLocalService;
	@Autowired
	private ABEOrderDAO abeOrderDAO;
	@Autowired
	private YGOrderDAO ygOrderDAO;
	@Autowired
	private ComparePriceDAO comparePriceDAO;
	@Autowired
	private FlightPolicyDAO flightPolicyDAO;
	@Autowired
	private FlightTicketDAO flightTicketDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Resource
	private PlatformAskOrderTicketLocalService platformAskOrderTicketLocalService;
	@Resource
	private FlightPolicyLocalService flightPolicyLocalService;
	@Resource
	private PassengerLocalService passengerlocalService;
	@Override
	protected JPOrderDAO getDao() {
		return jpOrderDAO;
	}
	
	/*public boolean cancelOrderNotify(JPOrderCommand command) {
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->cancelOrderNotify->[易购退废回调开始]"+JSON.toJSONString(command));
		boolean ret = false;
		
		if (command == null || StringUtils.isBlank(command.getYgOrderNo())) {
			return ret;
		}

		try {
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setYgOrderNo(command.getYgOrderNo());
			JPOrder jpOrder = this.queryUnique(qo);

			if (jpOrder != null) {
//				jpOrder.setOrderBackId(command.getId());
//				jpOrder.setYgRefundOrderNo(command.getYgOrderNo());
//				jpOrder.setRefundPlatformOrderNo(command.getSupplierOrderNo());
				jpOrder.setUserPayAmount(command.getPayAmount());//退废总金额
				if (command.getStatus() != null) {
					JPOrderStatus status = new JPOrderStatus();
					status.setStatus(command.getStatus());
					status.setPayStatus(jpOrder.getOrderStatus().getPayStatus());
					jpOrder.setOrderStatus(status);
				}

				getDao().update(jpOrder);
				ret = true;

				// 商城通知地址
				String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/refund/notify";
				// 通知商城更改订单状态notifyUrl
				// command.setNotifyUrl();
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				HttpUtil.reqForPost(notifyUrl, "flag=" + command.getFlag() + "&dealerOrderCode=" + command.getDealerOrderCode(), 60000);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->cancelOrderNotify->exception[退废票通知处理]:"+ HgLogger.getStackTrace(e));
		}
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","cancelOrderNotify",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->cancelOrderNotify->[易购退费回调结束]ret="+ret);
		return ret;
	}*/
	
	public boolean backOrDiscardTicketNotify(JPOrderCommand command){
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->backOrDiscardTicketNotify->[易购退废回调开始]"+JSON.toJSONString(command));  //没走到这一步
		boolean ret = false;
		
		if (command == null || StringUtils.isBlank(command.getYgOrderNo())) {
			return ret;
		}
		
		try {
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setYgOrderNo(command.getYgOrderNo());
			JPOrder jpOrder = this.queryUnique(qo);
			
			if (jpOrder != null) {
				//退废总金额
				jpOrder.setRefundPrice(command.getPayAmount());
				
				if (command.getStatus() != null) {
					JPOrderStatus status = new JPOrderStatus();
					status.setStatus(command.getStatus());  //退/废处理中
					//实际上只是用一个支付宝账号，直接修改支付状态（分销已退款）
					if("N".equalsIgnoreCase(command.getFlag())){
						status.setPayStatus(command.getPayStatus());
					}else{
						status.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//已退款	   平台应该显示已退款，但是现在显示已支付					
					}
					jpOrder.setOrderStatus(status);
				}
				
				getDao().update(jpOrder);
				ret = true;
				
				// 商城通知地址
				String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/returned/notify";
				//退废金额不为空且小于等于付款金额才通知
				if(null != jpOrder.getRefundPrice() && jpOrder.getRefundPrice() <= jpOrder.getSuppPrice()
						&& "Y".equalsIgnoreCase(command.getFlag())){
//				if(jpOrder.getUserPayAmount() < 0 && "Y".equalsIgnoreCase(command.getFlag())){
					if("F".equalsIgnoreCase(jpOrder.getRefundType())){
						//废票，通知商城 +1支付宝服务费
						HttpUtil.reqForPost(
								notifyUrl, 
								"flag=" + command.getFlag() 
								+ "&dealerOrderCode=" + jpOrder.getDealerOrderCode()
								+ "&status=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC
								+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC
								+ "&amount=" + Math.abs(jpOrder.getRefundPrice() + jpOrder.getDisparity() + 1), 
								60000);
						
					}else{
						//退票，通知商城
						HttpUtil.reqForPost(
								notifyUrl, 
								"flag=" + command.getFlag() 
								+ "&dealerOrderCode=" + jpOrder.getDealerOrderCode()
								+ "&status=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC     //退废成功
								+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC   //已回款
								+ "&amount=" + Math.abs(jpOrder.getRefundPrice() + jpOrder.getDisparity()), 
								60000);						
						
					}
				}else{
					//退废票退款未成功
					HttpUtil.reqForPost(
							notifyUrl, 
							"flag=" + command.getFlag() 
							+ "&dealerOrderCode=" + jpOrder.getDealerOrderCode()
							+ "&status=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL
							+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC
							+ "&amount=0", 
							60000);					
				}
				
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->backOrDiscardTicketNotify->exception[退废票通知处理]:"+ HgLogger.getStackTrace(e));
		}
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","backOrDiscardTicketNotify",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->backOrDiscardTicketNotify->[易购退费回调结束]ret="+ret);
		return ret;
	}
	
	
	public boolean updateOrderStatus(JPOrderCommand command) {
		HgLogger.getInstance().info("zhangka","JPOrderLocalService->updateOrderStatus->[修改订单状态开始]"+ JSON.toJSONString(command));
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYgOrderNo())) {
			return bool;// 空指针，直接返回
		}

		try {
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setYgOrderNo(command.getYgOrderNo());
			JPOrder jpOrder = this.queryUnique(qo);

			if (jpOrder != null) {
				JPOrderStatus orderStatus = new JPOrderStatus();
				orderStatus.setStatus(command.getStatus());
				orderStatus.setPayStatus(jpOrder.getOrderStatus().getPayStatus());
				jpOrder.updateOrderStatus(jpOrder,command,orderStatus);
				this.update(jpOrder);

				Set<Passenger> passengers = jpOrder.getPassangerList();
				String[] tktNos = command.getTktNo();
				if (passengers.size() == tktNos.length) {// 机票数量等于乘客数量

					Iterator<Passenger> iterator = passengers.iterator();
					int index = 0;// 机票数组下标

					while (iterator.hasNext()) {
						Passenger passenger = iterator.next();
						FlightTicket ticket = passenger.getTicket();
						ticket.setTicketNo(tktNos[index++]);
						jpFlightTicketLocalService.update(ticket);
					}
				}

				HgLogger.getInstance().info("zhangka","JPOrderLocalService->updateOrderStatus->[发送POST消息通知商城]");
				// 通知商城更改订单状态
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				HttpUtil.reqForPost(command.getNotifyUrl(),
						"msg=" + JSON.toJSONString(command), 60000);

				HgLogger.getInstance().info("zhangka","JPOrderLocalService->updateOrderStatus->[POST消息通知商城已完成]");

				bool = true;
				String ygStatus = YGOrderStatus.BB;
				if ("Y".equals(command.getFlag())) {
					ygStatus = YGOrderStatus.DZ;
				}
				// 更新易购订单状态
				YGOrderQO ygQo = new YGOrderQO();
				ygQo.setOrderNo(jpOrder.getYgOrderNo());
				this.updateYGOrder(ygQo, ygStatus);
				//添加操作日志
				CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
				logCommand.setLogName(OrderLogConstants.MESSAGE_LOG_NAME);
				logCommand.setWorker(OrderLogConstants.ZX_WORKER);
				logCommand.setLogInfo(OrderLogConstants.MESSAGE_SUCCESS_LOG_INFO);
				logCommand.setJpOrderId(jpOrder.getId());
				jpOrderLogLocalService.create(logCommand,jpOrder);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka","JPOrderLocalService->updateOrderStatus->exception:"+ HgLogger.getStackTrace(e));
		}
		
		
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","updateOrderStatus",JSON.toJSONString(command));
		domainEventRepository.save(event);
		HgLogger.getInstance().info("zhangka","JPOrderLocalService->updateOrderStatus->[修改订单状态结束]");
		return bool;
	}
	
	public boolean getTicketNoFailure(JPOrderCommand command) {
		HgLogger.getInstance().info("tandeng","JPOrderLocalService->getTicketNoFailure->[出票失败业务处理开始]"+ JSON.toJSONString(command));
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYgOrderNo())) {
			return bool;// 空指针，直接返回
		}
		
		try {
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setYgOrderNo(command.getYgOrderNo());
			JPOrder jpOrder = this.queryUnique(qo);
			
			if (jpOrder != null) {
				JPOrderStatus orderStatus = new JPOrderStatus();
				orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL));//出票失败
				orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//已退款
				jpOrder.setOrderStatus(orderStatus);
				
				//jpOrder.updateOrderStatus(jpOrder,command,orderStatus);
				this.update(jpOrder);
				
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->getTicketNoFailure->[发送POST消息通知商城]");
				// 通知商城更改订单状态
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				command.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
				command.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
				
				HttpUtil.reqForPost(command.getNotifyUrl(),"msg=" + JSON.toJSONString(command), 60000);
				
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->getTicketNoFailure->[POST消息通知商城已完成]");
				
				bool = true;
				
				// 更新易购订单状态
				YGOrderQO ygQo = new YGOrderQO();
				ygQo.setOrderNo(jpOrder.getYgOrderNo());
				this.updateYGOrder(ygQo, command.getYgOrderStatus());
				//添加操作日志
				CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
				logCommand.setLogName(OrderLogConstants.MESSAGE_LOG_NAME);
				logCommand.setWorker(OrderLogConstants.ZX_WORKER);
				logCommand.setLogInfo(OrderLogConstants.MESSAGE_FAIL_LOG_INFO);
				logCommand.setJpOrderId(jpOrder.getId());
				jpOrderLogLocalService.create(logCommand,jpOrder);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng","JPOrderLocalService->getTicketNoFailure->exception:"+ HgLogger.getStackTrace(e));
		}
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","getTicketNoFailure",JSON.toJSONString(command));
		domainEventRepository.save(event);
		HgLogger.getInstance().info("tandeng","JPOrderLocalService->getTicketNoFailure->[修改订单状态结束]");
		return bool;
	}
	
	
	public boolean shopRefundOrder(JPCancelTicketSpiCommand jpCancelTicketCommand) throws JPOrderException{
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->[退款开始]" + JSON.toJSONString(jpCancelTicketCommand));
		
		if (StringUtils.isBlank(jpCancelTicketCommand.getTicketNumbers())) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->[退废票]:票号为空");
			throw new JPOrderException(JPOrderException.ORDER_TICKET_NUMBER_NULL,"票号为空");
		}
		
		// 查询订单详情
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setOrderNo(jpCancelTicketCommand.getOrderId());//根据平台订单号查询详情
		
		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);	
		
		if (jpOrderDTO == null) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->[退废票]:查询不到对应订单,平台订单号orderNo:" + jpCancelTicketCommand.getOrderId());
			throw new JPOrderException(JPOrderException.ORDER_QUERY_NULL,"查询不到对应订单");
		}
		
		try {
			//调用易购接口，删除pnr
			ABEDeletePnrCommand deleteCommand = new ABEDeletePnrCommand();
			deleteCommand.setPnr(jpOrderDTO.getPnr());
			ABEDeletePnrDTO abeDeletePnrDTO = jpPlatformApplyRefundService.deletePnr(deleteCommand);
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->删除pnr,dto="+JSONObject.toJSON(abeDeletePnrDTO));
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->shopRefundOrder->exception:" + HgLogger.getStackTrace(e));
			throw new JPOrderException(JPOrderException.ORDER_DELETE_PNR_ERROR,"删除pnr异常");
		}
		

		// 处理退废票逻辑
		YGApplyRefundCommand command = new YGApplyRefundCommand();
		command.setOrderNo(jpOrderDTO.getYgOrderNo());//易购订单号5032418036108
		// 供记录日志使用，是平台的订单id。
		//command.setCommandId(jpCancelTicketCommand.getOrderId());
		command.setCommandId(jpOrderDTO.getId());//命令id平台订单id
		
		//组装票号
		String[] commandTktNos = jpCancelTicketCommand.getTicketNumbers().split(",");
		String ticketNos = ArraysUtil.toStringWithSlice(commandTktNos, "|");
		command.setTicketNos(ticketNos);

		if (StringUtils.isNotBlank(jpOrderDTO.getFlightSnapshotJSON())) {
			try {
				SlfxFlightDTO flightDto = com.alibaba.dubbo.common.json.JSON.parse(jpOrderDTO.getFlightSnapshotJSON(), SlfxFlightDTO.class);
				command.setSegment(flightDto.getStartPort() + flightDto.getEndPort());
			} catch (com.alibaba.dubbo.common.json.ParseException e) {
				HgLogger.getInstance().error("tandeng", "JPOrderLocalService->shopRefundOrder->exception:" + HgLogger.getStackTrace(e));
			}
		}
		command.setRefundType(jpCancelTicketCommand.getRefundType());
		command.setActionType(jpCancelTicketCommand.getActionType());
		command.setReason(jpCancelTicketCommand.getReason());
		command.setNoticeUrl(SysProperties.getInstance().get("http_domain") + "/slfx/api/backOrDiscardTicket/notify");// 退废票完成通知地址
		
		YGApplyRefundDTO ygApplyRefundDTO = jpPlatformApplyRefundService.applyRefund(command);

		if (ygApplyRefundDTO == null) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->[退废票]:易购退废票返回结果ygApplyRefundDTO==null");
			throw new JPOrderException(JPOrderException.ORDER_YG_CANCEL_REFUND_NULL,"易购退废票返回结果为空");
		}

		// 更新平台订单状态
		ApplyRefundCommand applyRefundCommand = new ApplyRefundCommand();
		applyRefundCommand.setReason(command.getReason());
		applyRefundCommand.setOrderNo(jpOrderDTO.getId());
		applyRefundCommand.setRefundOrderNo(ygApplyRefundDTO.getRefundOrderNo());
		applyRefundCommand.setTicketNos(ticketNos);
		applyRefundCommand.setRefundType(jpCancelTicketCommand.getRefundType());

		// 1. 更新现有订单状态
		this.adminRefundOrder(applyRefundCommand);
		
		// 2. 更新易购订单状态
		YGOrderQO ygQo = new YGOrderQO();
		ygQo.setOrderNo(jpOrderDTO.getYgOrderNo());
		this.updateYGOrder(ygQo, YGOrderStatus.SQ);
		JPOrder jpOrder = this.get(jpOrderDTO.getId());
		if(jpOrder != null){
			if ("F".equals(command.getRefundType())) {
				//添加操作日志
				CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
				logCommand.setLogName(OrderLogConstants.DESTROY_LOG_NAME);
				logCommand.setWorker(OrderLogConstants.ZX_WORKER);
				logCommand.setLogInfo(OrderLogConstants.DESTROY_ZX_LOG_INFO);
				logCommand.setJpOrderId(jpCancelTicketCommand.getOrderId());
				jpOrderLogLocalService.create(logCommand,jpOrder);
			} else {
				//添加操作日志
				CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
				logCommand.setLogName(OrderLogConstants.RETURN_LOG_NAME);
				logCommand.setWorker(OrderLogConstants.ZX_WORKER);
				logCommand.setLogInfo(OrderLogConstants.RETURN_ZX_LOG_INFO);
				logCommand.setJpOrderId(jpCancelTicketCommand.getOrderId());
				jpOrderLogLocalService.create(logCommand,jpOrder);
			}
		}
		
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","shopRefundOrder",JSON.toJSONString(jpCancelTicketCommand));
		domainEventRepository.save(event);
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopRefundOrder->[退款结束]");
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public Pagination shopQueryOrderList(JPOrderSpiQO jpOrderQO) throws JPOrderException {
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopQueryOrderList->[查询订单列表开始]" + JSON.toJSONString(jpOrderQO));
		if (jpOrderQO == null) {
			throw new JPOrderException(JPOrderException.ORDER_SHOP_QUERY_QO_NULL,"查询条件为空");
		}
		Pagination pagination = new Pagination();
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();

		if (jpOrderQO.getOrderId() != null) {
			platformOrderQO.setId(jpOrderQO.getOrderId());
		}
		if (jpOrderQO.getUserId() != null) {
			platformOrderQO.setUserId(jpOrderQO.getUserId());
		}
		int pageNo = jpOrderQO.getPageNo();
		int pageSize = jpOrderQO.getPageSize();

		pagination.setCondition(platformOrderQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);

		pagination = this.queryPagination(pagination);
		List<JPOrder> jPOrderList = (List<JPOrder>) pagination.getList();
		List<JPOrderDTO> jPOrderDTOList = new ArrayList<JPOrderDTO>();
		JPOrderDTO jpOrderDTO = null;
		for (JPOrder jpOrder : jPOrderList) {
			jpOrderDTO = new JPOrderDTO();
			if (jpOrder.getCreateDate() != null) {
				jpOrderDTO.setCreateDate(jpOrder.getCreateDate());
			}
			if (jpOrder.getDealerOrderCode() != null) {
				jpOrderDTO.setDealerOrderCode(jpOrder.getDealerOrderCode());
			}
			SlfxFlightDTO flightDTO = new SlfxFlightDTO();
			//String flightSnapshotJSON = jpOrder.getFlightSnapshotJSON();// 获取航班信息快照

			jpOrderDTO.setFlightDTO(flightDTO);

			Set<Passenger> passengerList = jpOrder.getPassangerList();

			List<FlightPassengerDTO> flightPassengerDTOList = new ArrayList<FlightPassengerDTO>();
			FlightPassengerDTO tempFlightPassangerDTO = null;
			for (Passenger passenger : passengerList) {

				tempFlightPassangerDTO = new FlightPassengerDTO();
				if (passenger.getId() != null) {
					tempFlightPassangerDTO.setId(passenger.getId());
				}
				if (passenger.getName() != null) {
					tempFlightPassangerDTO.setName(passenger.getName());
				}
				if (passenger.getPsgType() != null) {
					tempFlightPassangerDTO.setPassangerType(passenger
							.getPsgType());
				}
				if (passenger.getCardNo() != null) {
					tempFlightPassangerDTO.setCardNo(passenger.getCardNo());
				}
				if (passenger.getPsgId() != null) {
					tempFlightPassangerDTO.setPsgNo(passenger.getPsgId());
				}
				// 更具具体的需要调整module和DTO的对应关系
				flightPassengerDTOList.add(tempFlightPassangerDTO);

				tempFlightPassangerDTO = null;
			}
			jpOrderDTO.setPassangers(flightPassengerDTOList);
			// jpOrderDTO.setOrderNo(jpOrder.getOrderNo());
			if (jpOrder.getOrderStatus() != null) {
				jpOrderDTO.setStatus(jpOrder.getOrderStatus().getStatus());
			}
			jpOrderDTO.setSupplierPayType("");

			jPOrderDTOList.add(jpOrderDTO);
		}
		pagination.setList(jPOrderDTOList);
		
		
		HgLogger.getInstance().info("tandeng",
				"JPOrderLocalService->shopQueryOrderList->[查询订单列表结束]");
		return pagination;
	}
	
	
	public boolean shopCancelOrder(JPCancelTicketSpiCommand jpCancelTicketCommand) throws JPOrderException{
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopCancelOrder->[取消订单开始]" + JSON.toJSONString(jpCancelTicketCommand));
		
		boolean bool = false;
		if (jpCancelTicketCommand == null) {
			throw new JPOrderException(JPOrderException.ORDER_SHOP_CANCEL_PARAMS_NULL,"请求参数为空");
		}
		
		AdminCancelOrderCommand adminCancelOrderCommand = new AdminCancelOrderCommand();

		if (jpCancelTicketCommand.getOrderId() != null) {
			adminCancelOrderCommand.setOrderId(jpCancelTicketCommand.getOrderId());
		}

		if (jpCancelTicketCommand.getTicketNumbers() != null) {
			adminCancelOrderCommand.setTicketNumbers(jpCancelTicketCommand.getTicketNumbers());
		}

		bool = this.shopCancelOrder(adminCancelOrderCommand);
		
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","shopCancelOrder",JSON.toJSONString(jpCancelTicketCommand));
		domainEventRepository.save(event);
		JPOrder jpOrder = this.get(adminCancelOrderCommand.getOrderId());
		if(jpOrder != null){
			//添加操作日志
			CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
			logCommand.setLogName(OrderLogConstants.CANCEL_LOG_NAME);
			logCommand.setWorker(OrderLogConstants.ZX_WORKER);
			logCommand.setLogInfo(OrderLogConstants.CANCEL_ZX_LOG_INFO);
			logCommand.setJpOrderId(adminCancelOrderCommand.getOrderId());
			jpOrderLogLocalService.create(logCommand,jpOrder);
		}
		HgLogger.getInstance().info("tandeng",
				"JPOrderLocalService->shopCancelOrder->[取消订单结束]");
		return bool;
	}
	
	
	public boolean shopAskOrderTicket(JPAskOrderTicketSpiCommand command) throws JPOrderException {
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopAskOrderTicket->[分销平台请求出票开始]" + JSON.toJSONString(command));
		
		boolean bool = false;
		if (command == null) {
			throw new JPOrderException(JPOrderException.ORDER_ASK_TICKET_PARAMS_NULL, "请求参数为空");
		}

		// 根据商城订单编号查询易购订单编号
		String ygOrderId = null;
		String orderNo = command.getOrderId();
		JPOrder jpOrder = null;
		try {
			PlatformOrderQO platformOrderQO = new PlatformOrderQO();
			platformOrderQO.setDealerOrderCode(orderNo);
			jpOrder = this.queryJPOrderUnique(platformOrderQO);
			
			if (jpOrder == null) {
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->shopAskOrderTicket->jpOrder[查询不到订单], orderNo=" + orderNo);
				throw new JPOrderException(JPOrderException.ORDER_QUERY_NULL, "查询不到订单");
			}
			
			ygOrderId = jpOrder.getYgOrderNo();
		} catch (Exception e) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopAskOrderTicket->[分销平台根据分销平台订单号查询易购订单号失败],orderNo=" + orderNo);
			throw new JPOrderException(JPOrderException.ORDER_QUERY_YGORDER_BY_ORDERID_ERROR, "分销平台根据分销平台订单号查询易购订单号失败");
		}
		
		YGAskOrderTicketCommand ygAskOrderTicketCommand = new YGAskOrderTicketCommand();

		// 易购订单号
		ygAskOrderTicketCommand.setPayOrderId(command.getPayOrderId());
		ygAskOrderTicketCommand.setOrderNo(ygOrderId);
		ygAskOrderTicketCommand.setPayType(command.getPayWay());

		// 设置出票完成通知地址
		ygAskOrderTicketCommand.setNotifyUrl(SysProperties.getInstance().get("http_domain") + "/slfx/api/ticket/notify");
		ygAskOrderTicketCommand.setPayReturnUrl(SysProperties.getInstance().get("http_domain") + "/slfx/api/ticket/notify");

		//平台数据库查询该订单的成库列表
		bool = platformAskOrderTicketLocalService.askOrderTicket(ygAskOrderTicketCommand);


		JPOrderStatus orderStatus ;

		// 如果请求出票成功，更新易购订单状态和平台订单状态
		if (bool) {
			YGOrderQO qo = new YGOrderQO();
			qo.setOrderNo(ygOrderId);
			this.updateYGOrder(qo, YGOrderStatus.RR);
			
			orderStatus = new JPOrderStatus(
					Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_DEALING),
					Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_PAY_SUCC));
			
			jpOrder.shopAskOrderTicketSucc(jpOrder,command,orderStatus);
			
			getDao().update(jpOrder);
			
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","shopAskOrderTicketSucc",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} else {
			// 如果请求出票失败，订单状态为：扣款失败
			orderStatus = new JPOrderStatus(
					Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL),
					Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));
			
			jpOrder.shopAskOrderTicketFail(jpOrder,command,orderStatus);
			getDao().update(jpOrder);
			
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","shopAskOrderTicketFail",JSON.toJSONString(command));
			domainEventRepository.save(event);
			
			//同步通知商城
			String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
			HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() 
					+ "&status=" + JPOrderStatusConstant.SHOP_TICKET_FAIL
					+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC, 60000);
		} 
		//添加操作日志
		CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
		logCommand.setLogName(OrderLogConstants.ASK_ORDER_TICKET_LOG_NAME);
		logCommand.setWorker(OrderLogConstants.ZX_WORKER);
		logCommand.setLogInfo(OrderLogConstants.ASK_ORDER_TICKET_LOG_INFO);
		logCommand.setJpOrderId(command.getOrderId());
		jpOrderLogLocalService.create(logCommand,jpOrder);
		HgLogger.getInstance().info("tandeng",
				"JPOrderLocalService->shopAskOrderTicket->[分销平台请求出票结束]");
		return bool;
		
	}
	
	
	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand) {
		HgLogger.getInstance().info("tandeng","JPOrderLocalService->saveErrorJPOrder->[保存异常订单开始]"+ JSON.toJSONString(jpOrderCommand));
		
		if (jpOrderCommand == null) {
			return false;
		}
		
		/*PlatformOrderQO qo = new PlatformOrderQO();
		qo.setId(jpOrderCommand.getId());
		// 查询机票订单
		JPOrder JPOrder = this.queryUnique(qo);

		// 设置异常订单的调整属性
		JPOrder.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
		JPOrder.setAdjustAmount(jpOrderCommand.getAdjustAmount());
		JPOrder.setVoucherPicture(jpOrderCommand.getVoucherPicture());
		JPOrder.setAdjustReason(jpOrderCommand.getAdjustReason());
		this.update(JPOrder);*/

		//将退废单的状态也修改成异常订单
		PlatformOrderQO qo2 = new PlatformOrderQO();
		qo2.setOrderNo(jpOrderCommand.getOrderNo());
		// 查询机票订单
		List<JPOrder> JPOrderTempList = this.queryList(qo2);
		for (JPOrder o : JPOrderTempList) {
			// 设置异常订单的调整属性
			o.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
			o.setAdjustAmount(jpOrderCommand.getAdjustAmount());
			o.setVoucherPicture(jpOrderCommand.getVoucherPicture());
			o.setAdjustReason(jpOrderCommand.getAdjustReason());
			this.update(o);
		}
		
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","saveErrorJPOrder",JSON.toJSONString(jpOrderCommand));
		domainEventRepository.save(event);
		
		HgLogger.getInstance().info("tandeng","JPOrderLocalService->saveErrorJPOrder->[保存异常订单结束]");
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pagination queryOrderList(Pagination pagination) {
		HgLogger.getInstance().info("tandeng",
				"JPOrderLocalService->queryOrderList->[查询订单列表开始]");
		PassengerQO pqo = ((PlatformOrderQO) pagination.getCondition())
				.getPassengerQO();
		List<Passenger> pList = null;// 乘客列表
		// 条件查询乘客
		if (pqo != null) {
			pList = passengerlocalService.queryList(pqo);
		}

		pagination = this.queryPagination(pagination);

		if (pList != null && pagination.getList() != null) {// 乘客列表不为空 匹配满足条件的订单
			List<JPOrder> newJPOrderList = new ArrayList<JPOrder>();
			for (JPOrder jp : (List<JPOrder>) pagination.getList()) {
				Set<Passenger> pSet = new HashSet<Passenger>();
				boolean flag = false;
				for (Passenger p : pList) {
					if (jp.getId().equals(p.getJpOrder().getId())) {
						pSet.add(p);
						flag = true;
					}
				}
				if (flag) {
					newJPOrderList.add(jp);
				}
			}
			pagination.setList(newJPOrderList);
		}

		// JPOrder转JPOrderDTO
		List<JPOrderDTO> list = new ArrayList<JPOrderDTO>();
		if (pagination.getList() != null && pagination.getList().size() > 0) {

			for (JPOrder o : (List<JPOrder>) pagination.getList()) {
				JPOrderDTO dto = new JPOrderDTO();
				dto.setAbeOrderId(o.getAbeOrderId());
				dto.setCommAmount(o.getCommAmount());
				dto.setCommRate(o.getCommRate());
				dto.setCreateDate(o.getCreateDate());
				dto.setDealerOrderCode(o.getDealerOrderCode());

				SlfxFlightDTO fd = JSONObject.parseObject(
						o.getFlightSnapshotJSON(), SlfxFlightDTO.class);
				dto.setFlightDTO(fd);

				// dto.setOrderNo(o.getOrderNo());
				dto.setStatus(o.getOrderStatus().getStatus());
				dto.setUserPayAmount(o.getUserPayAmount());
				dto.setSupplierOrderNo(o.getSupplierOrderNo());
				dto.setTktAmount(o.getTktPrice());
				dto.setYgOrderNo(o.getYgOrderNo());
				dto.setPlatCode(o.getPlatCode());
				dto.setPnr(o.getPnr());
				// dto.setTktNo(tktNo); 暂无
				// dto.setPnr(o.getpnr);暂无
				// Passenger转FlightPassengerDTO
				if (o.getPassangerList() != null
						&& o.getPassangerList().size() > 0) {
					List<FlightPassengerDTO> pl = new ArrayList<FlightPassengerDTO>();
					Iterator it = o.getPassangerList().iterator();
					while (it.hasNext()) {
						Passenger p = (Passenger) it.next();
						FlightPassengerDTO pdto = new FlightPassengerDTO();
						pdto.setBirthday(p.getBirthDay());
						pdto.setCardNo(p.getCardNo());
						pdto.setCardType(p.getCardType());
						// pdto.setCountry(p.getCountry());
						pdto.setIdentityType(p.getIdentityType());
						pdto.setInsueFee(p.getInsueFee());
						pdto.setInsueSum(p.getInsueSum());
						pdto.setPsgNo(p.getPsgId());
						pdto.setPassangerType(p.getPsgType());
						pdto.setName(p.getName());
						pdto.setMobile(p.getMobilePhone());
						pdto.setFare(p.getFare());
						pdto.setSalePrice(p.getSalePrice());
						pdto.setTaxAmount(p.getTaxAmount());
						pl.add(pdto);
					}
					dto.setPassangers(pl);
				}
				list.add(dto);
			}
		}
		pagination.setList(list);
		HgLogger.getInstance().info("tandeng",
				"JPOrderLocalService->queryOrderList->[查询订单列表结束]");
		return pagination;
	}
	
	
	public PlatformOrderDetailDTO queryOrderDetail(PlatformOrderQO platformOrderQO){
		// 查询平台订单
				JPOrder jpOrder = getDao().get(platformOrderQO.getId());
				// 查询日志
				SlfxFlightDTO fd = null;

				PlatformOrderDetailDTO orderDetail = new PlatformOrderDetailDTO();

				if (jpOrder != null) {
					fd = JSONObject.parseObject(jpOrder.getFlightSnapshotJSON(),
							SlfxFlightDTO.class);
					JPOrderDTO oDto = new JPOrderDTO();
					/*
					 * oDto.setAgencyName(agencyName); oDto.setAgencyTel(agencyTel);
					 * oDto.setOrderSource(orderSource);
					 * oDto.setSupplierPayType(supplierPayType); oDto.setTktNo(tktNo);
					 * oDto.setAbeOrderId(abeOrder.getId());
					 */

					oDto.setCommAmount(jpOrder.getCommAmount());
					oDto.setCommRate(jpOrder.getCommRate());
					oDto.setCreateDate(jpOrder.getCreateDate());
					oDto.setDealerOrderCode(jpOrder.getDealerOrderCode());
					oDto.setFlightDTO(fd);
					oDto.setClassNo(fd.getClassCode());
					oDto.setFlightSnapshotJSON(jpOrder.getFlightSnapshotJSON());
					// oDto.setOrderNo(jpOrder.getOrderNo());
					oDto.setUserPayAmount(jpOrder.getUserPayAmount());
					oDto.setPlatCode(jpOrder.getPlatCode());
					oDto.setPnr(jpOrder.getPnr());
					oDto.setStatus(jpOrder.getOrderStatus().getStatus());
					oDto.setSupplierOrderNo(jpOrder.getSupplierOrderNo());
					oDto.setTktAmount(jpOrder.getTktPrice());
					oDto.setTktTax(jpOrder.getTktTax());
					oDto.setYgOrderNo(jpOrder.getYgOrderNo());
					/*
					PassengerQO pqo = new PassengerQO();
					pqo.setJpOrderId(jpOrder.getId());

					List<FlightPassengerDTO> pList2 = new ArrayList<FlightPassengerDTO>();
					List<Passenger> pList = localPassengerService.queryList(pqo);
					for (Passenger p : pList) {
						FlightPassengerDTO pDto = new FlightPassengerDTO();
						pDto.setBirthday(p.getBirthDay());
						pDto.setCardNo(p.getCardNo());
						pDto.setCardType(p.getCardType());
						// pDto.setCountry(p.getCountry());
						pDto.setIdentityType(p.getIdentityType());
						pDto.setInsueFee(p.getInsueFee());
						pDto.setInsueSum(p.getInsueSum());
						pDto.setPsgNo(p.getPsgId());
						pDto.setPassangerType(p.getPsgType());
						pDto.setName(p.getName());
						pDto.setMobile(p.getMobilePhone());
						pDto.setFare(p.getFare());
						pDto.setSalePrice(p.getSalePrice());
						pDto.setTaxAmount(p.getTaxAmount());
						pList2.add(pDto);
					}
					oDto.setPassangers(pList2);
 					*/
					orderDetail.setOrder(oDto);
					orderDetail.setFlight(fd);
				} else {
					orderDetail.setOrder(null);
				}

				return orderDetail;
		
	}
	
	
	//abe下单入库的实体
	//private ABEOrder abeOrder;
	//易购下单入库的实体
	//private YGOrder ygOrder;
	
	@SuppressWarnings("deprecation")
	public JPOrderDTO shopCreateOrder(JPOrderCreateSpiCommand jpOrderCreateSpiCommand) throws JPOrderException{
		HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建]:"+ JSON.toJSONString(jpOrderCreateSpiCommand));
		long t1 = System.currentTimeMillis();
		if (jpOrderCreateSpiCommand == null) {
			HgLogger.getInstance().info("tandeng", "下单参数传递错误");
			throw new JPOrderException(JPOrderException.ORDER_PARAMS_NULL,"下单参数传递错误");
		}

		//1.判断订单是否已经存在
		try {
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setFlightNo(jpOrderCreateSpiCommand.getFlightNo());
			String dateStr = jpOrderCreateSpiCommand.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			date.setDate(date.getDate() + 1);
			dateStr = sdf.format(date);
			qo.setStartDepartureTime(jpOrderCreateSpiCommand.getDate());
			qo.setEndDepartureTime(dateStr);
			List<FlightPassengerDTO> passangerDTOs = jpOrderCreateSpiCommand.getPassangers();
			if (passangerDTOs == null || passangerDTOs.size() == 0) {
				HgLogger.getInstance().info("tandeng","JPOrderLocalService-->下单参数缺少乘机人,dealerOrderCode="+ jpOrderCreateSpiCommand.getDealerOrderId());
				throw new JPOrderException(JPOrderException.ORDER_PARAMS_NO_PASSANGER,"参数缺少乘机人");
			} else {
				FlightPassengerDTO passengerDTO = passangerDTOs.get(0);
				PassengerQO passengerQO = new PassengerQO(passengerDTO);
				qo.setPassengerQO(passengerQO);
			}

			JPOrder isExsitsOrder = this.queryUnique(qo);  //查询订单
			if (isExsitsOrder != null
					&& String.valueOf(isExsitsOrder.getOrderStatus().getStatus()).equals(JPOrderStatusConstant.SLFX_PAY_WAIT)) {
				HgLogger.getInstance().info("tandeng","JPOrderLocalService-->dateStr=" + qo.getEndDepartureTime()+ ",flightNo="+ jpOrderCreateSpiCommand.getFlightNo());
				throw new JPOrderException(JPOrderException.ORDER_IS_EXSITS,"订单已存在");
			}
		} catch (ParseException e1) {
			HgLogger.getInstance().info("tandeng","[同一日期，同一航班，同一乘机人身份证验证异常]" + HgLogger.getStackTrace(e1));
			throw new JPOrderException(JPOrderException.ORDER_PASSANGER_CHECK_FAIL,"乘机人身份证验证异常");
		}

		String classCode = jpOrderCreateSpiCommand.getClassCode();
		try {
			//2.转换成平台command
			//SPIJPOrderCreateCommand jPOrderCreateCommand = new SPIJPOrderCreateCommand();
			//BeanUtils.copyProperties(jpOrderCreateSpiCommand,jPOrderCreateCommand);
			
			jpOrderCreateSpiCommand.setStartDateStr(jpOrderCreateSpiCommand.getDate());
			jpOrderCreateSpiCommand.setClassCode(jpOrderCreateSpiCommand.getClassCode());
			//3.通过 flightNo,yyyyMMdd,获取航班信息(FlightDTO的返回格式)
			YGFlightDTO ygFlightDto = this.queryCacheFlightInfo(jpOrderCreateSpiCommand.getFlightNo(),jpOrderCreateSpiCommand.getStartDateStr());
			ygFlightDto.setClassCode(classCode);
			
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->YGFlightDTO[机票订单创建-查询航班]:"+ JSON.toJSONString(ygFlightDto));
			
			//4.将易购返回的航班DTO 转换为平台航班DTO
			SlfxFlightDTO flightDto = this.convertYGFlightDTOToFlightDTO(ygFlightDto);
			flightDto.setClassCode(classCode);

			long t4 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->从缓存查询航班信息=t4=====" + (t4 - t1));
			//5.报价接口
			PatFlightQO patQo = new PatFlightQO(flightDto);
			patQo.setClassCode(classCode);
			HashMap<String, ABEPatFlightDTO> mps = null;
			try {
				mps = webFlightService.patByFlights(patQo);
				if (mps == null || mps.size() <= 0) {
					HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-查询报价失败]:"+ JSON.toJSONString(mps));
					throw new JPOrderException(JPOrderException.ORDER_QUERY_PAT_ERROR,"查询报价失败");
				}
			} catch (Exception e) {
				HgLogger.getInstance().error("tandeng","JPOrderLocalService->shopCreateOrder->[调用报价接口获取黑屏价格失败]:"+ HgLogger.getStackTrace(e));
				throw new JPOrderException(JPOrderException.ORDER_USE_PAT_INTERFACE_ERROR,"调用报价接口失败");
			}
			long t5 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->调用报价接口，获取运价结果=t5====="+ (t5 - t4));
			//6.ABE下单，获取真实PNR
			ABEOrderFlightDTO abeOrderFlightDTO = null;
			String abeOderId = null;
			ABEOrderFlightCommand aBEOrderFlightCommand = this.collectABECommandParam(flightDto,mps,jpOrderCreateSpiCommand);

			if (aBEOrderFlightCommand == null) {
				HgLogger.getInstance().info("tandeng","这个" + flightDto.getFlightNo() + "-"+ flightDto.getStartDate() + "航班没有这个"+ classCode + "仓位。");
				throw new JPOrderException(JPOrderException.ORDER_ABE_FLIGHT_NULL,"该航班舱位有误");
			}

			try {
				abeOrderFlightDTO = this.abeOrderFlight(aBEOrderFlightCommand);
				if (abeOrderFlightDTO == null || abeOrderFlightDTO.getPnr() == null
						|| "".equals(abeOrderFlightDTO.getPnr())) {
					HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-ABE下单异常]:"+ JSON.toJSONString(aBEOrderFlightCommand));
					throw new JPOrderException(JPOrderException.ORDER_USE_ABE_FLIGHT_ERROR,"ABE下单异常");
				}
				abeOderId = abeOrderFlightDTO.getId();
			} catch (Exception e) {
				HgLogger.getInstance().error("tandeng", "JPOrderLocalService->shopCreateOrder->根据报价结果获取真实pnr失败" + HgLogger.getStackTrace(e));
				throw new JPOrderException(JPOrderException.ORDER_PNR_ERROR,"根据报价结果获取真实pnr失败");
			}
			long t6 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->ABE下单获取正式PNR=t6====="+ (t6 - t5));
			//7.根据真实pnr获取pnrtext和bigPnr
			String pnr = "";
			String pnrText = "";
			// String patText="";
			String bigPnr = "";
			try {
				pnr = abeOrderFlightDTO.getPnr();
				ABEXmlRtPnrDTO abeXmlRtPnrDTO = this.xmlRtPnr(pnr);
				pnrText = abeXmlRtPnrDTO.getHostText();
				bigPnr = abeXmlRtPnrDTO.getPnrNo();
			} catch (Exception e) {
				HgLogger.getInstance().error("tandeng","JPOrderLocalService->shopCreateOrder->根据真实pnr获取pnrText失败" + HgLogger.getStackTrace(e));
				throw new JPOrderException(JPOrderException.ORDER_PNRTEXT_ERROR,"根据真实pnr获取pnrText失败");
			}
			long t7 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->根据PNR获取PNRTEXT和大编码=t7====="+ (t7 - t6));
			//8.易购下单
			// 查询比价快照
			PolicySnapshotQO policySnapshotQo = new PolicySnapshotQO(jpOrderCreateSpiCommand);
			JPPlatformPolicySnapshot jpPlatformPolicySnapshot = null;
			String jpPlatformPolicySnapshotId = null;
			try {
				jpPlatformPolicySnapshot = flightPolicyLocalService.queryUnique(policySnapshotQo);
				jpPlatformPolicySnapshotId = jpPlatformPolicySnapshot.getId();
			} catch (Exception e) {
				HgLogger.getInstance().error("tandeng", "获取比较政策失败" + HgLogger.getStackTrace(e));
				throw new JPOrderException(JPOrderException.ORDER_FLIGHT_POLICY_ERROR,"获取比较政策失败");
			}
			YGFlightOrderDTO yGFlightOrderDTO = null;
			try {
				// flightDto.setPolicyRemark(jpPlatformPolicySnapshot.getRemark());
				YGOrderCommand yGOrderCommand = this.collectionYGOrderCreateParam(pnr, 
						pnrText, 
						bigPnr,
						classCode, 
						jpPlatformPolicySnapshot, 
						flightDto,
						jpOrderCreateSpiCommand);
				// 持久化接口返回数据
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-易购YGOrderCommand]:"+ JSON.toJSONString(yGOrderCommand));
				yGFlightOrderDTO = this.ygOrderByPnr(yGOrderCommand);
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-易购YGFlightOrderDTO]:"+ JSON.toJSONString(yGFlightOrderDTO));

				if (yGFlightOrderDTO == null
						|| yGFlightOrderDTO.getBalanceMoney() == 0
						|| yGFlightOrderDTO.getFare() == 0
						|| yGFlightOrderDTO.getPsgCount() == 0) {
					HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-易购下单异常]:"+ JSON.toJSONString(yGOrderCommand));
					throw new JPOrderException(JPOrderException.ORDER_USE_YG_FLIGHT_ERROR,"易购下单异常");
				}
			} catch (Exception e) {
				HgLogger.getInstance().error("tandeng", "JPOrderLocalService->shopCreateOrder->易购下单失败" + HgLogger.getStackTrace(e));
				throw new JPOrderException(JPOrderException.ORDER_USE_YG_FLIGHT_ERROR,"易购下单异常");
			}
			long t8 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->-采购接口下单=t8====="+ (t8 - t7));
			//9.商旅分销平台加价政策
			PolicyQO policyQO = new PolicyQO();
			policyQO.setSuppId(jpPlatformPolicySnapshot.getPlatCode());// platCode
			policyQO.setDealerId(jpPlatformPolicySnapshot.getDealerCode());// dealerCode
			policyQO.setCurrentTime(new Date());
			policyQO.setStatus(PolicyConstants.EFFECT);// 价格政策已经生效
			policyQO.setSortCreateTime(true); // 取最新的价格政策
			PolicyDTO policyDTO = policyService.getEffectPolicy(policyQO);
			//10.生成入库平台订单
			jpOrderCreateSpiCommand.setClassCode(classCode);
			jpOrderCreateSpiCommand.setAbeOrderId(abeOderId);
			jpOrderCreateSpiCommand.setComparePocilyId(jpPlatformPolicySnapshotId);
			DealerQO dealerQO = new DealerQO();
			dealerQO.setCode(jpOrderCreateSpiCommand.getFromClientKey());
			DealerDTO dealerDTO = dealerService.queryUnique(dealerQO);
			JPOrderStatus status = new JPOrderStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_PAY_WAIT),Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_NO_PAY));
			JPOrder jpOrder = new JPOrder(jpOrderCreateSpiCommand,yGFlightOrderDTO,policyDTO,flightDto,jpPlatformPolicySnapshot,dealerDTO,status);

			//11.持久化下单成功后，返回DTO
			try {
				JPOrderDTO jpOrderDTO = this.saveJPOrderInfo(jpOrder);
				jpOrderDTO.setFlightDTO(flightDto);
				long t11 = System.currentTimeMillis();
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->创建分销平台订单=t11=" + (t11 - t8));
				return jpOrderDTO;
			} catch (Exception e) {
				
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->[机票订单创建-平台下单异常]:"+ JSON.toJSONString(jpOrder));
				long t12 = System.currentTimeMillis();
				HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->分销平台创建订单总耗时=t12=" + (t12 - t8));
				throw new JPOrderException(JPOrderException.ORDER_CREATE_ERROR,"平台下单异常");
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng","JPOrderLocalService->shopCreateOrder->"+ HgLogger.getStackTrace(e));
		} finally {
			long t13 = System.currentTimeMillis();
			HgLogger.getInstance().info("tandeng","JPOrderLocalService->shopCreateOrder->完成整个下单流程，返回dto给商城=t13====="+ (t13 - t1));
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","shopCreateOrder",JSON.toJSONString(jpOrderCreateSpiCommand));
			domainEventRepository.save(event);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @方法功能说明：收集易购订单参数
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月30日下午7:50:01
	 * @修改内容：
	 * @参数：@return
	 * @return:YGOrderCommand
	 * @throws
	 */
	private YGOrderCommand collectionYGOrderCreateParam(String pnr,
			String pnrText, String bigPnr, String classCode,
			JPPlatformPolicySnapshot jpPlatformPolicySnapshot,
			SlfxFlightDTO flightDto,
			JPOrderCreateSpiCommand jPOrderCreateCommand) {

		YGOrderCommand yGOrderCommand = new YGOrderCommand();
		yGOrderCommand.setPnr(pnr);
		yGOrderCommand.setClassCode(classCode);
		yGOrderCommand.setPnrText(pnrText);

		yGOrderCommand.setBigPNR(bigPnr);
		yGOrderCommand.setPolicyId(jpPlatformPolicySnapshot.getPolicyId());
		yGOrderCommand
				.setPolicyCommsion(jpPlatformPolicySnapshot.getCommRate());
		// 001
		yGOrderCommand.setPlatCode(jpPlatformPolicySnapshot.getPlatCode());
		yGOrderCommand.setPlatformType(jpPlatformPolicySnapshot
				.getPlatformType());
		yGOrderCommand.setVip(jpPlatformPolicySnapshot.getIsVip());
		yGOrderCommand.setAccountLevel(jpPlatformPolicySnapshot.getIsVip());
		yGOrderCommand.setRemark(jpPlatformPolicySnapshot.getRemark());
		yGOrderCommand.setWorkTime(jpPlatformPolicySnapshot.getTktWorktime());
		yGOrderCommand.setRefundWorkTime(jpPlatformPolicySnapshot
				.getRfdWorktime());
		yGOrderCommand.setWastWorkTime(jpPlatformPolicySnapshot
				.getWastWorkTime());
		// office
		String ticketOffice = jpPlatformPolicySnapshot.getTktOffice();
		if (ticketOffice == null || "".equals(ticketOffice)) {
			ticketOffice = "BJS280";
		}
		yGOrderCommand.setTicketOffice(ticketOffice);
		// 航班信息
		YGFlightCommand ygFlightCommand = new YGFlightCommand();
		ygFlightCommand.setCarrier(flightDto.getCarrier());
		ygFlightCommand.setFlightNo(flightDto.getFlightNo());
		ygFlightCommand.setBoardPoint(flightDto.getStartCityCode());
		ygFlightCommand.setOffPoint(flightDto.getEndCityCode());
		ygFlightCommand.setClassCode(classCode);
		ygFlightCommand.setDepartureDate(flightDto.getStartDate());
		yGOrderCommand.setFlight(ygFlightCommand);

		yGOrderCommand.setPassengers(jPOrderCreateCommand.getPassangers());

		// yGOrderCommand.setPatText();
		// yGOrderCommand.setSwitchPnr(jpPlatformPolicySnapshot.getSwitchPnr());
		// yGOrderCommand.setPolicyCommsion(jpPlatformPolicySnapshot.getCommRate());
		// yGOrderCommand.setForceDelete();
		return yGOrderCommand;
	}
	
	/**
	 * 
	 * @方法功能说明：收集WebABE下单需要参数
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月22日下午2:23:11
	 * @修改内容：
	 * @参数： SlfxFlightDTO
	 * @参数： map
	 * @参数： jPOrderCreateCommand
	 * @return:ABEOrderFlightCommand
	 * @throws
	 */
	private ABEOrderFlightCommand collectABECommandParam(SlfxFlightDTO sdto,
			HashMap<String, ABEPatFlightDTO> map,
			JPOrderCreateSpiCommand jPOrderCreateCommand) {

		SlfxFlightClassDTO fc = null;
		List<SlfxFlightClassDTO> slfxFlightClassDTOList = sdto
				.getFlightClassList();
		if (slfxFlightClassDTOList != null && slfxFlightClassDTOList.size() > 0) {
			for (SlfxFlightClassDTO slfxFlightClassDTO : slfxFlightClassDTOList) {
				if (jPOrderCreateCommand.getClassCode().equalsIgnoreCase(
						slfxFlightClassDTO.getClassCode())) {
					fc = slfxFlightClassDTO;
					break;
				}
			}
		}
		// 航班没有这个仓位，直接return
		if (fc == null) {
			return null;
		}

		// ABE下单Command
		ABEOrderFlightCommand ofc = new ABEOrderFlightCommand();

		// 旅客列表
		ABEPassengerCommand pc = null;
		List<ABEPassengerCommand> list = new ArrayList<ABEPassengerCommand>();
		// 乘客信息
		List<FlightPassengerDTO> passList = jPOrderCreateCommand
				.getPassangers();
		int index = 1;
		for (FlightPassengerDTO flightPassengerDTO : passList) {
			pc = new ABEPassengerCommand();

			pc.setPsgId(index);// 乘机人序号

			String passengerName = flightPassengerDTO.getName();
			if (StringUtils.isBlank(passengerName)) {
				passengerName = "乔布斯";
			}
			pc.setName(passengerName);
			pc.setPsgType("ADT");//
			// pc.setIdentityType(flightPassengerDTO.getIdentityType());//
			// pc.setCardType(flightPassengerDTO.getCardType());//
			pc.setIdentityType("NI");//
			pc.setCardType("NI");//
			pc.setCardNo(flightPassengerDTO.getCardNo());//
			pc.setCountry("CN");//
			if (flightPassengerDTO.getBirthday() != null
					&& !"".equals(flightPassengerDTO.getBirthday())) {
				pc.setBirthDay(flightPassengerDTO.getBirthday());
			} else {
				String idCard = flightPassengerDTO.getCardNo();
				if (idCard != null && idCard.length() == 18) {
					// 18位
					pc.setBirthDay(idCard.substring(6, 10) + "-"
							+ idCard.substring(10, 12) + "-"
							+ idCard.substring(12, 14));
				} else if (idCard != null && idCard.length() == 15) {
					// 15位
					pc.setBirthDay("19" + idCard.substring(6, 8) + "-"
							+ idCard.substring(8, 10) + "-"
							+ idCard.substring(10, 12));
				}
			}
			pc.setMobliePhone(flightPassengerDTO.getMobile());

			list.add(pc);

			index++;
		}

		// 报价项列表
		ABEPriceDetailCommand pdc = new ABEPriceDetailCommand();
		List<ABEPriceDetailCommand> list2 = new ArrayList<ABEPriceDetailCommand>();

		if (map != null && map.get("ADT") != null
				&& map.get("ADT").getAirportTax() != null) {
			pdc.setAirportTax(map.get("ADT").getAirportTax());
		} else {
			pdc.setAirportTax(0.00);
		}

		if (map != null && map.get("ADT") != null
				&& map.get("ADT").getFacePar() != null) {
			pdc.setFare(map.get("ADT").getFacePar());
		} else {
			pdc.setFare(0.00);
		}

		if (map != null && map.get("ADT") != null
				&& map.get("ADT").getFuelSurTax() != null) {
			pdc.setFuelSurTax(map.get("ADT").getFuelSurTax());
		} else {
			pdc.setFuelSurTax(0.00);
		}

		if (map != null && map.get("ADT") != null
				&& map.get("ADT").getAmount() != null) {
			pdc.setAmount(map.get("ADT").getAmount());
		} else {
			pdc.setAmount(0.00);
		}
		if (map != null && map.get("ADT") != null
				&& map.get("ADT").getTaxAmount() != null) {
			pdc.setTaxAmount(map.get("ADT").getTaxAmount());
		} else {
			pdc.setTaxAmount(0.00);
		}
		pdc.setPsgType("ADT");
		// pdc.setPsgId(0);
		list2.add(pdc);

		// 联系信息
		ABELinkerInfoCommand lic = new ABELinkerInfoCommand();
		lic.setIsETiket("Y");
		lic.setIsPrintSerial("Y");
		lic.setLinkerName(jPOrderCreateCommand.getLinker().getLoginName());
		lic.setMobilePhone(jPOrderCreateCommand.getLinker().getMobile());
		lic.setTelphone(jPOrderCreateCommand.getLinker().getMobile());

		// 订单信息描述
		ABEOrderInfoCommand oic = new ABEOrderInfoCommand();
		oic.setLinker("tou/shi");
		oic.setIsDomc("D");
		oic.setBalanceMoney(pdc.getAmount());
		oic.setTelephone(jPOrderCreateCommand.getLinker().getMobile());

		// 订单航段信息
		ofc.setCarrier(sdto.getCarrier());
		ofc.setFlightNo(sdto.getFlightNo());
		ofc.setStartCityCode(sdto.getStartPort());
		ofc.setEndCityCode(sdto.getEndPort());
		ofc.setMileage(Integer.valueOf(sdto.getMileage()));
		ofc.setClassCode(fc.getClassCode() == null ? "" : fc.getClassCode());
		ofc.setClassPrice(fc.getOriginalPrice() == null ? 0.0d : fc
				.getOriginalPrice());
		ofc.setClassRebate(String.valueOf(fc.getDiscount()));
		ofc.setYPrice(sdto.getYPrice());
		ofc.setFuelSurTax(sdto.getFuelSurTax());
		ofc.setAirportTax(sdto.getAirportTax());
		ofc.setStartDate(sdto.getStartDate());
		ofc.setStartTime(sdto.getStartTime());
		ofc.setEndDate(sdto.getEndDate());
		ofc.setEndTime(sdto.getEndTime());
		ofc.setAircraftCode(sdto.getAircraftCode());

		ofc.setAbeOrderInfoCommand(oic);
		ofc.setAbeLinkerInfoCommand(lic);
		ofc.setAbePriceDetailList(list2);
		ofc.setAbePsgList(list);

		return ofc;
	}
	/**
	 * 
	 * @方法功能说明：将易购返回的航班DTO 转换为平台航班DTO
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月1日上午11:39:58
	 * @修改内容：
	 * @参数：@param ygFlightDto
	 * @参数：@return
	 * @return:FlightDTO
	 * @throws
	 */
	private SlfxFlightDTO convertYGFlightDTOToFlightDTO(YGFlightDTO ygFlightDto) {
		if (ygFlightDto == null) {
			return null;
		}
		SlfxFlightDTO flightDto = new SlfxFlightDTO();
		flightDto.setFlightNo(ygFlightDto.getFlightNo());
		String tempCarrier = ygFlightDto.getCarrier();
		if (tempCarrier == null || "".equals(tempCarrier)) {
			tempCarrier = ygFlightDto.getFlightNo().substring(0, 2);
		}
		flightDto.setCarrier(tempCarrier);
		flightDto.setClassCode(ygFlightDto.getClassCode());
		flightDto.setAirCompName(ygFlightDto.getAirCompName());
		flightDto.setStartPort(ygFlightDto.getStartPort());
		flightDto.setEndPort(ygFlightDto.getEndPort());
		flightDto.setStartPortName(ygFlightDto.getStartPortName());

		flightDto.setEndPortName(ygFlightDto.getEndPortName());
		flightDto.setStartCityCode(ygFlightDto.getStartCityCode());
		flightDto.setEndCityCode(ygFlightDto.getEndCityCode());
		flightDto.setStartCity(ygFlightDto.getStartPortName());
		flightDto.setEndCity(ygFlightDto.getEndPortName());
		flightDto.setStartTime(ygFlightDto.getStartTime());
		flightDto.setEndTime(ygFlightDto.getEndTime());
		flightDto.setStartDate(ygFlightDto.getStartDate());
		flightDto.setEndDate(ygFlightDto.getEndDate());
		flightDto.setFlyTime(ygFlightDto.getFlyTime());
		flightDto.setAircraftCode(ygFlightDto.getAircraftCode());
		flightDto.setFuelSurTax(ygFlightDto.getFuelSurTax());
		flightDto.setAirportTax(ygFlightDto.getAirportTax());
		flightDto.setTaxAmount(ygFlightDto.getTaxAmount());
		flightDto.setShareFlightNum(ygFlightDto.getShareFlightNum());
		flightDto.setStopNum(ygFlightDto.getStopNum());
		flightDto.setStartTerminal(ygFlightDto.getStartTerminal());

		flightDto.setEndTerminal(ygFlightDto.getEndTerminal());
		flightDto.setIsFood(ygFlightDto.getIsFood());
		flightDto.setYPrice(ygFlightDto.getYPrice());
		flightDto.setMileage(ygFlightDto.getMileage());
		flightDto.setCheapestFlightClass(ygFlightDto.getCheapestFlightClass());
		flightDto.setFlightClassList(ygFlightDto.getFlightClassList());

		flightDto.setFare(ygFlightDto.getFare());

		return flightDto;
	}

	/**
	 * 查询平台机票的订单信息
	 * @param qo
	 * @return
	 */
	public JPOrder queryJPOrderUnique(PlatformOrderQO qo) {
		try{
			return jpOrderDAO.queryUnique(qo);			
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->queryJPOrderUnique->exception[查询平台机票的订单信息]:" + HgLogger.getStackTrace(e));
		}
		return null;
	}
	/**
	 * 查询易购机票的订单信息
	 * @param qo
	 * @return
	 */
	public YGOrder queryYGOrderUnique(YGOrderQO qo) {
		return ygOrderDAO.queryUnique(qo);
	}
	/**
	 * 查询ABE机票的订单信息
	 * @param qo
	 * @return
	 */
	public ABEOrder queryABEOrderUnique(PlatformOrderQO qo) {
		return abeOrderDAO.queryUnique(qo);
	}
	
	public boolean adminCancelOrder(AdminCancelOrderCommand command) {
		if (command == null) {
			return false;
		}
		JPOrder jpOrder = getDao().get(command.getId());
		if (jpOrder == null) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->adminCancelOrder->查询不到订单:" + JSON.toJSONString(command));
			return false;
		}

		//调用易购接口，删除pnr
		try {
			ABEDeletePnrCommand deleteCommand=new ABEDeletePnrCommand();
			deleteCommand.setCommandId(command.getId());
			deleteCommand.setPnr(jpOrder.getPnr());
			ygFlightService.deletePnr(deleteCommand);
			
			/*
			ABEDeletePnrDTO abeDeletePnrDTO = ygFlightService.deletePnr(deleteCommand);
			if(abeDeletePnrDTO == null || abeDeletePnrDTO.getStatus() == null || "N".equalsIgnoreCase(abeDeletePnrDTO.getStatus().trim())){
				HgLogger.getInstance().error("tandeng", "JPOrderLocalService->adminCancelOrder->调用易购接口删除PNR失败，pnr="+jpOrder.getPnr()+",orderId="+command.getId());
				return false;
			}
			*/
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->adminCancelOrder->调用易购接口删除PNR异常，pnr="+jpOrder.getPnr()+",orderId="+command.getId());
			//return false;
		}
		
		//修改订单状态
		//jpOrder.adminCancelOrder(command);
		JPOrderStatus status = new JPOrderStatus();
		status.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_CANCEL));
		status.setPayStatus(jpOrder.getOrderStatus().getPayStatus());
		jpOrder.adminCancelOrder(jpOrder,command,status);
		getDao().update(jpOrder);
		
		//同步给商城
		String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
		HgLogger.getInstance().info("yuqz", "JPOrderLocalService->adminCancelOrder->取消订单通知商城的地址：" + notifyUrl);
		HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_CANCEL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_NO_PAY, 60000);
		//添加操作日志
		CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
		logCommand.setLogName(OrderLogConstants.CANCEL_LOG_NAME);
		logCommand.setWorker(command.getFromAdminId());
		logCommand.setLogInfo(OrderLogConstants.CANCEL_FX_LOG_INFO);
		logCommand.setJpOrderId(jpOrder.getId());
		jpOrderLogLocalService.create(logCommand,jpOrder);
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPCancelOrder","adminCancelOrder",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		return true;
	}
	
	public boolean adminRefundOrder(ApplyRefundCommand command) {
		
		if (command == null) return false;//空指针，直接返回
		
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->adminRefundOrder->分销退废票开始，command" + JSON.toJSONString(command));
		
		JPOrder jpOrder = getDao().get(command.getOrderNo());//这是一个坑，统一下字段名称吧！！！
		if (jpOrder == null) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->adminRefundOrder->查询不到订单:" + JSON.toJSONString(command));
			return false;//空指针，直接返回
		}
		
		
		// 1.更新原先的订单
		JPOrderStatus status01 = new JPOrderStatus(
				Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_DEALING), //退/废处理中
				Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_TO_BE_BACK_WAIT)); //待回款
		
		jpOrder.adminRefundOrder(jpOrder,command,status01);
		getDao().update(jpOrder);

		// 2. 增加一条废单记录
		YGQueryOrderDTO ygQueryOrderDTO = ygFlightService.queryOrder(command.getRefundOrderNo());
		JPOrderStatus orderStatus = new JPOrderStatus(
				Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_DEALING), //退/废处理中
				Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_PAY_SUCC));  //支付成功
		JPOrder jpOrder02 = new JPOrder(jpOrder,command,ygQueryOrderDTO,orderStatus); 
		jpOrder02.setUserPayAmount(0.00d);
		getDao().save(jpOrder02);
		

		//同步给商城
		String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
		HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_DEALING+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC, 60000);
		
		//===========记录日志==============
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","adminRefundOrder",JSON.toJSONString(command));
		List<String> params = new ArrayList<String>();
		params.add(JSON.toJSONString(command));
		String param;
		if ("F".equals(command.getRefundType())) {
			param = "废票";
		} else {
			param = "退票";
		}
		params.add(JSON.toJSONString(param));
		event.setParams(params);
		domainEventRepository.save(event);
		//=========================
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->adminRefundOrder->分销退废票结束");
		return true;
}

	/**
	 * 
	 * @方法功能说明：商城取消订单
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月30日下午8:04:48
	 * @修改内容：
	 * @参数：@param command
	 * @return:boolean
	 * @throws
	 */
	public boolean shopCancelOrder(AdminCancelOrderCommand command) {
		
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopCancelOrder->商城取消订单开始，command="+JSON.toJSONString(command));
		if (command == null){
			return false;
		}
		
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setOrderNo(command.getOrderId());
		JPOrder jpOrder = getDao().queryUnique(qo);
		
		if (jpOrder == null) {
			HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopCancelOrder->查询不到订单:" + JSON.toJSONString(command));
			return false;
		}
		//调用易购接口，删除pnr
		try {
			ABEDeletePnrCommand deleteCommand=new ABEDeletePnrCommand();
			deleteCommand.setCommandId(jpOrder.getId());
			deleteCommand.setPnr(jpOrder.getPnr());
			ygFlightService.deletePnr(deleteCommand);
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "JPOrderLocalService->shopCancelOrder->调用易购接口删除PNR异常，pnr="+jpOrder.getPnr()+",orderId="+command.getId());
		}
		
		JPOrderStatus orderStatus = new JPOrderStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_CANCEL),jpOrder.getOrderStatus().getPayStatus());
		jpOrder.shopCancelOrder(jpOrder,command,orderStatus);
		getDao().update(jpOrder);
		HgLogger.getInstance().info("tandeng", "JPOrderLocalService->shopCancelOrder->商城取消订单结束");
		
		//同步给商城
		String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
		HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_CANCEL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_NO_PAY, 60000);
		
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPCancelOrder","shopCancelOrder",JSON.toJSONString(command));
		domainEventRepository.save(event);
		return true;
	}
	
	/**
	 * 通过 {flightNo-yyyyMMdd},从缓存获取航班信息(FlightDTO的返回格式)
	 * @param flightNo
	 * @param flightDateStr
	 * @return
	 */
	public YGFlightDTO queryCacheFlightInfo(String flightNo,String flightDateStr){
		return this.webFlightService.queryCacheFlightInfo(flightNo,flightDateStr);
	}
	
	/**
	 * abe 下单
	 * @param abeCommand
	 * @return
	 */
	public ABEOrderFlightDTO abeOrderFlight(ABEOrderFlightCommand abeCommand){
		//abe下单,（返回PNR码）
		ABEOrderFlightDTO abeOrderDto=this.ygFlightService.abeOrderFlight(abeCommand);
		
		//abe订单入库
		ABEOrder abeOrder=this.collectABEOrderParam(abeCommand,abeOrderDto);
		
		String tempId = UUIDGenerator.getUUID();
		abeOrderDto.setId(tempId);
		
		abeOrder.setId(tempId);
		abeOrder.setCreateDate(new Date());
		abeOrderDAO.save(abeOrder);
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","abeOrderFlight",JSON.toJSONString(abeCommand));
		domainEventRepository.save(event);
		return abeOrderDto;
	}
	
	/**
	 * 易购下单
	 * @param yGOrderCommand
	 * @return
	 */
	public YGFlightOrderDTO ygOrderByPnr(YGOrderCommand yGOrderCommand){
		
		//易购下单
		YGFlightOrderDTO yGFlightOrderDTO = ygFlightService.orderByPnr(yGOrderCommand);
		
		//请求返回参数处理
		YGOrder ygOrder=this.setYGOrderInfoFromYGOrderCommand(yGOrderCommand,yGFlightOrderDTO);
		ygOrder.setStatus(YGOrderStatus.HK);
		ygOrderDAO.save(ygOrder);
		
		yGFlightOrderDTO.setId(ygOrder.getId());
		if (StringUtils.isBlank(yGFlightOrderDTO.getPnr())) {
			yGFlightOrderDTO.setPnr(yGOrderCommand.getPnr());
		}
		
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","ygOrderByPnr",JSON.toJSONString(yGOrderCommand));
		domainEventRepository.save(event);
		return yGFlightOrderDTO;
	}
	
	
	/**
	 * 更改易购订单状态
	 * @param ygOrder
	 * @return
	 */
	public void updateYGOrder(YGOrderQO qo, String status){
		YGOrder ygOrder = ygOrderDAO.queryUnique(qo);
		
		if (null != ygOrder) {
			ygOrder.setStatus(status);
			getDao().update(ygOrder);
		}
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","updateYGOrder",JSON.toJSONString(qo));
		domainEventRepository.save(event);
	}
	
	
	/**
	 * 设置abe订单入库实体类
	 * @param abeCommand
	 * @param abeOrderDto
	 * @return
	 */
	private ABEOrder collectABEOrderParam(ABEOrderFlightCommand abeCommand,
			ABEOrderFlightDTO abeOrderDto){
		
		ABEOrder abeOrder =new ABEOrder();
		
		abeOrder.setPriceDetailListJson(JSON.toJSONString(abeCommand.getAbePriceDetailList()));
		abeOrder.setPassengerInfoJson(JSON.toJSONString(abeCommand.getAbePsgList()));
		abeOrder.setPnr(abeOrderDto.getPnr());

		return abeOrder;
		/*//拷贝基本属性
		String[] ignoreProperties =new String[]{"abePsgList","abePriceDetailList","abeLinkerInfoCommand"};
		BeanUtils.copyProperties(abeCommand, abeOrder,ignoreProperties);
		
		//拷贝旅客信息
		List<ABEPassangerInfo> abePsgInfoList =new ArrayList<ABEPassangerInfo>();
		List<ABEPassengerCommand> abePsgCommandList =abeCommand.getAbePsgList();
		if(abePsgCommandList!=null&&abePsgCommandList.size()>0){
			for(ABEPassengerCommand abePsgCommand:abePsgCommandList){
				ABEPassangerInfo abePassangerInfo =new ABEPassangerInfo();
				BeanUtils.copyProperties(abePsgCommand, abePassangerInfo);
				abePsgInfoList.add(abePassangerInfo);
			}
		}
		abeOrder.setAbePsgList(abePsgInfoList);
		
		
		//拷贝航班信息
		List<ABEPriceDetailCommand> abePriceDetailList=abeCommand.getAbePriceDetailList();
		List<PriceItem> abePriceItemList=new ArrayList<PriceItem>();
		
		if(abePriceDetailList!=null){
			for(ABEPriceDetailCommand abePriceDetail:abePriceDetailList){
				PriceItem priceItem =new PriceItem();
				BeanUtils.copyProperties(abePriceDetail, priceItem);
				abePriceItemList.add(priceItem);
			}
		}
		abeOrder.setAbePriceDetailList(abePriceItemList);
		
		//拷贝联系人
		Linker abeLinkerInfo =new Linker();
		ABELinkerInfoCommand abeLinker=abeCommand.getAbeLinkerInfoCommand();
		BeanUtils.copyProperties(abeLinker, abeLinkerInfo);
		abeOrder.setAbeLinkerInfo(abeLinkerInfo);*/

	}
	
	/**
	 * 封装易购订单入库实体类
	 * @param ygOrderCommand
	 * @param yGFlightOrderDTO
	 * @return YGOrder
	 */
	private YGOrder setYGOrderInfoFromYGOrderCommand(
			YGOrderCommand ygOrderCommand,
			YGFlightOrderDTO yGFlightOrderDTO){
		
		YGOrder tempYGOrder =new YGOrder();
		tempYGOrder.setId(UUIDGenerator.getUUID());
		tempYGOrder.setStatus(YGOrderStatus.HK);
		
		tempYGOrder.setSupplierOrderNo(yGFlightOrderDTO.getSupplierOrderNo());
		tempYGOrder.setYgOrderNo(yGFlightOrderDTO.getYgOrderNo());
		tempYGOrder.setPsgCount(yGFlightOrderDTO.getPsgCount());
		tempYGOrder.setBalanceMoney(yGFlightOrderDTO.getBalanceMoney());
		tempYGOrder.setFare(yGFlightOrderDTO.getFare());
		tempYGOrder.setTaxAmount(yGFlightOrderDTO.getTaxAmount());
		//没有传值就自己计算
		tempYGOrder.setCommAmount(yGFlightOrderDTO.getCommAmount());
		tempYGOrder.setCommRate(yGFlightOrderDTO.getCommRate());
		tempYGOrder.setTicketOffice(yGFlightOrderDTO.getTicketOffice());
		
		tempYGOrder.setPnr(ygOrderCommand.getPnr());
		tempYGOrder.setClassCode(ygOrderCommand.getClassCode());
		tempYGOrder.setPlatCode(ygOrderCommand.getPlatCode());
		tempYGOrder.setPnrText(ygOrderCommand.getPnrText());
		tempYGOrder.setBigPNR(ygOrderCommand.getBigPNR());
		tempYGOrder.setPolicyId(ygOrderCommand.getPolicyId());
		tempYGOrder.setPlatformType(ygOrderCommand.getPlatformType());
		tempYGOrder.setIsVip(ygOrderCommand.getIsVip());
		tempYGOrder.setAccountLevel(ygOrderCommand.getAccountLevel());
		tempYGOrder.setRemark(ygOrderCommand.getRemark());
		tempYGOrder.setWorkTime(ygOrderCommand.getWorkTime());
		tempYGOrder.setRefundWorkTime(ygOrderCommand.getRefundWorkTime());
		tempYGOrder.setWastWorkTime(ygOrderCommand.getWastWorkTime());
		
		tempYGOrder.setFlightJson(JSON.toJSONString(ygOrderCommand.getFlight()));
		tempYGOrder.setPassengersJson(JSON.toJSONString(ygOrderCommand.getPassengers()));

		tempYGOrder.setCreateDate(new Date());

		return tempYGOrder;
	}
	
	
	/**
	 * 设置平台订单入库实体类
	 * @param jpOrder
	 * @param abeId 表主键关联
	 * @param ygId  表主键关联
	 * @return
	 */
//	@Transactional
	public JPOrderDTO saveJPOrderInfo(JPOrder jpOrder) {
		

		jpOrder.setId(UUIDGenerator.getUUID());
		jpOrder.setCreateDate(new Date());
		
		jpOrderDAO.save(jpOrder);
		
		
		//设置操作日志
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","saveJPOrderInfo",JSON.toJSONString(jpOrder));
		domainEventRepository.save(event);

		// 旅客信息列表
		Set<Passenger> passangerList = jpOrder.getPassangerList();
		for (Passenger passenger : passangerList) {
			
			//1.保存机票信息
			passenger.getTicket().setId(UUID.randomUUID().toString());
			this.saveFlightTicket(passenger.getTicket());
			
			//2.保存旅客信息
			passenger.setId(UUID.randomUUID().toString());
			passenger.setJpOrder(jpOrder);
			this.savePassenger(passenger);
		}
		//3. 保存政策信息
//		jpOrder.getComparePrice().getCompareResultPolicy().setId(UUID.randomUUID().toString());
//		this.saveFlightPolicy(jpOrder.getComparePrice().getCompareResultPolicy());
		
		//4. 保存比价信息
//		jpOrder.getComparePrice().setId(UUID.randomUUID().toString());
//		this.saveComparePrice(jpOrder.getComparePrice());

		JPOrderDTO jpOrderDTO = this.returnJPOrderDTO(jpOrder);
		
		return jpOrderDTO;
		
	}
	
	/**
	 * 转换dto
	 * @param jpOrder
	 * @return
	 */
	public JPOrderDTO conversionJPOrderToDTO(JPOrder jpOrder) {
		
		JPOrderDTO jpOrderDTO = this.returnJPOrderDTO(jpOrder);
		
		return jpOrderDTO;
		
	}
	
	/**
	 * 
	 * @方法功能说明：平台返回DTO
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月30日上午11:38:17
	 * @修改内容：
	 * @参数：@param abeOrderFlightDTO
	 * @参数：@param ygFlightOrderDTO
	 * @参数：@return
	 * @return:JPOrderDTO
	 * @throws
	 */
	private JPOrderDTO returnJPOrderDTO(JPOrder jpOrder){
		JPOrderDTO jpOrderDTO = new JPOrderDTO();
		if(jpOrder != null){
			
			jpOrderDTO.setId(jpOrder.getId());
			jpOrderDTO.setOrderNo(jpOrder.getOrderNo());
			jpOrderDTO.setPnr(jpOrder.getPnr());
			jpOrderDTO.setClassCode(jpOrder.getClassCode());
			jpOrderDTO.setPlatCode(jpOrder.getPlatCode());
			jpOrderDTO.setUserPayAmount(jpOrder.getUserPayAmount());
			jpOrderDTO.setTktAmount(jpOrder.getTktPrice());
			jpOrderDTO.setTktTax(jpOrder.getTktTax() / jpOrder.getPassangerCount());
			jpOrderDTO.setCreateDate(jpOrder.getCreateDate());
			jpOrderDTO.setStatus(jpOrder.getOrderStatus().getStatus());
			jpOrderDTO.setAgencyName(jpOrder.getAgencyName());
			
			//供应商有office使用接口传递的，没有默认平台的office
			jpOrderDTO.setOrderSource(jpOrder.getSuppShortName());
			jpOrderDTO.setWastWorkTime(jpOrder.getWastWorkTime());
			
			jpOrderDTO.setRefundWorkTime(jpOrder.getRefundWorkTime());
			jpOrderDTO.setWorkTime(jpOrder.getWorkTime());
			
			jpOrderDTO.setDealerOrderCode(jpOrder.getDealerOrderCode());
			jpOrderDTO.setSupplierOrderNo(jpOrder.getSupplierOrderNo());
			jpOrderDTO.setCommRate(jpOrder.getCommRate());
			jpOrderDTO.setCommAmount(jpOrder.getCommAmount());
			
			jpOrderDTO.setTicketPrice(jpOrder.getTktPrice() / jpOrder.getPassangerCount());

			//jpOrderDTO.setYgOrderNo(jpOrder.getYgOrderNo());
			//jpOrderDTO.setFlightDTO(jpOrder.getFlightSnapshotJSON());
			//jpOrderDTO.setOrderNo(jpOrder.getOrderNo());
			/*Set<Passenger> passengerList =  jpOrder.getPassangerList();
			if (null != passengerList) {
				String[] arrTktNo =  new String[passengerList.size()];
				Iterator<Passenger> iter = passengerList.iterator();
				for (int i = 0; iter.hasNext() ; i++) {
					FlightTicket tkt = iter.next().getTicket();
					if (null != tkt)
						arrTktNo[i] = tkt.getTicketNo();
				}
				jpOrderDTO.setTktNo(arrTktNo);
			}*/

		}
		
		return jpOrderDTO;
	}
	
	/**
	 * 处理支付宝退款业务
	 * @param resultDetails
	 * @return
	 */
	public boolean refund(String resultDetails) {
		
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->refund->resultDetails:" + resultDetails);
		boolean ret = true;
		
		//2014111909785721^257.84^SUCCESS$ply365@ply365.com^2088611359544680^1.55^SUCCESS#
		//解析支付宝退款结果集
		if (StringUtils.isNotBlank(resultDetails)) {
			String[] details = resultDetails.split("#");//解析每一条数据集
			
			for (String detail : details) {
				detail.indexOf("$");
				int index = detail.indexOf("$");
				if (index != -1)  {
					detail = detail.substring(0, index);
				}
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->refund->detail=" + detail);
				/*报空指针 tandeng 注释 20141126
				String[] datas = detail.split("^"); //解析每一条数据集中的字段，第一项为支付宝单号，第二项为退款金额，第三项为退款状态
				String payTradeNo = datas[0];
				String status = datas[2];
				*/
				String payTradeNo =detail.substring(0,16);
				
				//if (detail.endsWith("SUCCESS") || detail.endsWith("REASON_REFUND_CHARGE_ERR") 
				//		|| detail.endsWith("SERVICE_AMOUNT_OVER")) {
				if (detail.endsWith("SUCCESS")) {
					
					PlatformOrderQO qo = new PlatformOrderQO();
					qo.setPayTradeNo(payTradeNo);
					/*qo.setStatus(Integer.parseInt(JPOrderStatus.DISCARD_SUCC_NOT_REFUND),
							Integer.parseInt(JPOrderStatus.BACK_SUCC_NOT_REFUND),
							Integer.parseInt(JPOrderStatus.TICKET_FAIL_NEED_REFUND));*/
					try {
						JPOrder jpOrder = getDao().queryList(qo).get(0);  //根据交易帐号查询订单
						
						if (jpOrder == null) {
							HgLogger.getInstance().info("zhangka", "JPOrderLocalService->refund->没有查询数据,qo=" + JSON.toJSONString(qo));
							continue;
						}
						
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->refund->jpOrder=" + JSON.toJSONString(jpOrder));
						
						JPOrderStatus orderStatus = jpOrder.getOrderStatus();
						if (orderStatus == null) {
							orderStatus = new JPOrderStatus();
						}
						
						if (orderStatus.getStatus() == null) {  //订单状态为空
							orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC)); //退废成功
							orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//已退款
						} 
						//判断一样删除一个
//						else if (orderStatus.getStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC)&&orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT)) {//订单状态：退废成功  支付状态：待退款
//							orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC));//订单状态:退废成功
//							orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//支付状态：已退款
//						} 
						else if (orderStatus.getStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC)&&orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT)) {//订单状态：退废成功  支付状态：待退款
							orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC));//订单状态:退废成功
							orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//支付状态：已退款
						} else if (orderStatus.getStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL)&&orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT)) {//订单状态：出票失败  支付状态：待退款
							orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL));//出票失败
							orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC));//已退款
						}
						
						jpOrder.setOrderStatus(orderStatus);
						getDao().update(jpOrder);
						
						//同步给商城
						String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
						if(orderStatus.getStatus() ==Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC) &&orderStatus.getPayStatus()==Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_SUCC)){//订单状态：退/废成功  支付状态：已退款
							HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC, 60000);//订单状态：退/废成功  支付状态：已退款
						}else{
							HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrder.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_FAIL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC, 60000);//订单状态：出票失败   支付状态：已退款
						}
						
						
						
					} catch(Exception e) {
						HgLogger.getInstance().error("zhangka", "JPOrderLocalService->refund->exception:" + HgLogger.getStackTrace(e));
						ret = false;
					}
					
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 单元测试
	 * @param ygOrder
	 */
	public void saveYGOrder(YGOrder ygOrder) {
		this.ygOrderDAO.save(ygOrder);
	}
	
	/**
	 * 单元测试
	 * @param jpOrder
	 */
	public void saveJPOrder(JPOrder jpOrder) {
		this.jpOrderDAO.save(jpOrder);
	}
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveABEOrder(ABEOrder abeOrder) {
		this.abeOrderDAO.save(abeOrder);
	}
	/**
	 * 单元测试
	 * @param flightTicket
	 */
	public void savePassenger(Passenger passenger) {
		this.passengerDAO.save(passenger);
	}
	
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveComparePrice(ComparePrice comparePrice) {
		this.comparePriceDAO.save(comparePrice);
	}
	
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveFlightPolicy(FlightPolicy flightPolicy) {
		this.flightPolicyDAO.save(flightPolicy);
	}
	
	/**
	 * 单元测试
	 * @param flightTicket
	 */
	public void saveFlightTicket(FlightTicket flightTicket) {
		this.flightTicketDAO.save(flightTicket);
	}
	
	/*public void testABEOrderSave(){
		ABEOrder abeOrder = new ABEOrder();
		abeOrder.setYprice(880.25);
		abeOrder.setStartTime("08:28");
		abeOrder.setStartDate("2014-05-06");
		abeOrder.setStartCityCode("ABC");
		abeOrder.setMileage(22);
		abeOrder.setId("123456789321654987");
		abeOrder.setFuelSurTax(222.30);
		abeOrder.setFlightNo("MH370");
		abeOrder.setEndTime("11:23");
		abeOrder.setEndDate("2014-05-06");
		abeOrder.setEndCityCode("XYZ");
		abeOrder.setClassRebate("40");
		abeOrder.setClassPrice(257.35);
		abeOrder.setClassCode("Y");
		abeOrder.setCarrier("MH");
		abeOrder.setAirportTax(50.5);
		abeOrder.setAircraftCode("737");
		
		Linker  linker = new Linker();
		linker.setMobilePhone("13755555555");
		linker.setPayType("支付宝");
		
		linker.setAddress("浙江杭州滨江");
		linker.setInvoicesSendType("A");
		linker.setIsETiket("Y");
		linker.setIsPrintSerial("Y");
		linker.setLinkerEmail("1212@163.com");
		linker.setLinkerName("大妈大叔");
		linker.setNeedInvoices("Y");
		linker.setSendTime(null);
		linker.setSendTktsTypeCode("ZQ");
		linker.setTelphone("13755555555");
		linker.setZip("314400");
		abeOrder.setAbeLinkerInfo(linker);
		
		abeOrder.setAbePriceDetailListJson("[{a:'aaa',ab:null,abc:254}");
		
		ABEOrderInfoDetail oid = new ABEOrderInfoDetail();
		oid.setAddress("杭州市西湖区文三路222号");
		oid.setBalanceMoney(2221.00);
		oid.setBankCode("BADA0161");
		oid.setDomc("D");
		oid.setLinker("哈哈哈");
		oid.setPayPlatform("了解的垃圾垃圾");
		oid.setRemark("备注备注");
		oid.setTel("0571-87452145");
		oid.setTicketLimitDate(new Date());
		abeOrder.setOrderInfo(oid);
		
		abeOrder.setAbePsgListJson("ssssssssssssss");
		
		this.abeOrderDAO.save(abeOrder);
	}*/
	
	public ABEDeletePnrDTO testDeletePnr(String pnr){
		ABEDeletePnrCommand command = new ABEDeletePnrCommand();
		command.setPnr(pnr);
		return this.ygFlightService.deletePnr(command);
	}
	
	
	public ABEXmlRtPnrDTO xmlRtPnr(String pnr){
		return ygFlightService.xmlRtPnr(pnr);
	}
	
	
	public YGQueryOrderDTO queryYgOrder(String orderNo) {
		return ygFlightService.queryOrder(orderNo);
	}

	public JPOrder queryPaltformOrder(PlatformOrderQO qo) {
		return jpOrderDAO.queryUnique(qo);
	}
	
	@Transactional
	public void saveRefundJPOrderInfo(JPOrder jpOrder) {
		jpOrderDAO.save(jpOrder);
	}
}



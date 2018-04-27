package hsl.app.service.local.jp;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.SMSUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.config.SysProperties;
import hsl.app.dao.CouponDao;
import hsl.app.dao.JPOrderDao;
import hsl.app.dao.JPOrderOperationLogDao;
import hsl.app.dao.JPOrderRefundLogDao;
import hsl.app.service.local.coupon.CouponLocalService;
import hsl.app.service.local.user.UserLocalService;
import hsl.domain.model.coupon.Coupon;
import hsl.domain.model.jp.JPOrder;
import hsl.domain.model.jp.JPOrderOperationLog;
import hsl.domain.model.jp.JPOrderRefundLog;
import hsl.domain.model.jp.JPPassanger;
import hsl.domain.model.jp.JPTicket;
import hsl.domain.model.user.User;
import hsl.domain.model.user.UserAuthInfo;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.UpdateJPOrderStatusCommand;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.jp.FlightPassangerDto;
import hsl.pojo.dto.jp.JPOrderCreateDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.command.JPOrderCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.response.jp.JPCancelOrderTicketResponse;
import slfx.api.v1.response.jp.JPCreateOrderResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Service("jpOrderLocalService")
@Transactional
public class JPOrderLocalService extends BaseServiceImpl<JPOrder, HslJPOrderQO, JPOrderDao> {

	@Resource
	private JPOrderDao dao;
	
	@Resource
	private JPOrderOperationLogDao operationLogDao;
	
	@Resource
	private JPOrderRefundLogDao refundLogDao;
	
	//@Resource
	//private JPPassangerDao passangerDao;
	@Resource
	private JPPassangerLocalService jpPassangerLocalService;
	
	@Resource
	private SlfxApiClient client;
	
	@Resource
	private JPFlightLocalService jpFlightLocalService;
	
	@Resource
	private UserLocalService userLocalService;
	
	@Resource
	private CouponLocalService couponLocalService;
	
	@Resource
	private SMSUtils smsUtils;
	
	@Resource
	private JedisPool jedisPool;
	
	@Resource
	private CouponDao couponDao;
	
//	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//	private DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
//	private DateFormat timeFormat2 = new SimpleDateFormat("yyyy/MM/dd hh:mm"); 
	
	@Override
	protected JPOrderDao getDao() {
		return dao;
	}
	
	//订单查询
	public List<JPOrder> queryOrder(HslJPOrderQO qo) {
		List<JPOrder> jpOrders = dao.queryList(qo);
		
		if (jpOrders != null && jpOrders.size() > 0) {
			for (JPOrder order : jpOrders) {
				if (order != null) {
					order.setFlightDTO(JSON.parseObject(order.getJpOrderSnapshot(), FlightDTO.class));
					
					Set<JPPassanger> passangers = order.getPassangers();
					if (passangers != null && passangers.size() > 0) {
						for (JPPassanger passanger : passangers) {
							Hibernate.initialize(passanger);
						}
					}
				}
			}
		}
		
		return jpOrders;
	}
	
	//订单创建
	public JPOrderCreateDTO orderCreate(JPOrderCreateCommand command) {
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderCreate->开始执行");
		
		//new出一个slfx的command
		APIJPOrderCreateCommand  slfxCommand  = new  APIJPOrderCreateCommand();
		slfxCommand = BeanMapperUtils.getMapper().map(command, APIJPOrderCreateCommand.class);
		
		if (slfxCommand != null && slfxCommand.getLinker() != null && StringUtils.isNotBlank(slfxCommand.getLinker().getId())) {
			HslUserQO userQO = new HslUserQO();
			userQO.setId(slfxCommand.getLinker().getId());
			HslUserBindAccountQO hslUserBindAccountQO=new HslUserBindAccountQO();
			hslUserBindAccountQO.setUserQO(userQO);
			try {
				User user = userLocalService.queryUser(hslUserBindAccountQO);
				if (user != null) {
					UserAuthInfo userAuthInfo = user.getAuthInfo();
					if (null != userAuthInfo) {
						slfxCommand.getLinker().setLoginName(user.getAuthInfo().getLoginName());
					}
					if (slfxCommand.getLinker().getMobile() == null || "".equals(slfxCommand.getLinker().getMobile())) {						
						slfxCommand.getLinker().setMobile(user.getContactInfo().getMobile());						
					}
					
				} else {
					return null;					
				}
			} catch (UserException e) {
				HgLogger.getInstance().error("zhangka", "JPOrderLocalService->orderCreate->exception[订单创建]:" + HgLogger.getStackTrace(e));
				return null;
			} catch (Exception e) {
				HgLogger.getInstance().error("zhangka", "JPOrderLocalService->orderCreate->exception[订单创建]:" + HgLogger.getStackTrace(e));
				return null;
			}
		} else {
			return null;
		}
		
		JPCreateOrderResponse response = null;
		JPOrderCreateDTO jpOrderCreateDTO = null;
		JPOrder jpOrder =null;
		try {
			/*由于在Controller已经判断是否重复下单，把此处屏蔽*/
			/*HslJPOrderQO jpOrderQO = new HslJPOrderQO();
			jpOrderQO.setFlightNo(slfxCommand.getFlightNo());
			
			List<FlightPassangerDto> passangerDTOs = command.getPassangers();
			if (passangerDTOs == null || passangerDTOs.size() == 0) {
				return null;
			} else {
				FlightPassangerDto passenger = passangerDTOs.get(0);
				jpOrderQO.setCardNo(passenger.getCardNo());
			}
			
			String dateStr = slfxCommand.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			date.setDate(date.getDate() + 1);
			dateStr = sdf.format(date);
			jpOrderQO.setFlightStartTime(slfxCommand.getDate());
			jpOrderQO.setFlightEndTime(dateStr);
			
			JPOrder jpOrder = dao.queryUnique(jpOrderQO);
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderCreate->jpOrder[订单创建，验证订单是否重复提交]:" + JSON.toJSONString(jpOrder));
			if (jpOrder != null && String.valueOf(jpOrder.getPayStatus()).equals(JPOrderStatusConstant.SHOP_TICKET_NO_PAY))//不为空且待支付
				return null;//重复下单直接返回
			*/
			
			Date nowDate = new Date();
			String dealerOrderCode = OrderUtil.createOrderNo(nowDate, this.getOrderSequence(), 0, 0);
			slfxCommand.setDealerOrderId(dealerOrderCode);
			// 创建要发送的请求对象
			ApiRequest request = new ApiRequest("JPOrderCreate", ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), slfxCommand);
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderCreate->request[订单创建，向分销平台发送请求]:" + JSON.toJSONString(request));
			
			// 发送请求
			response = client.send(request, JPCreateOrderResponse.class);
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderCreate->response[订单创建，分销平台已返回]:" +JSON.toJSONString(response));
			
			//出错，直接返回
			if(response == null || response.getResult().equals("-1")) return null;
			
			//查询航班信息
			JPFlightQO qo = new JPFlightQO();
			qo.setDate(command.getDate());
			qo.setFlightNo(command.getFlightNo());
			List<FlightDTO> flightDTOs = jpFlightLocalService.queryFlight(qo);
			if (flightDTOs == null || flightDTOs.size() == 0) {
				return null;
			}
			
			//订单入库
			jpOrder = new JPOrder();
			FlightDTO flightDTO = flightDTOs.get(0);
			
			jpOrder.setOrderCode(response.getOrderCode());
			jpOrder.setPayAmount(response.getPayAmount());
			jpOrder.setTicketPrice(response.getTicketPrice());
			jpOrder.setSingleTaxAmount(response.getSingleTaxAmount());
			jpOrder.setPlatCode(response.getPlatCode());
			jpOrder.setAgencyName("票量商旅平台");
			
			jpOrder.setCreateDate(nowDate);
			jpOrder.setFlightDTO(flightDTO);
			jpOrder.setJpOrderSnapshot(JSON.toJSONString(flightDTO));
			String datestr=flightDTO.getStartDate() + " " + flightDTO.getStartTime();
			jpOrder.setFlightStartTime(datestr);
			jpOrder.setStatus(response.getStatus());
			jpOrder.setWastWorkTime(response.getWastWorkTime());
			jpOrder.setPnr(response.getPnr());
			jpOrder.initFromCommand(slfxCommand);
			jpOrder.setDealerOrderCode(dealerOrderCode);
			jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//待确认
			jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//待支付
			
			//退票时间和工作时间
			jpOrder.setWorkTime(response.getWorkTime());
			jpOrder.setRefundWorkTime(response.getRefundWorkTime());
			
			//hibernate4.2.4存在bug，级联持久化出错
			dao.save(jpOrder);
			
			//保存乘机人信息
			Set<JPPassanger> passangers = jpOrder.getPassangers();
			
			for (JPPassanger passanger : passangers) {
				for(FlightPassangerDto passenger : command.getPassangers()){
					passanger.setCompanyId(passenger.getCompanyId());
					passanger.setCompanyName(passenger.getCompanyName());
					passanger.setDepartmentId(passenger.getDepartmentId());
					passanger.setDepartmentName(passenger.getDepartmentName());
					passanger.setMemeberId(passenger.getMemeberId());
					
					jpPassangerLocalService.save(passanger);
				}
			}
			
			//保存成功后，设置下一条订单流水号
			this.setNextOrderSequence();
			
			//转化DTO
			jpOrderCreateDTO = BeanMapperUtils.getMapper().map(response, JPOrderCreateDTO.class);
			jpOrderCreateDTO.setDealerOrderCode(jpOrder.getDealerOrderCode());
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "JPOrderLocalService->queryFlightPolicy->exception[订单创建]:" + HgLogger.getStackTrace(e));
			return null;
		}
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderCreate->执行结束");
		return jpOrderCreateDTO;
	}
	
	//取消订单
	public JPOrderDTO cancelTicket(JPCancelTicketCommand command) {
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->开始执行");
		//非法，直接返回
		if (command == null || StringUtils.isBlank(command.getDealerOrderCode())) return null;
		
		JPCancelOrderTicketResponse response;
		JPOrderDTO jpOrderDTO = new JPOrderDTO();
		
		StringBuilder sb = new StringBuilder();
		JPOrder jpOrder = null;
		Set<JPPassanger> passangers = null;
		try {
			//通过商城订单号查询订单
			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setDealerOrderCode(command.getDealerOrderCode());
			jpOrder = dao.queryUnique(qo);
			
			if (jpOrder == null) {
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->：查询不到订单,dealerOrderCode:" + command.getDealerOrderCode());
				return null;
			}
			
			if (StringUtils.isNotBlank(command.getTicketNumbers())
					&& !String.valueOf(jpOrder.getStatus()).equals(JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL)//退/废失败
//					&& !String.valueOf(jpOrder.getStatus()).equals(JPOrderStatus.BACK_FAIL)
					&& !String.valueOf(jpOrder.getStatus()).equals(JPOrderStatusConstant.SHOP_TICKET_SUCC)) {
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->退废票：订单状态与退废票状态不符,status:" + jpOrder.getStatus());
				return null;
			} else if (StringUtils.isBlank(command.getTicketNumbers()) && (!String.valueOf(jpOrder.getPayStatus()).equals(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC)&&!String.valueOf(jpOrder.getPayStatus()).equals(JPOrderStatusConstant.SHOP_TICKET_NO_PAY))) {
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->订单取消：订单状态与支付状态状态不符,status:" + jpOrder.getStatus());
					return null;
			}
			
			passangers = jpOrder.getPassangers();
			if (passangers != null && StringUtils.isNotBlank(command.getTicketNumbers())) {
				for (JPPassanger passanger : passangers) {
					Hibernate.initialize(passanger);//初始化延迟加载的对象
					if (passanger == null) continue;
					sb.setLength(0);
					sb.append("【"+ SysProperties.getInstance().get("sms_sign", "票量旅游")+"】");
					if (jpOrder.getOrderUser() != null)
						sb.append(jpOrder.getOrderUser().getLoginName());
					sb.append(" 票号：");
					if (passanger.getTicket() != null)
						sb.append(passanger.getTicket().getTicketNo());
					sb.append("申请退票，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
					smsUtils.sendSms(jpOrder.getOrderUser().getMobile(), sb.toString());
				}
			}
			
			/**************************************************************************************/
			/***********远程请求开始*******************************************************************/
			//设置平台订单号
			command.setOrderId(jpOrder.getOrderCode());
			// 创建要发送的请求对象
			ApiRequest request = new ApiRequest("JPCancelTicket", ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), command);
			// 发送请求,调用分销平台
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->request[订单取消]:" + JSON.toJSONString(request));
			response = client.send(request, JPCancelOrderTicketResponse.class);
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->response[订单取消成功值>]:" +JSON.toJSONString(response));

			/***********远程请求结束*******************************************************************/
			/**************************************************************************************/
			//退废种类：退票（T），废票（F）
			String refundType =  command.getRefundType();
			String orderStatus = JPOrderStatusConstant.SHOP_TICKET_SUCC;
			//废票失败，直接返回
			if (response != null && response.getResult().equals("-1")) {
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->response:" + JSON.toJSONString(response));
				return null;
			}else{
				if (response != null&&response.getResult().equals("1")) { //退废成功
					if ("F".equals(refundType)) {
//						orderStatus = JPOrderStatus.APPLY_DISCARD;
						orderStatus = JPOrderStatusConstant.SHOP_TICKET_REFUND_DEALING;//退票处理中 
					} else if ("T".equals(refundType)){
						orderStatus = JPOrderStatusConstant.SHOP_TICKET_REFUND_DEALING;//退票处理中 
//						orderStatus = JPOrderStatus.APPLY_BACK;
					} else {
						orderStatus = JPOrderStatusConstant.SHOP_TICKET_CANCEL;
					}
				}
			}
			
			
			//取消订单
			jpOrder.setStatus(Integer.parseInt(orderStatus));
			dao.update(jpOrder);
			
			StringBuilder context = new StringBuilder("取消未支付订单");//操作日志内容
			
			if (passangers != null && StringUtils.isNotBlank(command.getTicketNumbers())) {
				String[] ticketNumbers = command.getTicketNumbers().split(",");
				for (String ticketNumber : ticketNumbers) {
					for (JPPassanger passanger : passangers) {
						if (passanger != null) {
							JPTicket ticket = passanger.getTicket();
							if (ticket != null && ticket.getTicketNo().equals(ticketNumber)) {
								JPOrderRefundLog refundLog = new JPOrderRefundLog();
								refundLog.setId(UUIDGenerator.getUUID());
								refundLog.setCardType(passanger.getCardType());
								refundLog.setCreateDate(new Date());
								refundLog.setIdCardNo(passanger.getCardNo());
								refundLog.setName(command.getName());
								refundLog.setStatus(Integer.parseInt(orderStatus));
								refundLog.setTicketNo(ticketNumber);
								refundLog.setPassanger(passanger);
								
								context.append(passanger.getName()).append(",");
								
								refundLogDao.save(refundLog);
								break;
							}
						}
					}
				}
			}
			
			//插入操作日志
			JPOrderOperationLog operationLog = new JPOrderOperationLog();
			operationLog.setId(UUIDGenerator.getUUID());
			operationLog.setContext(context.toString());
			operationLog.setCreateDate(new Date());
			operationLog.setName(command.getName());
			operationLog.setOrder(jpOrder);
			operationLog.setStatus(response.getStatus());
			operationLogDao.save(operationLog);
			//如果是取消订单不发送短信
			if (passangers != null&&!orderStatus.equals(JPOrderStatusConstant.SHOP_TICKET_CANCEL)) {
				int count = passangers.size();
				for (int i = 0; i < count; i++) {
					sb.setLength(0);
					sb.append("【"+SysProperties.getInstance().get("sms_sign")+"】您的订单").append(command.getDealerOrderCode()).append("退票成功，票款会在7-15个工作日内返回到您的账户，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
					smsUtils.sendSms(jpOrder.getOrderUser().getMobile(), sb.toString());
				}
			}
			jpOrderDTO.setStatus(response.getStatus());
		} catch (Exception e) {
			if (passangers != null) {
				int count = passangers.size();
				for (int i = 0; i < count; i++) {
					sb.setLength(0);
					sb.append("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单").append(command.getDealerOrderCode()).append("退票失败，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
					try {
						smsUtils.sendSms(jpOrder.getOrderUser().getMobile(), sb.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "JPOrderLocalService->cancelTicket->exception[订单取消]:" + HgLogger.getStackTrace(e));
			return null;
		}
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->cancelTicket->执行结束");
		return jpOrderDTO;
	}
	
	//查询订单
	public JPOrder queryOrderDetail(HslJPOrderQO qo) {
		JPOrder jpOrder = getDao().queryUnique(qo);
		if (jpOrder != null) {
			Set<JPPassanger> passangers = jpOrder.getPassangers();
			List<JPOrderOperationLog> orderOperationLogs = jpOrder.getOrderOperationLogs();
			jpOrder.setFlightDTO(JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightDTO.class));
			
			for (JPPassanger passanger : passangers) {
				Hibernate.initialize(passanger);
			}
			
			for (JPOrderOperationLog orderOperationLog : orderOperationLogs) {
				Hibernate.initialize(orderOperationLog);
			}
		}
		
		return jpOrder;
	}
	
	@SuppressWarnings("unchecked")
	public Pagination queryPaginationInit(Pagination pagination) {
		pagination = getDao().queryPagination(pagination);
		List<JPOrder> orders = (List<JPOrder>) pagination.getList();
		
		for (JPOrder order : orders) {
			if (order == null) continue;
			
			order.setFlightDTO(JSON.parseObject(order.getJpOrderSnapshot(), FlightDTO.class));
			
			Set<JPPassanger> passangers = order.getPassangers();
			for (JPPassanger passanger : passangers) {
				Hibernate.initialize(passanger);
			}
		}
		return pagination;
	}
	
	/**
	 * 出票完成处理
	 * @param command
	 * @return
	 */
	@Transactional
	public boolean orderTicketNotify(JPOrderCommand command) {
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->执行开始");
		//空指针，直接返回
		if (command == null || StringUtils.isBlank(command.getDealerOrderCode())){
			return false;
		}
		
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setDealerOrderCode(command.getDealerOrderCode());
		JPOrder jpOrder = getDao().queryUnique(qo);
		
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->订单查询->dealerOrderCode:" + command.getDealerOrderCode() + " jpOrder:" + jpOrder);
		
		if (jpOrder != null) {
			
			String flag = command.getFlag().trim();
			if ("Y".equalsIgnoreCase(flag)) {
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC));//已出票
			} else {
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));//出票失败
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC));//待退款
				jpOrder.setBackPrice(jpOrder.getPayAmount());//出票失败，退全款。
				jpOrder.setReturnedPrice(jpOrder.getPayAmount());
			}
			
//			getDao().update(jpOrder);
//			jpOrder.set
//			jpOrder.setAgencyName("xxxxxx");
			getDao().update(jpOrder);
			
			Set<JPPassanger> passangers = jpOrder.getPassangers();
			Iterator<JPPassanger> iterator = passangers.iterator();
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->订单查询->passangers.size:" + passangers.size());
			
			String[] tktNos = command.getTktNo();//出票票号数组
			int index = 0;//数组索引
			StringBuilder sb = new StringBuilder();
			FlightDTO flightDTO = JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightDTO.class);
			while (iterator.hasNext()) {
				
				JPPassanger passanger = iterator.next();
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->passanger:" + JSON.toJSONString(passanger));
				
				if (passanger != null) {
					JPTicket ticket = new JPTicket();
					ticket.setTicketNo(tktNos[index++]);
					ticket.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC));
					passanger.setTicket(ticket);
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->订单查询->passangers:" + JSONObject.toJSONString(passanger));
					jpPassangerLocalService.update(passanger);
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->订单查询->passangers:" + JSONObject.toJSONString(passanger));
					
					//发送短信
					try {
						sb.setLength(0);//清空sb
						
						if (jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC)) {
							sb.append("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】").append(flightDTO.getStartDate()).append(flightDTO.getStartPortName()+flightDTO.getStartTerminal()).append("到");
							sb.append(flightDTO.getEndPortName()+flightDTO.getEndTerminal()).append("的 ").append(flightDTO.getFlightNo()).append(" 航班（");
							sb.append(flightDTO.getStartDate()+flightDTO.getStartTime()+"起飞");
							sb.append("/").append(flightDTO.getEndDate()+flightDTO.getEndTime()+"到达");
							sb.append("）已出票。").append(passanger.getName()).append("票号：").append(passanger.getTicket().getTicketNo());
							sb.append("。请提前2个小时到机场办理乘机手续。详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
						} else if ((jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL))&&(jpOrder.getPayStatus()==Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT))) {
							//出票失败待退款
							sb.append("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】对不起，您的订单:").append(jpOrder.getDealerOrderCode());
							sb.append("出票失败，已申请退款，具体到账时间请咨询相应银行，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
						}
						smsUtils.sendSms(passanger.getMobile(), sb.toString());
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->正在发送出票完成短信->mobile:" + passanger.getMobile());
					} catch (Exception e) {
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->正在发送出票完成短信失败->mobile:" + passanger.getMobile()+HgLogger.getStackTrace(e));
					}
				}else{
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->关联乘机人失败，短信发送失败:" + command.getDealerOrderCode());
				}
				
				return true;
			}
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->没有乘机人passanger:" + JSON.toJSONString(passangers));
			
		}
		
		return false;
	}
	
	/**
	 * 退废票完成通知
	 * @param command
	 * @return
	 */
	public boolean orderRefundNotify(JPOrderCommand command) {
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->执行开始");
		if (command == null || StringUtils.isBlank(command.getDealerOrderCode())) return false;//空指针，直接返回
		
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setDealerOrderCode(command.getDealerOrderCode());
		JPOrder jpOrder = getDao().queryUnique(qo);
		
		if (jpOrder == null) {
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->查询不到订单->dealerOrderCode:" + command.getDealerOrderCode() + " jpOrder:" + jpOrder);
			return false;
		}
		
		HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->订单查询->dealerOrderCode:" + command.getDealerOrderCode() + " jpOrder:" + jpOrder);
		
		try {
			jpOrder.setStatus(command.getStatus());
			getDao().update(jpOrder);
			
			Set<JPPassanger> passangers = jpOrder.getPassangers();
				StringBuilder sb = new StringBuilder();
				for (JPPassanger passanger : passangers) {
					sb.setLength(0);//清空sb
					
					//出票成功或退票失败
					if (jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC)||jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL)) {
						sb.append("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单:").append(jpOrder.getDealerOrderCode());
						sb.append("退票失败，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
					} else if (jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC)&&jpOrder.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT)) {
						//退票成功待退款
						sb.append("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单:").append(jpOrder.getDealerOrderCode());
						sb.append("退票成功，票款会在7-15个工作日内返回到您的账户，详情请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。");
					}
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->正在发送退废票完成短信->mobile:" + passanger.getMobile());
					
					//发送短信
					try {
						smsUtils.sendSms(passanger.getMobile(), sb.toString());
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->正在发送退废票完成短信->mobile:" + passanger.getMobile());
					} catch (Exception e) {
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->正在发送退废票完成短信->exception:" + HgLogger.getStackTrace(e));
					}
				}
			
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderRefundNotify->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	public boolean updateOrderStatus(UpdateJPOrderStatusCommand command){
		if(command==null)
			return false;
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setDealerOrderCode(command.getDealerOrderCode());
		JPOrder jpOrder = getDao().queryUnique(qo);
		if(jpOrder==null)
			return false;
		HgLogger.getInstance().info("wuyg", "JPOrderLocalService->updateOrderStatus机票订单更新："+JSON.toJSONString(command));
		if(command.getPayStatus()!=null)
			jpOrder.setPayStatus(command.getPayStatus());
		if(command.getStatus()!=null)
			jpOrder.setStatus(command.getStatus());
		jpOrder.setBackPrice(command.getBackPrice());
		dao.update(jpOrder);
		return true;
	}
	/**
	 * @方法功能说明：修改订单状态
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月17日下午3:30:05
	 * @修改内容：
	 * @参数：@param dealerOrderCode
	 * @参数：@param orderStatus
	 * @参数：@param payStatus
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateOrderStatus(String dealerOrderCode, int orderStatus, int payStatus) {
		if (StringUtils.isBlank(dealerOrderCode)) { 
			return false;
		}
		HgLogger.getInstance().info("chenxy", "JPOrderLocalService->updateOrderStatus->[修改订单状态方法]->>经销商订单号："+dealerOrderCode+"status"+orderStatus+",payStatus:"+payStatus);
		boolean ret = false;
		try {
			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setDealerOrderCode(dealerOrderCode);
			JPOrder jpOrder = getDao().queryUnique(qo);
			
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->updateOrderStatus->[修改订单状态方法]->jpOrder:" + JSON.toJSONString(jpOrder));
			
			if (jpOrder != null) {
				if(orderStatus>-1)
					jpOrder.setStatus(orderStatus);//设置订单状态为已支付
				if(payStatus>-1)
					jpOrder.setPayStatus(payStatus);//设置订单的支付状态为已支付
				//当分销同步状态传值过来为已回款，默认 认为 向易购支付失败，钱在公司账户上面可以直接变为待退款，设置退款金额 @chenxy 2015-0709
				if(payStatus==Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT)){
					jpOrder.setBackPrice(jpOrder.getPayAmount());
				}
				HgLogger.getInstance().info("zhangka", "JPOrderLocalService->updateOrderStatus->[修改订单状态方法]->设置状态之后:" + JSON.toJSONString(jpOrder));
				getDao().update(jpOrder);
				ret = true;
			}
		} catch (Throwable e) {
			HgLogger.getInstance().info("zhangka", "JPOrderLocalService->updateOrderStatus->exception:" + HgLogger.getStackTrace(e));
		}
		
		return ret;
	}
	
	//获取订单流水号
	public int getOrderSequence() {
		Jedis jedis = null;
		String value = "0";
		try {
			jedis = jedisPool.getResource();
			value = jedis.get("jp_sequence");
			String date = jedis.get("jp_sequence_date");
			String today = this.getDateString("yyyyMMdd");
			
			if (StringUtils.isBlank(value) || StringUtils.isBlank(date) || !date.equals(today)) {
				value = "0";
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return Integer.parseInt(value);
	}
	
	//设置下一条订单流水号
	public void setNextOrderSequence() {
		Jedis jedis = null;
		int value = 0;
		
		try {
			jedis = jedisPool.getResource();
			value = this.getOrderSequence();
			
			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}
			
			jedis.set("jp_sequence", String.valueOf(value));
			jedis.set("jp_sequence_date", String.valueOf(this.getDateString("yyyyMMdd")));
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
	
	/*result_details格式为：第一笔交易#第二笔交易#第三笔交易...#第N笔交易。
	 	第N笔交易格式为：
		交易退款数据集$收费退款数据集|分润退款数据集|分润退款数据集|...|分润
		退款数据集$$退子交易
		
		“交易退款数据集”不可为空，其余数据皆可为空。
		
		交易退款数据集格式为：原付款支付宝交易号^退款总金额^退款状态。
	 */
	public boolean orderRefund(String resultDetails){
		if(StringUtils.isBlank(resultDetails))
			return false;
		boolean rt=true;
		String[] trades=resultDetails.split("#");
		for(String trade:trades){
			String str=trade.split("\\$")[0];//获得交易退款数据集
			String[] data=str.split("\\^");//按照 "原付款支付宝交易号^退款总金额^退款状态" 格式分割为数组
			String tradeno=data[0];
			String sum=data[1];
			String status=data[2];
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->付款支付宝交易号:"+tradeno);
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->退款总金额:"+sum);
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->退款状态"+status);
			HslJPOrderQO qo=new HslJPOrderQO();
			qo.setPayTradeNo(tradeno);
			try {
				JPOrder jporder=dao.queryUnique(qo);
				if(jporder==null||!status.trim().equals("SUCCESS")){
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->jporder is null?"+jporder==null?"null":"not null"+",status:"+status);
					continue;
				}
				jporder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC));//已退款
				jporder.setReturnedPrice(Double.parseDouble(sum));
				HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->jporder:"+JSON.toJSONString(jporder));
				dao.update(jporder);
				//退还卡券
				HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券");
				HslCouponQO couponqo=new HslCouponQO();
				couponqo.setOrderId(jporder.getDealerOrderCode());
				List<Coupon> list=couponDao.queryList(couponqo);
				if(list.isEmpty())
					continue;
				Coupon c=list.get(0);
				double value=c.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
				//卡券面额大于退款金额，不返还卡券
				if(jporder.getBackPrice()-value>0){
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券->退款金额大于卡券面额，返还卡券");
					//返还卡券
					OrderRefundCommand cmd=new OrderRefundCommand();
					cmd.setOrderId(jporder.getDealerOrderCode());
					couponLocalService.orderRefund(cmd);
				}else{
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券->卡券面额大于退款金额，不返还卡券");
				}
			} catch (NumberFormatException e) {
				HgLogger.getInstance().error("wuyg", "JPOrderLocalService->OrderRefund->NumberFormatException:"+HgLogger.getStackTrace(e));
				rt=false;
			}catch (Exception e) {
				HgLogger.getInstance().error("wuyg", "JPOrderLocalService->OrderRefund->Exception:"+HgLogger.getStackTrace(e));
				rt=false;
			}
		}
		return rt;
	}
	
	
//	/*	单元测试使用*/
//	public void testQueryOrUpdateJPOrder(){
//		
//		String msg ="{\"dealerOrderCode\":\"A902172830000000\",\"flag\":\"Y\",\"notifyUrl\":\"http://183.129.207.5:8380/api/ticket/notify\",\"passangers\":[],\"status\":22,\"tktNo\":[\"7312391511111\"],\"ygOrderNo\":\"4101310376106\"}";
//		
//		JPOrderCommand command = JSON.parseObject(msg, JPOrderCommand.class);
//	
//		HslJPOrderQO qo = new HslJPOrderQO();
//		qo.setDealerOrderCode("A902172830000000");
//		//qo.setPageNo(0);
//		//qo.setPageSize(1);
//		JPOrder jpOrder = getDao().queryUnique(qo);
//		//JPOrder jpOrder = jpOrderLocalService.queryOrder(qo).get(0);
//
//		if (jpOrder != null) {
//			
//			String flag = command.getFlag().trim();
//			if ("Y".equalsIgnoreCase(flag)) {
//				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC));
//			} else {
//				//出票失败待退款
//				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
//				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
//			}
//			Set<JPPassanger> passangers = jpOrder.getPassangers();
//			Iterator<JPPassanger> iterator = passangers.iterator();
//			
//			String[] tktNos = command.getTktNo();//出票票号数组
//			int index = 0;//数组索引
//			while (iterator.hasNext()) {
//				JPPassanger passanger = iterator.next();
//				if (passanger != null) {
//					JPTicket ticket = new JPTicket();
//					ticket.setTicketNo(tktNos[index++]);
//					ticket.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC));
//					passanger.setTicket(ticket);
//					
//					jpPassangerLocalService.update(passanger);
//					
//				}
//			}
//			
//			getDao().update(jpOrder);
//		}
//	}
	
	
	/**
	 * 返回差旅dto
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryTravelDtoPagination(Pagination pagination){
		Pagination p1=queryPagination(pagination);
		p1.setList(changeJPToTravelDto((List<JPOrder>) p1.getList()));
		return p1;
	}
	private SimpleDateFormat dateformater1=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private SimpleDateFormat dateformater2=new SimpleDateFormat("yyyy/MM/dd hh:mm");
	/**
	 * 转化机票为travelDTO，没有设置职务
	 * @param jplist
	 * @return
	 */
	private List<TravelDTO> changeJPToTravelDto(List<JPOrder> jplist){
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		for(JPOrder jpOrder:jplist){
			Set<JPPassanger> jpPassangers=jpOrder.getPassangers();
			FlightDTO flightDTO=JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightDTO.class);
			for(JPPassanger jpPassanger:jpPassangers){
				TravelDTO travelDTO=new TravelDTO();
				travelDTO.setCompanyName(jpPassanger.getCompanyName());
				travelDTO.setDeptName(jpPassanger.getDepartmentName());
				travelDTO.setDestination(flightDTO.getEndPortName());//目的地
//				HslMemberQO qo=new HslMemberQO();
//				qo.setId(jpPassanger.getMemeberId());
//				Member member=companyLocalService.getMember(qo);
//				travelDTO.setJob(member.getJob());//职务
				travelDTO.setId(jpPassanger.getMemeberId());//设置成员id
				travelDTO.setMemberName(jpPassanger.getName());
				travelDTO.setOrderNum(jpOrder.getDealerOrderCode());
				travelDTO.setPrice(jpOrder.getTicketPrice()+jpOrder.getSingleTaxAmount());//票面价+税款
				travelDTO.setProjectType(1);
				String timestr=flightDTO.getStartDate()+" "+flightDTO.getStartTime();
				Date time=null;
				try {
					if(timestr.indexOf("-")>0){
						time=dateformater1.parse(timestr);
					}else if(timestr.indexOf("/")>0){
						time=dateformater2.parse(timestr);
					}else{
						HgLogger.getInstance().error("wuyg", "unknown parttern time:"+timestr);
						time=new Date();
					}
				} catch (ParseException e) {
					HgLogger.getInstance().error("wuyg", "ParseException:"+timestr);
					e.printStackTrace();
					continue;
				}
				travelDTO.setTarvelDate(time);
				list.add(travelDTO);
			}
		}
		return list;
	}
	
	public JPOrderDTO queryOrderOne(HslJPOrderQO qo){
		return BeanMapperUtils.getMapper().map(this.queryOrderDetail(qo), JPOrderDTO.class);
	}
}

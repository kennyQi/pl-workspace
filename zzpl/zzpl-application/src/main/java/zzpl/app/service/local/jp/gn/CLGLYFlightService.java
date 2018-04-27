package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.DepartmentDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.service.local.convert.FlightOrderConver;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.pojo.command.jp.BookGNFlightCommand;
import zzpl.pojo.command.jp.CancelGNTicketCommand;
import zzpl.pojo.command.jp.plfx.gn.CancelTicketGNCommand;
import zzpl.pojo.command.jp.plfx.gn.RefundTicketGNCommand;
import zzpl.pojo.dto.jp.plfx.gn.CancelTicketGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.RefundTicketGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.jp.plfx.gn.JPPolicyGNQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class CLGLYFlightService extends
BaseServiceImpl<FlightOrder, FlightOrderQO, FlightOrderDAO> {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private DepartmentDAO departmentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private QueryPolicyService queryPolicyService;
	@Autowired
	private ApprovalGNFlightService approvalGNFlightService;
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * @Title: clglyBookFlight 
	 * @author guok
	 * @Description: 差旅管理员直接下本地订单
	 * @Time 2015年10月12日上午8:57:57
	 * @param bookGNFlightCommand void 设定文件
	 * @throws
	 */
	public FlightOrder clglyBookFlight(BookGNFlightCommand bookGNFlightCommand) {
		HgLogger.getInstance().info("cs", "【FlightOrderService】【clglyBookFlight】"+"bookGNFlightCommand:"+JSON.toJSONString(bookGNFlightCommand));
		//1:保存订单信息
		FlightOrder flightOrder = new FlightOrder();;
		try {
			flightOrder=JSON.parseObject(JSON.toJSONString(bookGNFlightCommand), FlightOrder.class);
			String orderID = UUIDGenerator.getUUID();
			flightOrder.setId(orderID);
			flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS);
			flightOrder.setPayType(FlightOrder.PAY_BY_TRAVEL.toString());
			flightOrder.setTickType(Integer.parseInt(SysProperties.getInstance().get("ticketType")));
			flightOrderDAO.save(flightOrder);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyBookFlight】flightOrder:"+JSON.toJSONString(flightOrder));
			//保存游客信息
			List<GNPassengerDTO> passengerList = JSON.parseArray(bookGNFlightCommand.getPassengerListJSON(), GNPassengerDTO.class);
			for (GNPassengerDTO gnPassengerDTO : passengerList) {
				Passenger passenger = new Passenger();
				passenger.setId(UUIDGenerator.getUUID());
				passenger.setIdNO(gnPassengerDTO.getIdNO());
				passenger.setIdType(gnPassengerDTO.getIdType());
				passenger.setOrderType(Passenger.GN_ORDER);
				passenger.setPassengerName(gnPassengerDTO.getPassengerName());
				passenger.setPassengerType(gnPassengerDTO.getPassengerType());
				passenger.setTelephone(gnPassengerDTO.getTelephone());
				passenger.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS.toString());
				passenger.setFlightOrder(flightOrder);
				HgLogger.getInstance().info("cs", "【FlightOrderService】【clglyBookFlight】【passenger】"+JSON.toJSONString(passenger));
				passengerDAO.save(passenger);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("cs", "【FlightOrderService】【clglyBookFlight】【Exception】"+e.getMessage());
		}
		return flightOrder;
	}
	
	/**
	 * @Title: clglyPrintTicket 
	 * @author guok
	 * @Description: 差旅管理员自有订票去分销下订单获取价格
	 * @Time 2015年10月12日上午10:16:33
	 * @param orderID
	 * @return
	 * @throws Exception FlightOrder 设定文件
	 * @throws
	 */
	public FlightOrder clglyPrintTicket(String orderID) throws Exception {

		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setId(orderID);
		FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
		HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyPrintTicket】flightOrder:"+JSON.toJSONString(flightOrder));
		if(flightOrder.getStatus()==FlightOrderStatus.APPROVAL_SUCCESS||Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF)
			throw new Exception();
		/*PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<Passenger> passengers = passengerDAO.queryList(passengerQO);*/
		//根据订单信息组装查询订单所有的政策信息，进行验证
		JPPolicyGNQO jpPolicyGNQO = new JPPolicyGNQO();
		jpPolicyGNQO.setEncryptString(flightOrder.getEncryptString());
		jpPolicyGNQO.setTickType(flightOrder.getTickType());
		flightOrder=queryPolicyService.queryPolicy(flightOrder.getId(),jpPolicyGNQO);
		/*JPQueryHighPolicyGNDTO jqHighPolicyGNDTO = new JPQueryHighPolicyGNDTO();
		try {
			jqHighPolicyGNDTO = gnFlightService.queryHighPolicyGN(jpPolicyGNQO);
			BigDecimal sum = new BigDecimal(0);
			BigDecimal ibePrice = new BigDecimal(jqHighPolicyGNDTO.getPricesGNDTO().getIbePrice());
			sum=sum.add(ibePrice);
			BigDecimal oilFee = new BigDecimal(jqHighPolicyGNDTO.getOilFee());
			sum=sum.add(oilFee);
			BigDecimal buildFee = new BigDecimal(jqHighPolicyGNDTO.getBuildFee());
			sum=sum.add(buildFee);
			flightOrder.setIbePrice(ibePrice.toString());
			flightOrder.setOilFee(jqHighPolicyGNDTO.getOilFee());
			flightOrder.setBuildFee(jqHighPolicyGNDTO.getBuildFee());
			flightOrder.setCommitPrice(sum.doubleValue());
			flightOrder.setPlcid(jqHighPolicyGNDTO.getPricesGNDTO().getPlcid()+"");
			flightOrder.setPassengers(null);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】【execute】flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【FlightOrderService】【printTicket】【execute】政策查询失败:" + HgLogger.getStackTrace(e));
			flightOrder.setTotalPrice(-1.0);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】去分销差政策失败"+"flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			throw new Exception();
		}
		//组装下单Command
		JPBookTicketGNCommand jpBookTicketGNCommand = new JPBookTicketGNCommand(); 
		jpBookTicketGNCommand.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
		jpBookTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
		jpBookTicketGNCommand.setFlightNo(flightOrder.getFlightNO());
		jpBookTicketGNCommand.setStartDate(flightOrder.getStartTime());
		if (StringUtils.isNotBlank(flightOrder.getCabinCode())) {
			jpBookTicketGNCommand.setCabinCode(flightOrder.getCabinCode());
		}
		if (StringUtils.isNotBlank(flightOrder.getCabinName())) {
			jpBookTicketGNCommand.setCabinName(flightOrder.getCabinName());
		}
		flightOrder.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
		//联系人及乘机人信息
		PassengerInfoGNDTO passengerInfoGNDTO = new PassengerInfoGNDTO();
		passengerInfoGNDTO.setName(flightOrder.getLinkName());
		passengerInfoGNDTO.setTelephone(flightOrder.getLinkTelephone());
		List<PassengerGNDTO> passengerGNDTOs = new ArrayList<PassengerGNDTO>();
		for (Passenger passenger : passengers) {
			PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
			passengerGNDTO.setPassengerName(passenger.getPassengerName());
			passengerGNDTO.setIdType(passenger.getIdType());
			passengerGNDTO.setIdNo(passenger.getIdNO());
			passengerGNDTO.setPassengerType(passenger.getPassengerType());
			passengerGNDTOs.add(passengerGNDTO);
		}
		passengerInfoGNDTO.setPassengerList(passengerGNDTOs);
		jpBookTicketGNCommand.setPassengerInfoGNDTO(passengerInfoGNDTO);
		//去分销下订单
		JPBookOrderGNDTO jpBookOrderGNDTO = new JPBookOrderGNDTO();
		try {
			jpBookOrderGNDTO = gnFlightService.bookOrderGN(jpBookTicketGNCommand);
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【FlightOrderService】【printTicket】下单失败:" + HgLogger.getStackTrace(e));
			flightOrder.setTotalPrice(-1.0);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】去分销下单失败"+"flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			throw new Exception();
		}
		HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】下单成功:"+JSON.toJSONString(jpBookOrderGNDTO));
		flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
		*//**
		 * 2015年9月9日16:34:47 
		 * 因为要把 按照票面价 卖票 所以要把订单中的 经销商价格 改为票面价 这样 结算中心取数据时 会去取这个票面价格 结算价格同样就会更新到这个价格 
		 * 因为 票面价与成本价 会有差额 这部分差额就是中智票量所盈利的部分
		 *//*
		
		 * *
		flightOrder.setPlatTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getPlatTotalPirce());
		 
		flightOrder.setPlatTotalPrice(flightOrder.getCommitPrice());
		flightOrder.setTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getTotalPrice());
		flightOrder.setMemo(jpBookOrderGNDTO.getPriceGNDTO().getMemo());
		flightOrder.setPlcid(String.valueOf(jpBookOrderGNDTO.getPriceGNDTO().getPlcid()));
		flightOrder.setPayPlatform(jpBookOrderGNDTO.getPriceGNDTO().getPayType());
		flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
		HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
		flightOrderDAO.update(flightOrder);*/
		return flightOrder;
	}
	
	/**
	 * @Title: selectOrderList 
	 * @author guok
	 * @Description: 下单时订单查询（差旅管理员自有订票）
	 * @Time 2015年10月12日下午5:17:14
	 * @param flightOrderQO
	 * @return List<FlightOrder> 设定文件
	 * @throws
	 */
	public List<FlightOrder> selectOrderList(FlightOrderQO flightOrderQO) {
		List<FlightOrder> flightOrders = flightOrderDAO.queryList(flightOrderQO);
		for (FlightOrder flightOrder : flightOrders) {
			Hibernate.initialize(flightOrder.getPassengers());
		}
		return flightOrders;
	}
	
	/**
	 * @Title: payOrder 
	 * @author guok
	 * @Description: 差旅管理员自有订票去分销系统支付订单
	 * @Time 2015年10月12日下午5:57:00
	 * @param flightOrderID
	 * @throws Exception void 设定文件
	 * @throws
	 */
	public void payOrder(String flightOrderID) throws Exception {
		HgLogger.getInstance().info("gk", "【FlightOrderService】"+"flightOrder:"+flightOrderID);
		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setId(flightOrderID);
		FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
		HgLogger.getInstance().info("gk", "【FlightOrderService】【payOrder】【flightOrder】:"+JSON.toJSONString(flightOrder));
		
		String result = approvalGNFlightService.bookandpay(flightOrder); 
		if(result!=CommonService.SUCCESS){
			throw new Exception();
		}
		
		/*flightOrder.setTicketChannel(0);
		flightOrder.setTicketChannelName("中航信");
		if(flightOrder.getTotalPrice()==-1.0){
			throw new Exception();
		}
		if(flightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_SUCCESS||flightOrder.getPayStatus()==FlightPayStatus.PAY_SUCCESS){
			throw new Exception();
		}
		//组装付款信息
		PayToJPOrderGNCommand payToJPOrderGNCommand = new PayToJPOrderGNCommand();
		payToJPOrderGNCommand.setDealerOrderId(flightOrder.getOrderNO());
		payToJPOrderGNCommand.setPayPlatform(flightOrder.getPayPlatform());
		payToJPOrderGNCommand.setTotalPrice(flightOrder.getTotalPrice());
		HgLogger.getInstance().info("gk", "【FlightOrderService】【payOrder】:"+JSON.toJSONString(payToJPOrderGNCommand));
		PayToJPOrderGNDTO payToJPOrderGNDTO = new PayToJPOrderGNDTO();
		try {
			payToJPOrderGNDTO = gnFlightService.payToOrderGN(payToJPOrderGNCommand);
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【FlightOrderService】【payOrder】扣款失败:" + HgLogger.getStackTrace(e));
			flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
			flightOrder.setPayStatus(FlightPayStatus.PAY_FAILED);
			flightOrderDAO.update(flightOrder);
			
			throw new Exception();
		}
		HgLogger.getInstance().info("gk", "【FlightOrderService】【payOrder】扣款成功:"+JSON.toJSONString(payToJPOrderGNDTO));
		
		flightOrder.setPrintTicketType(FlightOrder.AUTO_VOTE_PRIMT_TICKET);
		flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
		flightOrder.setPayStatus(FlightPayStatus.PAY_SUCCESS);
		HgLogger.getInstance().info("gk", "【FlightOrderService】自动出票付款成功："+"flightOrder:"+JSON.toJSONString(flightOrder));
		flightOrderDAO.update(flightOrder);
		PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<Passenger> passengers = passengerDAO.queryList(passengerQO);
		for (Passenger passenger : passengers) {
			passenger.setStatus(FlightOrderStatus.APPROVAL_SUCCESS.toString());
			passengerDAO.update(passenger);
		}*/
	}
	
	/**
	 * @Title: refundTicket 
	 * @author guok
	 * @Description: 差旅管理员自有订票退票
	 * @Time 2015年10月14日上午9:26:52
	 * @param orderID
	 * @param command
	 * @throws Exception void 设定文件
	 * @throws
	 */
	public void clglyRefundTicket(CancelGNTicketCommand command) throws Exception {
		//查询订单
		FlightOrder flightOrder = flightOrderDAO.get(command.getOrderID());
		HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】【flightOrder】:"+JSON.toJSONString(flightOrder));
		Jedis jedis = jedisPool.getResource();
		String cancelstatus = jedis.get("ZHIXING_"+flightOrder.getId()+"CANCEL");
		HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】:"+cancelstatus);
		if(StringUtils.endsWith(cancelstatus, "canceling")){
			HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】正在退票");
			throw new Exception();
		}else{
			HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】去退票");
			jedis.setex("ZHIXING_"+flightOrder.getId()+"CANCEL",2592000 , "canceling");
		}
		//查询乘机人
		PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<Passenger> passengers = passengerDAO.queryList(passengerQO);
		for (Passenger passenger : passengers) {
			//判断状态
			//一、审批通过，未出票
			if (StringUtils.equals(passenger.getStatus(), FlightOrderStatus.APPROVAL_SUCCESS.toString()) || StringUtils.equals(passenger.getStatus(), FlightOrderStatus.CONFIRM_ORDER_SUCCESS.toString())) {
				
				//组装提交数据
				CancelTicketGNCommand cancelTicketGNCommand = JSON.parseObject(JSON.toJSONString(command), CancelTicketGNCommand.class);
				cancelTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
				cancelTicketGNCommand.setPassengerName(passenger.getPassengerName());
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】【cancelTicketGNCommand】:"+JSON.toJSONString(cancelTicketGNCommand));
				
				CancelTicketGNDTO cancelTicketGNDTO = new CancelTicketGNDTO();
				try {
					cancelTicketGNDTO = gnFlightService.cancelTicketGN(cancelTicketGNCommand);
				} catch (GNFlightException e) {
					HgLogger.getInstance().error("gk", "【FlightOrderService】【clglyRefundTicket】取消订单失败:" + HgLogger.getStackTrace(e));
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
					HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】取消订单失败:"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					passengerDAO.update(passenger);
					throw new Exception();
				}
				
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】【cancelTicketGNDTO】:"+JSON.toJSONString(cancelTicketGNDTO));
				//根据返回结果修改订单表数据
				flightOrder.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS);
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】取消订单成功:"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				passenger.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS.toString());
				passengerDAO.update(passenger);
				
			}
			
			//二、出票成功 ，调分销退费票
			if(StringUtils.equals(passenger.getStatus(), FlightOrderStatus.PRINT_TICKET_SUCCESS.toString())){
				//组装提交数据
				RefundTicketGNCommand refundTicketGNCommand = JSON.parseObject(JSON.toJSONString(command), RefundTicketGNCommand.class);
				refundTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
				refundTicketGNCommand.setRefundType(command.getRefundType());
				refundTicketGNCommand.setRefundMemo(command.getRefundMemo());
				refundTicketGNCommand.setPassengerName(passenger.getPassengerName());
				refundTicketGNCommand.setAirId(passenger.getAirID());
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】"+"refundTicketGNCommand:"+JSON.toJSONString(refundTicketGNCommand));
				
				RefundTicketGNDTO refundTicketGNDTO = new RefundTicketGNDTO();
				try {
					refundTicketGNDTO = gnFlightService.refundTicketGN(refundTicketGNCommand);
				} catch (GNFlightException e) {
					HgLogger.getInstance().error("gk", "【FlightOrderService】【clglyRefundTicket】取消订单失败:" + HgLogger.getStackTrace(e));
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
					HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】取消订单失败:"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					passenger.setRefundType(command.getRefundType());
					passenger.setRefundMemo(command.getRefundMemo());
					passengerDAO.update(passenger);
					throw new Exception();
				}
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】【refundTicketGNDTO】:"+JSON.toJSONString(refundTicketGNDTO));
				flightOrder.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS);
				HgLogger.getInstance().info("gk", "【FlightOrderService】【clglyRefundTicket】取消订单成功:"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				passenger.setRefundType(command.getRefundType());
				passenger.setRefundMemo(command.getRefundMemo());
				passenger.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS.toString());
				passengerDAO.update(passenger);
				
			}
		}
	}
	
	/**
	 * @Title: queryOrder 
	 * @author guok
	 * @Description: 查询订单
	 * @Time 2015年10月19日上午11:14:50
	 * @param orderID
	 * @return FlightOrder 设定文件
	 * @throws
	 */
	public FlightOrder queryOrder(String orderID) {
		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setId(orderID);
		FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
		Hibernate.initialize(flightOrder.getPassengers());
		return flightOrder;
	}
	
	/**
	 * @Title: getList 
	 * @author guok
	 * @Description: 订单列表转换
	 * @Time 2015年8月27日下午3:22:02
	 * @param pagination
	 * @return Pagination 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination getList(Pagination pagination) {
		pagination = flightOrderDAO.queryPagination(pagination);
		pagination.setList(FlightOrderConver.listConvert((List<FlightOrder>)pagination.getList(), passengerDAO,companyDAO,userDAO));
		return pagination;
	}
	@Override
	protected FlightOrderDAO getDao() {
		return flightOrderDAO;
	}

}

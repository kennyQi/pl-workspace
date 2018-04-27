package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.plfx.gn.JPBookTicketGNCommand;
import zzpl.pojo.command.jp.plfx.gn.PayToJPOrderGNCommand;
import zzpl.pojo.dto.jp.plfx.gn.JPBookOrderGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.PassengerGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.PassengerInfoGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.PayToJPOrderGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：自动出票 去易行进行下单 支付操作
 * @类修改者：
 * @修改日期：2015年12月24日下午3:00:13
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午3:00:13
 */
@Component("ApprovalGNFlightService")
public class ApprovalGNFlightService implements CommonService {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private JedisPool jedisPool;

	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】"+JSON.toJSONString(command));
		//查询订单
		WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
		workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
		List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
		for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setId(workflowInstanceOrder.getOrderID());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
			Jedis jedis = jedisPool.getResource();
			String bookstatus = jedis.get("ZHIXING_"+flightOrder.getId()+"BOOK");
			HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】【bookstatus】:"+bookstatus);
			if(StringUtils.endsWith(bookstatus, "booking")){
				HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】【bookstatus】正在出票");
				return "101;正在出票，请耐心等候";
			}else{
				HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】【bookstatus】去出票");
				jedis.setex("ZHIXING_"+flightOrder.getId()+"BOOK",2592000 , "booking");
				String result = bookandpay(flightOrder);
				jedis.setex("ZHIXING_"+flightOrder.getId()+"BOOK",2592000 , "booked");
				jedis.del("ZHIXING_"+flightOrder.getId()+"BOOK");
				jedisPool.returnResource(jedis);
				if(result!=CommonService.SUCCESS){
					return result;
				}
			}
			
		}
		return CommonService.SUCCESS;
	}
	
	/**
	 * 
	 * @方法功能说明：去分销下单并支付
	 * @修改者名字：cangs
	 * @修改时间：2015年12月25日上午10:07:57
	 * @修改内容：
	 * @参数：@param flightOrder
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String bookandpay(FlightOrder flightOrder){
		PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<Passenger> passengers = passengerDAO.queryList(passengerQO);
		
		if(flightOrder.getStatus()==FlightOrderStatus.GET_POLICY_FAIL){
			return  "100;支付失败，请尝试手动出票";
		}
		
		if(flightOrder.getStatus()==FlightOrderStatus.GET_POLICY_SUCCESS){
			JPBookTicketGNCommand jpBookTicketGNCommand = new JPBookTicketGNCommand(); 
			jpBookTicketGNCommand.setEncryptString(flightOrder.getEncryptString());
			jpBookTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
			jpBookTicketGNCommand.setFlightNo(flightOrder.getFlightNO());
			jpBookTicketGNCommand.setStartDate(flightOrder.getStartTime());
			if (StringUtils.isNotBlank(flightOrder.getCabinCode())) {
				jpBookTicketGNCommand.setCabinCode(flightOrder.getCabinCode());
			}
			if (StringUtils.isNotBlank(flightOrder.getCabinName())) {
				jpBookTicketGNCommand.setCabinName(flightOrder.getCabinName());
			}
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
				HgLogger.getInstance().error("gk", "【FlightOrderService】【bookandpay】下单失败:" + HgLogger.getStackTrace(e));
				flightOrder.setTotalPrice(-1.0);
				HgLogger.getInstance().info("gk", "【FlightOrderService】【bookandpay】去分销下单失败"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				return  e.getCode().toString()+";"+e.getMessage();
			}
			HgLogger.getInstance().info("gk", "【FlightOrderService】【bookandpay】下单成功:"+JSON.toJSONString(jpBookOrderGNDTO));
			flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
			/*
			 * 2015年9月9日16:34:47 
			 * 因为要把 按照票面价 卖票 所以要把订单中的 经销商价格 改为票面价 这样 结算中心取数据时 会去取这个票面价格 结算价格同样就会更新到这个价格 
			 * 因为 票面价与成本价 会有差额 这部分差额就是中智票量所盈利的部分
			 */
			/*
			 * *
			flightOrder.setPlatTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getPlatTotalPirce());
			 */
			flightOrder.setPlatTotalPrice(flightOrder.getCommitPrice());
			flightOrder.setTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getTotalPrice());
			flightOrder.setMemo(flightOrder.getMemo()+jpBookOrderGNDTO.getPriceGNDTO().getMemo());
			flightOrder.setPlcid(String.valueOf(jpBookOrderGNDTO.getPriceGNDTO().getPlcid()));
			flightOrder.setPayPlatform(jpBookOrderGNDTO.getPriceGNDTO().getPayType());
			HgLogger.getInstance().info("gk", "【FlightOrderService】【bookandpay】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
		}
		/*else if (flightOrder.getStatus()!=FlightOrderStatus.PRINT_TICKET_FAILED&&flightOrder.getStatus()!=FlightOrderStatus.APPROVAL_SUCCESS){
			return  "100;支付失败，请尝试手动出票";
		}*/
		
		
		
		
		
		//组装付款信息
		PayToJPOrderGNCommand payToJPOrderGNCommand = new PayToJPOrderGNCommand();
		payToJPOrderGNCommand.setDealerOrderId(flightOrder.getOrderNO());
		payToJPOrderGNCommand.setPayPlatform(flightOrder.getPayPlatform());
		payToJPOrderGNCommand.setTotalPrice(flightOrder.getTotalPrice());
		HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】:"+JSON.toJSONString(payToJPOrderGNCommand));
		PayToJPOrderGNDTO payToJPOrderGNDTO = new PayToJPOrderGNDTO();
		try {
			payToJPOrderGNDTO = gnFlightService.payToOrderGN(payToJPOrderGNCommand);
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【ApprovalGNFlightService】【execute】扣款失败:" + HgLogger.getStackTrace(e));
			flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
			flightOrder.setPayStatus(FlightPayStatus.PAY_FAILED);
			flightOrderDAO.update(flightOrder);
			
			
			HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】付款失败 开始更新结算中心");
				/*
				 * 更改 退费订单所关联的 结算中心价格 新增订单流转记录信息
				 */
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
//				cosaof.setRefundPrice(cosaof.getPlatTotalPrice());
//				Double sum1 = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
//				cosaof.setCasaofPrice(sum1);
				HgLogger.getInstance().info("cs", "【自动支付失败】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
				cosaofdao.update(cosaof);
			}
			
			return  e.getCode().toString()+";"+e.getMessage();
		}
		HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】【execute】扣款成功:"+JSON.toJSONString(payToJPOrderGNDTO));
		flightOrder.setTicketChannel(0);
		flightOrder.setTicketChannelName("中航信");
		flightOrder.setPrintTicketType(FlightOrder.AUTO_VOTE_PRIMT_TICKET);
		flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
		flightOrder.setPayStatus(FlightPayStatus.PAY_SUCCESS);
		HgLogger.getInstance().info("gk", "【ApprovalGNFlightService】自动出票付款成功："+"flightOrder:"+JSON.toJSONString(flightOrder));
		flightOrderDAO.update(flightOrder);
		for (Passenger passenger : passengers) {
			passenger.setStatus(FlightOrderStatus.APPROVAL_SUCCESS.toString());
			passengerDAO.update(passenger);
		}
		return CommonService.SUCCESS;
	}
}

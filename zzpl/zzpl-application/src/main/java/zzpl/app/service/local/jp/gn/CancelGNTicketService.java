package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.plfx.gn.CancelTicketGNCommand;
import zzpl.pojo.command.jp.plfx.gn.RefundTicketGNCommand;
import zzpl.pojo.dto.jp.plfx.gn.CancelTicketGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.RefundTicketGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

@Component("CancelGNTicketService")
public class CancelGNTicketService implements CommonService {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private PushService pushService;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 给分销发送退废票
	 * @Time 2015年7月21日 14:19:30
	 * @author guok
	 */
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
			
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
			List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				//查询订单
				FlightOrder flightOrder = flightOrderDAO.get(workflowInstanceOrder.getOrderID());
				HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				Jedis jedis = jedisPool.getResource();
				String cancelstatus = jedis.get("ZHIXING_"+flightOrder.getId()+"CANCEL");
				HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】:"+cancelstatus);
				if(StringUtils.endsWith(cancelstatus, "canceling")){
					HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【flightOrder】正在退票");
					return "101;正在退票，请耐心等候";
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
					if (StringUtils.equals(passenger.getStatus(), FlightOrderStatus.APPROVAL_SUCCESS.toString())) {
						
						//组装提交数据
						CancelTicketGNCommand cancelTicketGNCommand = JSON.parseObject(JSON.toJSONString(command), CancelTicketGNCommand.class);
						cancelTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
						cancelTicketGNCommand.setPassengerName(passenger.getPassengerName());
						HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【cancelTicketGNCommand】:"+JSON.toJSONString(cancelTicketGNCommand));
						
						CancelTicketGNDTO cancelTicketGNDTO = new CancelTicketGNDTO();
						try {
							cancelTicketGNDTO = gnFlightService.cancelTicketGN(cancelTicketGNCommand);
						} catch (GNFlightException e) {
							HgLogger.getInstance().error("gk", "【CancelGNTicketService】【execute】取消订单失败:" + HgLogger.getStackTrace(e));
							flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
							HgLogger.getInstance().info("cs", "【CancelGNTicketService】【execute】取消订单失败:"+"flightOrder:"+JSON.toJSONString(flightOrder));
							flightOrderDAO.update(flightOrder);
							passengerDAO.update(passenger);
							return  e.getCode().toString()+";"+e.getMessage();
						}
						
						HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【cancelTicketGNDTO】:"+JSON.toJSONString(cancelTicketGNDTO));
						//根据返回结果修改订单表数据
						flightOrder.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS);
						HgLogger.getInstance().info("cs", "【CancelGNTicketService】【execute】取消订单成功:"+"flightOrder:"+JSON.toJSONString(flightOrder));
						flightOrderDAO.update(flightOrder);
						passenger.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS.toString());
						passengerDAO.update(passenger);
						
					}
					
					//二、出票成功 ，调分销退费票
					if(StringUtils.equals(passenger.getStatus(), FlightOrderStatus.PRINT_TICKET_SUCCESS.toString())){
						//组装提交数据
						RefundTicketGNCommand refundTicketGNCommand = JSON.parseObject(JSON.toJSONString(command), RefundTicketGNCommand.class);
						refundTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
						refundTicketGNCommand.setRefundType(passenger.getRefundType());
						refundTicketGNCommand.setRefundMemo(passenger.getRefundMemo());
						refundTicketGNCommand.setPassengerName(passenger.getPassengerName());
						refundTicketGNCommand.setAirId(passenger.getAirID());
						HgLogger.getInstance().info("gk", "【CancelGNTicketService】"+"refundTicketGNCommand:"+JSON.toJSONString(refundTicketGNCommand));
						
						RefundTicketGNDTO refundTicketGNDTO = new RefundTicketGNDTO();
						try {
							refundTicketGNDTO = gnFlightService.refundTicketGN(refundTicketGNCommand);
						} catch (GNFlightException e) {
							HgLogger.getInstance().error("gk", "【CancelGNTicketService】【execute】取消订单失败:" + HgLogger.getStackTrace(e));
							flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
							HgLogger.getInstance().info("cs", "【CancelGNTicketService】【execute】取消订单失败:"+"flightOrder:"+JSON.toJSONString(flightOrder));
							flightOrderDAO.update(flightOrder);
							passengerDAO.update(passenger);
							return  e.getCode().toString()+";"+e.getMessage();
						}
						HgLogger.getInstance().info("gk", "【CancelGNTicketService】【execute】【refundTicketGNDTO】:"+JSON.toJSONString(refundTicketGNDTO));
						flightOrder.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS);
						HgLogger.getInstance().info("cs", "【CancelGNTicketService】【execute】取消订单成功:"+"flightOrder:"+JSON.toJSONString(flightOrder));
						flightOrderDAO.update(flightOrder);
						passenger.setStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS.toString());
						passengerDAO.update(passenger);
						
						return CommonService.SUCCESS;
					}
				}
				jedis.setex("ZHIXING_"+flightOrder.getId()+"BOOK",2592000 , "canceled");
				jedis.del("ZHIXING_"+flightOrder.getId()+"BOOK");
				jedisPool.returnResource(jedis);
			}
			
		return CommonService.SUCCESS;
	}
	
}




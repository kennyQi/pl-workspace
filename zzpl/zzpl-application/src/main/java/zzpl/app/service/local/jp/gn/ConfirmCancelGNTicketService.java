package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.CancelGNTicketCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：取消订单申请
 * @类修改者：
 * @修改日期：2015年12月24日下午3:48:16
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午3:48:16
 */
@Component("ConfirmCancelGNTicketService")
public class ConfirmCancelGNTicketService implements CommonService {

	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private COSAOFDAO cosaofdao;

	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		try{
			CancelGNTicketCommand cancelGNTicketCommand = JSON.parseObject(JSON.toJSONString(command), CancelGNTicketCommand.class);
			HgLogger.getInstance().info("cs", "【CancelGNTicketService】"+"cancelGNTicketCommand:"+JSON.toJSONString(workflowInstanceID));
			PassengerQO passengerQO = new PassengerQO();
			passengerQO.setFlightOrderID(cancelGNTicketCommand.getOrderID());
			Passenger passenger = passengerDAO.queryUnique(passengerQO);
			passenger.setRefundMemo(cancelGNTicketCommand.getRefundMemo());
			passenger.setRefundType(cancelGNTicketCommand.getRefundType());
//			passenger.setStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS.toString());
			passengerDAO.update(passenger);
			FlightOrder flightOrder = flightOrderDAO.get(cancelGNTicketCommand.getOrderID());
			flightOrder.setPayStatus(FlightPayStatus.REFUND);
			flightOrder.setStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS);
			HgLogger.getInstance().info("cs", "【CancelGNTicketService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			WorkflowInstanceOrder workflowInstanceOrder = new WorkflowInstanceOrder();
			workflowInstanceOrder.setId(UUIDGenerator.getUUID());
			workflowInstanceOrder.setOrderID(cancelGNTicketCommand.getOrderID());
			workflowInstanceOrder.setOrderType(WorkflowInstanceOrder.GN_FLIGHT_ORDER);
			workflowInstanceOrder.setWorkflowInstance(workflowInstanceDAO.get(workflowInstanceID));
			workflowInstanceOrderDAO.save(workflowInstanceOrder);
			COSAOFQO cosaofqo = new COSAOFQO();
			cosaofqo.setOrderID(flightOrder.getId());
			COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
			/*
			 * 取消订单有一种情况就是 用户选择代扣然后 自动出票 差旅管理员同意出票后 机票一直没有出 然后就是等待出票 计算中心 成订单记录表中没有数据 所以在这里就不更新 结算中心 
			 * 这块 和 取消机票通知一块看
			 */
			if(cosaof!=null){
				cosaof.setOrderStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS);
				cosaofdao.update(cosaof);
			}
		}catch (Exception e){
			HgLogger.getInstance().info("cs", "【CancelGNTicketService】"+"国内航班取消失败，"+HgLogger.getStackTrace(e));
			return CommonService.FAIL;
		}
		return CommonService.SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public String execute(BaseCommand command, String workflowInstanceID) {
//		CancelGNTicketCommand cancelGNTicketCommand = JSON.parseObject(JSON.toJSONString(command), CancelGNTicketCommand.class);
//		HgLogger.getInstance().info("cs", "【CancelGNTicketService】"+"cancelGNTicketCommand:"+JSON.toJSONString(workflowInstanceID));
//		
//		WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
//		workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
//		List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
//		for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
//			FlightOrderQO flightOrderQO = new FlightOrderQO();
//			flightOrderQO.setId(workflowInstanceOrder.getOrderID());
//			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
//			PassengerQO passengerQO = new PassengerQO();
//			passengerQO.setFlightOrderID(flightOrder.getId());
//			List<Passenger> passengers = passengerDAO.queryList(passengerQO);
//			// 审批通过，提交分销订单成功，等待出票，调分销取消订单
//			if(flightOrder.getStatus()==FlightOrderStatus.APPROVAL_SUCCESS){
//				CancelTicketGNCommand cancelTicketGNCommand  = new CancelTicketGNCommand();
//				cancelTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
//				String passengerName = "";
//				int i = 1;
//				for (Passenger passenger : passengers) {
//					if(i<passengers.size()){
//						passengerName += passenger.getPassengerName() +"^";
//					}else {
//						passengerName += passenger.getPassengerName();
//					}
//					i++;
//				}
//				cancelTicketGNCommand.setPassengerName(passengerName);
//				HgLogger.getInstance().info("cs", "【CancelGNTicketService】"+"cancelTicketGNCommand:"+JSON.toJSONString(cancelTicketGNCommand));
//				try {
//					gnFlightService.cancelTicketGN(cancelTicketGNCommand);
//				} catch (GNFlightException e) {
//					HgLogger.getInstance().error("cs", "【CancelGNTicketService】"+"国内机票退票失败gnFlightException:"+JSON.toJSONString(e));
//					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
//					flightOrderDAO.update(flightOrder);
//					return e.getCode().toString()+";"+e.getMessage();
//				}
//				flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
//				flightOrderDAO.update(flightOrder);
//			}
//			/**
//			 * 订单详情页面出来后修改
//			 */
//			//出票成功 ，调分销颓废票
//			if(flightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_SUCCESS){
//				RefundTicketGNCommand refundTicketGNCommand = new RefundTicketGNCommand();
//				refundTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
//				refundTicketGNCommand.setRefundType(cancelGNTicketCommand.getRefundType());
//				refundTicketGNCommand.setRefundMemo(cancelGNTicketCommand.getRefundMemo());
//				String passengerName = "";
//				String airID = "";
//				int i = 1;
//				for (Passenger passenger : passengers) {
//					if(i<passengers.size()){
//						passengerName += passenger.getPassengerName() +"^";
//						airID +=passenger.getAirID().toString()+"^";
//					}else {
//						passengerName += passenger.getPassengerName();
//						airID +=passenger.getAirID().toString();
//					}
//					i++;
//				}
//				refundTicketGNCommand.setPassengerName(passengerName);
//				refundTicketGNCommand.setAirId(airID);
//				HgLogger.getInstance().info("cs", "【CancelGNTicketService】"+"refundTicketGNCommand:"+JSON.toJSONString(refundTicketGNCommand));
//				try {
//					gnFlightService.refundTicketGN(refundTicketGNCommand);
//				} catch (GNFlightException e) {
//					HgLogger.getInstance().error("cs", "【CancelGNTicketService】"+"国内机票退票失败gnFlightException:"+JSON.toJSONString(e));
//					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
//					flightOrderDAO.update(flightOrder);
//					return e.getCode().toString()+";"+e.getMessage();
//				}
//				flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
//				flightOrderDAO.update(flightOrder);
//				
//				
//			}
//			return CommonService.SUCCESS;
//		}
//		return CommonService.SUCCESS;
//	}
	
}
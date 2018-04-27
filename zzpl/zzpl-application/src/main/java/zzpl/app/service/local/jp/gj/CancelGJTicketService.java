package zzpl.app.service.local.jp.gj;

import hg.common.component.BaseCommand;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.GJFlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.plfx.gj.ApplyCancelNoPayOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.ApplyCancelOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.ApplyRefundTicketGJCommand;
import zzpl.pojo.command.jp.plfx.gj.ApplyRefundTicketGJCommand.RefundPassengerInfo;
import zzpl.pojo.dto.jp.plfx.gj.ApplyCancelNoPayOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.ApplyCancelOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.ApplyRefundTicketGJDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.exception.jp.GJFlightException;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

@Component("CancelGJTicketService")
public class CancelGJTicketService implements CommonService {

	@Autowired
	private GJFlightOrderDAO gjFlightOrderDAO;
	@Autowired
	private GJFlightService gjFlightService;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	
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
				GJFlightOrder gjFlightOrder = gjFlightOrderDAO.get(workflowInstanceOrder.getOrderID());
				//一、审批通过，提交分销订单成功，未支付，等待扣款，调分销取消订单
				if (gjFlightOrder.getStatus()==100) {
					//预留判断订单状态未支付
					ApplyCancelNoPayOrderGJCommand applyCancelNoPayOrderGJCommand = JSON.parseObject(JSON.toJSONString(command), ApplyCancelNoPayOrderGJCommand.class);
					applyCancelNoPayOrderGJCommand.setPlatformOrderId(gjFlightOrder.getOrderNO());
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyCancelNoPayOrderGJCommand:"+JSON.toJSONString(applyCancelNoPayOrderGJCommand));
					ApplyCancelNoPayOrderGJDTO applyCancelNoPayOrderGJDTO = new ApplyCancelNoPayOrderGJDTO();
					try {
						applyCancelNoPayOrderGJDTO = gjFlightService.applyCancelNoPayOrderGJ(applyCancelNoPayOrderGJCommand);
					} catch (GJFlightException e) {
						HgLogger.getInstance().error("gk", "【CancelGJTicketService】【execute】取消订单失败:" + HgLogger.getStackTrace(e));
						return  e.getCode().toString()+";"+e.getMessage();
					}
					
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyCancelOrderGJDTO:"+JSON.toJSONString(applyCancelNoPayOrderGJDTO));
					//根据返回结果修改订单表数据
					
				}
				
				//二、已支付但未出票订单，调用接口取消订单
				if(gjFlightOrder.getStatus()==FlightOrderStatus.APPROVAL_SUCCESS){
					ApplyCancelOrderGJCommand applyCancelOrderGJCommand = JSON.parseObject(JSON.toJSONString(command), ApplyCancelOrderGJCommand.class);
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyRefundTicketGJCommand:"+JSON.toJSONString(applyCancelOrderGJCommand));
					ApplyCancelOrderGJDTO applyCancelOrderGJDTO = new ApplyCancelOrderGJDTO();
					
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyCancelOrderGJDTO:"+JSON.toJSONString(applyCancelOrderGJDTO));
					//根据返回结果修改订单表数据
					if (applyCancelOrderGJDTO.getPlatformOrderId().equals(gjFlightOrder.getOrderNO())) {
						gjFlightOrder.setStatus(5);
					}
				}
				
				//三、出票成功 ，调分销退费票
				if(gjFlightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_SUCCESS){
					ApplyRefundTicketGJCommand applyRefundTicketGJCommand = JSON.parseObject(JSON.toJSONString(command), ApplyRefundTicketGJCommand.class);
					for (Passenger passenger : gjFlightOrder.getPassengers()) {
						RefundPassengerInfo refundPassengerInfo = new RefundPassengerInfo();
						refundPassengerInfo.setPassengerName(passenger.getPassengerName());
						refundPassengerInfo.setIdNo(passenger.getIdNO());
						refundPassengerInfo.setIdType(new Integer(passenger.getIdType()));
						applyRefundTicketGJCommand.getRefundPassengerInfos().add(refundPassengerInfo);
					}
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyRefundTicketGJCommand:"+JSON.toJSONString(applyRefundTicketGJCommand));
					ApplyRefundTicketGJDTO applyRefundTicketGJDTO = new ApplyRefundTicketGJDTO();
					try {
						applyRefundTicketGJDTO = gjFlightService.applyRefundTicketGJ(applyRefundTicketGJCommand);
					} catch (GJFlightException e) {
						HgLogger.getInstance().error("gk", "【CancelGJTicketService】【execute】取消订单失败:" + HgLogger.getStackTrace(e));
						return  e.getCode().toString()+";"+e.getMessage();
					}
					HgLogger.getInstance().info("gk", "【CancelGJTicketService】"+"applyRefundTicketGJDTO:"+JSON.toJSONString(applyRefundTicketGJDTO));
					//根据返回结果修改订单表数据
					if (applyRefundTicketGJDTO.getPlatformOrderId().equals(gjFlightOrder.getOrderNO())) {
						gjFlightOrder.setStatus(5);
					}
					
					
					return CommonService.SUCCESS;
				}
			}
			
			
		
		return CommonService.SUCCESS;
	}
	
}

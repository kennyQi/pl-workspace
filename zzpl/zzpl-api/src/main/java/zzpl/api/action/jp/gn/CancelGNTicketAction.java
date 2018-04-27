package zzpl.api.action.jp.gn;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.jp.GNCancelTicketCommand;
import zzpl.api.client.response.jp.GNCancelTicketResponse;
import zzpl.app.service.local.workflow.TasklistWaitService;
import zzpl.app.service.local.workflow.WorkflowInstanceOrderService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.pojo.command.jp.CancelGNTicketCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.exception.workflow.WorkflowException;

import com.alibaba.fastjson.JSON;

@Component("CancelGNTicketAction")
public class CancelGNTicketAction implements CommonAction{

	@Autowired
	private WorkflowInstanceOrderService workflowInstanceOrderService;
	@Autowired
	private TasklistWaitService tasklistWaitService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private WorkflowStepService workflowStepService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GNCancelTicketResponse gnCancelTicketResponse = new GNCancelTicketResponse();
		try {
			GNCancelTicketCommand gnCancelTicketCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GNCancelTicketCommand.class);
			HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"gnCancelTicketCommand:"+JSON.toJSONString(gnCancelTicketCommand));
			//1：把流程的command转出来
			SendCommand sendCommand = new SendCommand();
			sendCommand.setWorkflowID(gnCancelTicketCommand.getWorkflowID());
			sendCommand.setCurrentUserID(gnCancelTicketCommand.getUserID());
			sendCommand.setCurrentStepNO(gnCancelTicketCommand.getCurrentStepNO());
			sendCommand.setNextUserIDs(gnCancelTicketCommand.getNextUserIDs());
			sendCommand.setWorkflowID(gnCancelTicketCommand.getWorkflowID());
			sendCommand.setNextStepNO(gnCancelTicketCommand.getNextStepNO());
			//取消service
			String[] services = {"ConfirmCancelGNTicketService"};
			//取消订单command
			CancelGNTicketCommand cancelGNTicketCommand = new CancelGNTicketCommand();
			cancelGNTicketCommand.setOrderID(gnCancelTicketCommand.getOrderID());
			cancelGNTicketCommand.setOrderNO(gnCancelTicketCommand.getOrderNO());
			cancelGNTicketCommand.setRefundType(gnCancelTicketCommand.getRefundType());
			cancelGNTicketCommand.setRefundMemo(gnCancelTicketCommand.getRefundMemo());
			HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"sendCommand:"+JSON.toJSONString(sendCommand));
			HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"services:"+JSON.toJSONString(services));
			HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"cancelGNTicketCommand:"+JSON.toJSONString(cancelGNTicketCommand));
			workflowStepService.send(sendCommand, services, cancelGNTicketCommand);
			gnCancelTicketResponse.setResult(ApiResponse.RESULT_CODE_OK);
			gnCancelTicketResponse.setMessage("提交成功");
		} catch (WorkflowException workflowException) {
			HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"国内航班取消失败，"+HgLogger.getStackTrace(workflowException));
			gnCancelTicketResponse.setResult(workflowException.getCode());
			gnCancelTicketResponse.setMessage(workflowException.getMessage());
		}
		HgLogger.getInstance().info("cs", "【CancelGNTicketAction】"+"gnCancelTicketResponse:"+JSON.toJSONString(gnCancelTicketResponse));
		return JSON.toJSONString(gnCancelTicketResponse);
	}
	
}

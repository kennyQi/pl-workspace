package zzpl.api.action.jp.gj;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.response.jp.GJCancelTicketResponse;
import zzpl.app.service.local.workflow.TasklistWaitService;
import zzpl.app.service.local.workflow.WorkflowInstanceOrderService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.CancelGJTicketCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.workflow.TasklistWaitQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;
import zzpl.pojo.util.UserSecurity;

import com.alibaba.fastjson.JSON;

@Component("CancelGJTicketAction")
public class CancelGJTicketAction implements CommonAction{

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
		GJCancelTicketResponse gjCancelTicketResponse = new GJCancelTicketResponse();
		try {
			CancelGJTicketCommand cancelGJTicketCommand = JSON.parseObject(apiRequest.getBody().getPayload(),CancelGJTicketCommand.class);
			HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"cancelGJTicketCommand:"+JSON.toJSONString(cancelGJTicketCommand));
			//办结
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setOrderID(cancelGJTicketCommand.getOrderID());
			WorkflowInstanceOrder workflowInstanceOrder = workflowInstanceOrderService.queryUnique(workflowInstanceOrderQO);
			WorkflowInstance workflowInstance = workflowInstanceOrder.getWorkflowInstance();
			TasklistWaitQO tasklistWaitQO = new TasklistWaitQO();
			tasklistWaitQO.setWorkflowInstanceID(workflowInstance.getId());
			Jedis jedis = null;
			jedis = jedisPool.getResource();
			UserSecurity userSecurity = JSON.parseObject(jedis.get("ZZPL_"+apiRequest.getHead().getSessionID()),UserSecurity.class);
			jedisPool.returnResource(jedis);
			tasklistWaitQO.setCurrentUserID(userSecurity.getUserID());
			TasklistWait tasklistWait = tasklistWaitService.queryUnique(tasklistWaitQO);
			SendCommand sendCommand = new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());
			String[] services = {"CancelGJTicketService"};
			HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"sendCommand:"+JSON.toJSONString(sendCommand));
			HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"services:"+JSON.toJSONString(services));
			HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"cancelGJTicketCommand:"+JSON.toJSONString(cancelGJTicketCommand));
			workflowStepService.send(sendCommand, services, cancelGJTicketCommand);
		} catch (WorkflowException workflowException) {
			HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"国际航班取消失败，"+HgLogger.getStackTrace(workflowException));
			gjCancelTicketResponse.setResult(workflowException.getCode());
			gjCancelTicketResponse.setMessage(workflowException.getMessage());
		}
		HgLogger.getInstance().info("cs", "【CancelGJTicketAction】"+"gjCancelTicketResponse:"+JSON.toJSONString(gjCancelTicketResponse));
		return JSON.toJSONString(gjCancelTicketResponse);
	}

}

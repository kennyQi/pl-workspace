package service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.pojo.command.workflow.SendbackCommand;
import zzpl.pojo.exception.workflow.WorkflowException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestbackSendService {

	@Resource
	private WorkflowStepService workflowService;
	@Test
	public void send() {
		SendbackCommand sendbackCommand = new SendbackCommand();
		sendbackCommand.setWorkflowID("33333333333");
		sendbackCommand.setWorkflowInstanceID("ae90e6625260482b907f18d2d38f9d93");
		sendbackCommand.setCurrentUserID("fff5f8919090497489cd9cd212fdd1ae");
		sendbackCommand.setCurrentStepNO(2);
		try {
			workflowService.sendback(sendbackCommand,sendbackCommand);
		} catch (WorkflowException e) {
		System.out.println(e.getMessage());
		}
	}
	
	public void testSendBack() {
		SendbackCommand sendbackCommand = new SendbackCommand();
		sendbackCommand.setWorkflowID("");//流程ID
		sendbackCommand.setWorkflowInstanceID("");//流程实例ID
		sendbackCommand.setCurrentUserID("");//当前环节处理人
		sendbackCommand.setCurrentStepNO(2);//当前环节
		
		try {
			workflowService.sendback(sendbackCommand, sendbackCommand);
		} catch (WorkflowException e) {
			System.out.println("TestbackSendService -> testSendBack : "+e.getMessage());
		}
		
	}

}

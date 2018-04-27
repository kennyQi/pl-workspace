package service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.exception.workflow.WorkflowException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSendService {

	@Resource
	private WorkflowStepService workflowService;
	@Test
	public void send() {
		SendCommand sendCommand = new SendCommand();
		sendCommand.setWorkflowID("11111111111");
		sendCommand.setCurrentStepNO(1);
		sendCommand.setCurrentUserID("cac65d900f094adab96f153f50cd240a");
		sendCommand.setNextStepNO(2);
//		sendCommand.setWorkflowInstanceID("ae90e6625260482b907f18d2d38f9d93");
		List<String> strings = new ArrayList<String>();
		strings.add("cac65d900f094adab96f153f50cd240a");
		strings.add("fff5f8919090497489cd9cd212fdd1ae");
		sendCommand.setNextUserIDs(strings);
		try {
			String[] strings2 = {"BookGNFlightService"};
			workflowService.send(sendCommand, strings2,sendCommand);
		} catch (WorkflowException e) {
		System.out.println(e.getMessage());
		}
	}
	
	/*public void testSend() {
		SendCommand sendCommand=new SendCommand();
		sendCommand.setWorkflowID("");//流程ID
		sendCommand.setCurrentStepNO(1);//当前环节
		sendCommand.setCurrentUserID("");//当前环节处理人
		sendCommand.setNextStepNO(2);//下一环节
		List<String> nextUserIDs=new ArrayList<String>();//下一环节处理人，如果是会签则为多人
		nextUserIDs.add("");
		//nextUserIDs.add("");
		sendCommand.setNextUserIDs(nextUserIDs);
		try {
			workflowService.send(sendCommand, sendCommand);
		} catch (WorkflowException e) {
			System.out.println("TestSendService -> testSend -> WorkflowException : "+e.getMessage());
		}
	}
*/
}

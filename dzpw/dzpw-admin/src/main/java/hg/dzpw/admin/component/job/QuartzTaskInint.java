package hg.dzpw.admin.component.job;

import hg.system.common.quartz.QuartzTaskManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzTaskInint implements InitializingBean {

	@Autowired
	private CloseTimeoutTicketOrderTask closeTimeoutTicketOrderTask;
	@Autowired
	private GroupTicketRefundTask groupTicketRefundTask;
	@Autowired
	private UpdateTicketExpiryDateTask updateTicketExpiryDateTask;
	@Autowired
	private PushOrderNoTask pushOrderNoTask;
//	退款成功后通知经销商
	@Autowired
	private DealerRefundSuccessPublicTask dealerRefundSuccessPublicTask;
//	执行经销商的退款申请
	@Autowired
	private DealerApplyRefundTask dealerApplyRefundTask;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		QuartzTaskManager manager = QuartzTaskManager.getInstance();
		/** DZPW不自动退款 
		manager.addTask(groupTicketRefundTask);
		*/
		manager.addTask(closeTimeoutTicketOrderTask);
		manager.addTask(updateTicketExpiryDateTask);
		manager.addTask(pushOrderNoTask);
		manager.addTask(dealerRefundSuccessPublicTask);
		manager.addTask(dealerApplyRefundTask);
		
	}

}

//package hg.dzpw.app.component.task;
//
//import hg.system.common.quartz.QuartzTaskManager;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @类功能说明：定时任务初始化
// * @类修改者：
// * @修改日期：2015-4-1下午4:43:20
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：zhurz
// * @创建时间：2015-4-1下午4:43:20
// */
//@Component
//public class QuartzTaskInit implements InitializingBean {
//
//	@Autowired
//	private CheckTicketPolicyTask checkTicketPolicyTask;
//	@Autowired
//	private CloseTimeoutTicketOrderTask closeTimeoutTicketOrderTask;
//	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		QuartzTaskManager manager = QuartzTaskManager.getInstance();
//		manager.addTask(checkTicketPolicyTask);
//		manager.addTask(closeTimeoutTicketOrderTask);
//	}
//
//}

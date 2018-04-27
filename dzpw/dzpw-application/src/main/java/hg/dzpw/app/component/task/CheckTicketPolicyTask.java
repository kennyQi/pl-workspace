//package hg.dzpw.app.component.task;
//
//import hg.common.component.RedisLock;
//import hg.dzpw.app.service.local.TicketPolicyLocalService;
//import hg.log.util.HgLogger;
//import hg.system.common.quartz.QuartzTask;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @类功能说明：更新联票下架状态工作 
// * @类修改者：
// * @修改日期：2015-4-1下午4:02:03
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：zhurz
// * @创建时间：2015-4-1下午4:02:03
// */
//@Component
//public class CheckTicketPolicyTask extends QuartzTask {
//
//	@Autowired
//	private TicketPolicyLocalService ticketPolicyService;
//	
//	private RedisLock lock = new RedisLock("dzpwCheckTicketPolicyTask");
//
//	public CheckTicketPolicyTask() {
//		setGroupName("电子票务");
//		setTaskName("更新联票下架状态");
//		// 每天0点5分执行
//		setCronExpression("0 5 0 * * ?");
//	}
//
//	@Override
//	public void execute() {
//		if (lock.tryLock()) {
//			try {
//				//根据销售日期更新门票状态
//				HgLogger.getInstance().info("zhurz", "更新联票下架状态开始");
//				ticketPolicyService.updateTicketPolicyStatus();
//				HgLogger.getInstance().info("zhurz", "更新联票下架状态结束");
//			} finally {
//				lock.unlock();
//			}
//		} else {
//			HgLogger.getInstance().info("zhurz", "更新联票下架状态任务已有其他实例在执行");
//		}
//	}
//}

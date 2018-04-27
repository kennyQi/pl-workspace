//package hg.dzpw.app.component.task;
//
//import hg.common.component.RedisLock;
//import hg.dzpw.app.service.local.TicketOrderLocalService;
//import hg.log.util.HgLogger;
//import hg.system.common.quartz.QuartzTask;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @类功能说明：关闭3小时内未付款的订单
// * @类修改者：
// * @修改日期：2015-4-29下午4:37:35
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：zhurz
// * @创建时间：2015-4-29下午4:37:35
// */
//@Component
//public class CloseTimeoutTicketOrderTask extends QuartzTask {
//
//	@Autowired
//	private TicketOrderLocalService ticketOrderService;
//	
//	private RedisLock lock = new RedisLock("dzpwCloseTimeoutTicketOrderTask");
//
//	public CloseTimeoutTicketOrderTask() {
//		setGroupName("电子票务");
//		setTaskName("关闭3小时内未付款的订单");
//		// 每10分钟执行一次
//		setCronExpression("0 0/10 * * * ?");
//	}
//
//	@Override
//	public void execute() {
//		if (lock.tryLock()) {
//			try {
//				HgLogger.getInstance().info("zhurz", "关闭3小时内未付款的订单开始");
//				ticketOrderService.closeTimeoutTicketOrder();
//				HgLogger.getInstance().info("zhurz", "关闭3小时内未付款的订单结束");
//			} finally {
//				lock.unlock();
//			}
//		} else {
//			HgLogger.getInstance().info("zhurz", "关闭3小时内未付款的订单任务已有其他实例在执行");
//		}
//	}
//}

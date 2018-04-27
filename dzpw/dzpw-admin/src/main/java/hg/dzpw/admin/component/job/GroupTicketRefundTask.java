package hg.dzpw.admin.component.job;

import hg.common.component.RedisLock;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.log.util.HgLogger;
import hg.system.common.quartz.QuartzTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @类功能说明：定时退款
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-12-29下午5:38:25
 * @版本：V1.0
 *
 */
@Component
public class GroupTicketRefundTask extends QuartzTask {

	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	private RedisLock lock = new RedisLock("dzpwGroupTicketRefundTask");
	
	
	public GroupTicketRefundTask(){
		setGroupName("电子票务");
		setTaskName("定时退款");
		// 每天5点启动
//		setCronExpression("0 0 5 * * ?");
		setCronExpression("0 0/17 * * * ?");
	}
	
	@Override
	public void execute() {

		if (lock.tryLock()) {
			try {
				HgLogger.getInstance().info("yangk", "定时退款开始");
//				groupTicketLocalService.groupTicketRefund();
				HgLogger.getInstance().info("yangk", "定时退款结束");
			} finally {
				lock.unlock();
			}
		}else {
			HgLogger.getInstance().info("yangk", "定时退款已有其他实例在执行");
		}
		
	}

}

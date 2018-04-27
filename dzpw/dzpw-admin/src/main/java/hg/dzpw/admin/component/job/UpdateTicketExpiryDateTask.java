package hg.dzpw.admin.component.job;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.common.component.RedisLock;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.log.util.HgLogger;
import hg.system.common.quartz.QuartzTask;

/**
 * @类功能说明：更新门票状态
 * @公司名称：浙江汇购科技有限公司
 * @作者：yangkang
 * @创建时间：2015-12-28上午10:56:00
 */
@Component
public class UpdateTicketExpiryDateTask extends QuartzTask {
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;

	private RedisLock lock = new RedisLock("dzpwUpdateTicketExpiryDateTask");
	
	
	public UpdateTicketExpiryDateTask(){
		setGroupName("电子票务");
		setTaskName("更新过期门票状态");
		// 每天0点零一分启动
//		setCronExpression("0 1 0 * * ?");
		setCronExpression("0 0/7 * * * ?");
	}
	
	
	@Override
	public void execute() {
		
		if (lock.tryLock()) {
			
			try {
				HgLogger.getInstance().info("yangk", "更新过期门票状态开始");
				groupTicketLocalService.ticketExpiryDateTimeOut();
				HgLogger.getInstance().info("yangk", "更新过期门票状态结束");
			} finally {
				lock.unlock();
			}
		}else {
			HgLogger.getInstance().info("yangk", "更新过期门票状态已有其他实例在执行");
		}
	}
	
}

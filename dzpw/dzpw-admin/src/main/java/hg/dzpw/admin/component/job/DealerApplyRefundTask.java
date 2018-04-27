package hg.dzpw.admin.component.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.common.component.RedisLock;
import hg.dzpw.app.service.local.ApplyRefundRecordLocalService;
import hg.log.util.HgLogger;
import hg.system.common.quartz.QuartzTask;
/**
 * 
 * @类功能说明：处理经销商申请退款任务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-22下午2:07:47
 * @版本：
 */
@Component
public class DealerApplyRefundTask extends QuartzTask{
	@Autowired
	private ApplyRefundRecordLocalService applyRefundRecordLocalService;
	
	private RedisLock lock = new RedisLock("dealerApplyRefundTask");
	
	public DealerApplyRefundTask(){
		setGroupName("电子票务");
		setCronExpression("0 0/30 * * * ?");
		setTaskName("处理经销商申请退款任务");
	}
	@Override
	public void execute() {
		if (lock.tryLock()) {
			try {
				HgLogger.getInstance().info("guotx", "批量处理经销商退款申请开始");
				applyRefundRecordLocalService.dealRefund();
				HgLogger.getInstance().info("guotx", "批量处理经销商退款申请结束");
			} finally {
				lock.unlock();
			}
		}else {
			HgLogger.getInstance().info("guotx", "【批量处理经销商退款】任务已有其他实例在执行");
		}
		
	}


}

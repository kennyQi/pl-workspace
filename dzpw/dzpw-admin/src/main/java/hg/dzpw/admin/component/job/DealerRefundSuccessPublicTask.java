package hg.dzpw.admin.component.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.common.component.RedisLock;
import hg.dzpw.app.service.local.ApplyRefundRecordLocalService;
import hg.log.util.HgLogger;
import hg.system.common.quartz.QuartzTask;
/**
 * 
 * @类功能说明：经销商退款成功消息推送定时任务，从redis中查等待推送记录进行处理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-24上午11:32:43
 * @版本：
 */
@Component
public class DealerRefundSuccessPublicTask extends QuartzTask{

	@Autowired
	private ApplyRefundRecordLocalService refundRecordLocalService;
	private RedisLock lock = new RedisLock("DealerRefundSuccessPublicTask");
	public DealerRefundSuccessPublicTask(){
		setGroupName("电子票务");
		setTaskName("经销商退款成功消息推送");
		// 每10分启动
		setCronExpression("0 0/10 * * * ?");
	}
	@Override
	public void execute() {
		if (lock.tryLock()) {
			HgLogger.getInstance().info("guotx", "退款成功消息推送经销商开始");
			try {
				refundRecordLocalService.publishRefundEvent();
			} finally {
				lock.unlock();
			}
			HgLogger.getInstance().info("guotx", "退款成功消息推送经销商结束");
		}else {
			HgLogger.getInstance().info("guotx", "退款成功消息推送经销商任务已经在运行");
		}
		
	}

}

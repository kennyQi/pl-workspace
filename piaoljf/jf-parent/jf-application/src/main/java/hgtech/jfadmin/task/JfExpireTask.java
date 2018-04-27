/**
 * 
 */
package hgtech.jfadmin.task;

import hgtech.jfadmin.service.JFTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component("JfExpireTask")
public class JfExpireTask {
	@Autowired
	JFTaskService jfExpireService; 
	
	/**
	 * 每天零时  5秒  跑积分过期处理 秒  分 事 日 月 周 年
	 */
//	@Scheduled(cron = "5 0 0 * * ?")
	//@Scheduled(cron = "* * * * * ?") //每秒跑
	public void taskJob()
	{
	 	jfExpireService.updateJf();
		
	}

}

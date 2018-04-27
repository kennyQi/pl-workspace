/**
 * 
 */
package hgtech.jfadmin.task;

import javax.swing.JOptionPane;

import hg.hjf.app.service.grade.GradeService;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfadmin.service.JFTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 积分系统后台任务
 */
@Component("jfTask")
public class JfTask {
	@Autowired
	JFTaskService jfTaskService; 
	
	@Autowired
	GradeService gradeService;
	
	/**
	 * 每天零时  5秒  跑 
	 */
	//@Scheduled(cron = "5 0 18 * * ?")
	//@Scheduled(cron = "* * * * * ?")
	public void taskJob()
	{
	    System.out.println("开始批任务. ");
	    SetupAccountContext.acctTypeDao.refresh();
	   for(JfAccountType accountType: SetupAccountContext.acctTypeDao.getEntityList()){
	       	System.out.println("开始积分类型 %s 的 状态检查"+accountType.getCode());
		jfTaskService.changeArrving2Nor(accountType);
	   }
	   jfTaskService.changeArrving2Nor4Other();
	   
//	   //会员等级变更
	   gradeService.handleUserGrade();
	}

	 //启动一个线程，执行批任务
	public void startit() {
	   new Thread(){
	       public void run() {
		   while(true){
        		try {
        		    Thread.sleep(10000);
        		    taskJob();
        		    System.out.println("状态检查休息 1小时...");
        		    Thread.sleep(3600000);
        		} catch (InterruptedException e) {
        		    e.printStackTrace();
        		}
		   }
	       };
	   }.start();
	}

}

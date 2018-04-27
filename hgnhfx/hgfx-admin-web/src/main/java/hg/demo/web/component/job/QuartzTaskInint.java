package hg.demo.web.component.job;


import hg.framework.common.base.quartz.QuartzTaskManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzTaskInint implements InitializingBean{

	@Autowired
	private SyncNewFixedPriceSetJob syncNewFixedPriceSetJob;
	@Autowired
	private RebateReportJob rebateReportJob;
	@Autowired
	private FixedPriceReportJob fixedPriceReportJob;

	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		QuartzTaskManager manager = QuartzTaskManager.getInstance();
		
		manager.addTask(syncNewFixedPriceSetJob);
		manager.addTask(rebateReportJob);
		manager.addTask(fixedPriceReportJob);
	}

}

package hg.demo.web.component.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.framework.common.base.quartz.QuartzTask;
import hg.fx.domain.Distributor;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.RebateReportSPI;
import hg.fx.spi.qo.DistributorSQO;

/**
 * 返利月报任务
 * */
@Component
public class RebateReportJob extends QuartzTask{
	
	@Autowired
	private DistributorSPI distributorSPIServ;
	
	
	@Autowired
	private RebateReportSPI rebateReportSPIServ;
	
	
	public RebateReportJob(){
		setGroupName("里程分销");
		setTaskName("里程分销--每月10号生成返利商户月报");
		// 每月1号凌时
		setCronExpression("0 0 0 10 * ?");
//		setCronExpression("0 0/1 * * * ?");
	}
	
	
	@Override
	public void execute() {
		System.out.println("进入--里程分销--每月10号生成返利商户月报");
		
		DistributorSQO sqo = new DistributorSQO();
		sqo.setDiscountType(Distributor.DISCOUNT_TYPE_REBATE);
		// 查询返利的商户
		List<Distributor> distributors = distributorSPIServ.queryList(sqo);
		
		// 查询商户使用关联的商品
		for (Distributor d : distributors){
			
			rebateReportSPIServ.queryRebateReportData(d);
			
		}
		System.out.println("结束--里程分销--每月10号生成返利商户月报");
	}
	
}

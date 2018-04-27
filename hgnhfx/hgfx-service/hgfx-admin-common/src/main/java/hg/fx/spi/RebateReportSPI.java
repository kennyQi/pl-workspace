package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.report.CreateRebateReportCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.rebate.RebateReport;
import hg.fx.spi.qo.RebateReportSQO;

import java.util.HashMap;
import java.util.List;
/**
 * 返利月报SPI
 * */
public interface RebateReportSPI extends BaseServiceProviderInterface{
	
	/**
	 * 返利月报查询订单信息
	 * */
	public List<MileOrder> queryOrderForRebateReport(HashMap<String, Object> map);
	
	/**
	 * 创建返利月报
	 */
	public void createRebateReport(CreateRebateReportCommand command);
	
	public void queryRebateReportData(Distributor d);
	
	Pagination<RebateReport> queryPagination(RebateReportSQO sqo);
}

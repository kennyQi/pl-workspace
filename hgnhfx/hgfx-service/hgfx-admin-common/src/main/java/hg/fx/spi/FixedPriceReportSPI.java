package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.report.CreateFixedPriceReportCommand;
import hg.fx.domain.fixedprice.FixedPriceReport;
import hg.fx.spi.qo.FixedPriceReportSQO;

/**
 * 定价月报SPI
 * */
public interface FixedPriceReportSPI extends BaseServiceProviderInterface{

	Pagination<FixedPriceReport> queryPagination(FixedPriceReportSQO sqo);
	
	public FixedPriceReport createFixedPriceReport(CreateFixedPriceReportCommand cmd);
}

package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.operationLog.CreateOperationLogCommand;
import hg.fx.domain.OperationLog;
import hg.fx.spi.qo.OperationLogSQO;

/**
 * 运营端操作日志SPI
* @author yangkang
 * @date 2016-06-01
 * */
public interface OperationLogSPI extends BaseServiceProviderInterface{
	
	
	/**
	 * 创建操作日志
	 * */
	public OperationLog createOperationLog(CreateOperationLogCommand command);
	
	/**
	 * 分页条件查询
	 * */
	Pagination<OperationLog> queryPagination(OperationLogSQO sqo);
	
}

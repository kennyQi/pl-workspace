package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.arrearsRecord.AuditArrearsRecordCommand;
import hg.fx.command.arrearsRecord.CreateArrearsRecordCommand;
import hg.fx.domain.ArrearsRecord;
import hg.fx.spi.qo.ArrearsRecordSQO;

import java.util.List;

/**
 * @author cangs
 */
public interface ArrearsRecordSPI extends BaseServiceProviderInterface{
	
	/**
	 * 新增可欠费里程变更记录
	 */
	ArrearsRecord create(CreateArrearsRecordCommand command);
	
	/**
	 * 分页查询可欠费里程变更记录
	 */
	Pagination<ArrearsRecord> queryArrearsRecordPagination(ArrearsRecordSQO sqo);
	
	/**
	 * 列表查询可欠费里程变更记录
	 */
	List<ArrearsRecord> queryArrearsRecordList(ArrearsRecordSQO sqo);
	
	/**
	 * 查询可欠费里程变更记录
	 */
	ArrearsRecord queryArrearsRecordByID(ArrearsRecordSQO sqo);
	
	/**
	 * 可欠费里程变更记录审核
	 */
	ArrearsRecord audit(AuditArrearsRecordCommand command);
}

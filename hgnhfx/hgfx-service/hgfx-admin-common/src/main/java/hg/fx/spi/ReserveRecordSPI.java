package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.ReserveRecord;
import hg.fx.spi.qo.ReserveRecordSQO;

import java.util.List;

/**
 * @author cangs
 */
public interface ReserveRecordSPI extends BaseServiceProviderInterface{

	/**
	 * 新增里程余额变更记录
	 */
	ReserveRecord create(CreateReserveRecordCommand command);
	
	/**
	 * 分页查询里程余额变更记录
	 */
	Pagination<ReserveRecord> queryReserveRecordPagination(ReserveRecordSQO sqo);
	
	/**
	 * 列表查询里程余额变更记录
	 */
	List<ReserveRecord> queryReserveRecordList(ReserveRecordSQO sqo);
	
	/**
	 * 查询里程余额变更记录
	 */
	ReserveRecord queryReserveRecordByID(ReserveRecordSQO sqo);
	
	/**
	 * 里程余额变更记录审核
	 */
	ReserveRecord audit(AuditReserveRecordCommand command);
}

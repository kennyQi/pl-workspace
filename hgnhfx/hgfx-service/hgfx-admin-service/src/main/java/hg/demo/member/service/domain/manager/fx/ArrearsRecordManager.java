package hg.demo.member.service.domain.manager.fx;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.arrearsRecord.AuditArrearsRecordCommand;
import hg.fx.command.arrearsRecord.CreateArrearsRecordCommand;
import hg.fx.domain.ArrearsRecord;
import hg.fx.domain.Distributor;

import java.util.Date;

/**
 * @author cangs
 */
public class ArrearsRecordManager extends BaseDomainManager<ArrearsRecord>{

	public ArrearsRecordManager(ArrearsRecord entity) {
		super(entity);
	}

	/**
	 * 新增可欠费里程变更记录
	 */
	public ArrearsRecordManager create(CreateArrearsRecordCommand command,Distributor distributor,AuthUser authUser){
		entity.setId(UUIDGenerator.getUUID());
		entity.setPreArrears(command.getPreArrears());
		entity.setApplyArrears(command.getApplyArrears());
		entity.setReason(command.getReason());
		entity.setApplyDate(new Date());
		entity.setCheckStatus(ArrearsRecord.CHECK_STATUS_WAITTING);
		entity.setDistributor(distributor);
		entity.setOperator(authUser);
		return this;
	}
	
	/**
	 * 可欠费里程变更记录审核
	 */
	public ArrearsRecordManager audit(AuditArrearsRecordCommand command){
		if(entity.getCheckStatus().equals(ArrearsRecord.CHECK_STATUS_WAITTING)){
			entity.setCheckStatus(command.getCheckStatus());
		}
		return this;
	}
}

package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.fx.command.operationLog.CreateOperationLogCommand;
import hg.fx.domain.OperationLog;

public class OperationLogManager extends BaseDomainManager<OperationLog> {

	public OperationLogManager(OperationLog entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}
	
	public OperationLogManager create(CreateOperationLogCommand command){
		entity.setContent(command.getContent());
		entity.setCreateDate(command.getCreateDate());
		entity.setId(command.getId());
		entity.setType(command.getType());
		entity.setOperator(command.getAuthUser());
		return this;
	}
	

}

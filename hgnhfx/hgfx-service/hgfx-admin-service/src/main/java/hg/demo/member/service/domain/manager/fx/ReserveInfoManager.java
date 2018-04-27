package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.reserveInfo.CreateReserveInfoCommand;
import hg.fx.domain.ReserveInfo;

public class ReserveInfoManager extends BaseDomainManager<ReserveInfo>{

	public ReserveInfoManager(ReserveInfo entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public ReserveInfoManager create(CreateReserveInfoCommand command)
	{
		entity.setAmount(command.getAmount());
		entity.setFreezeBalance(command.getFreezeBalance());
		entity.setId(UUIDGenerator.getUUID());
		entity.setUsableBalance(command.getUsableBalance());
		entity.setArrearsAmount(command.getArrearsAmount());
		entity.setWarnValue(command.getWarnValue());
		return this;
	}
	
	public ReserveInfoManager modifyWarnValue(Integer warnValue)
	{
		entity.setWarnValue(warnValue);
		return this;
	}
	
	public ReserveInfoManager modifyArrearsAmount(Integer value)
	{
		entity.setArrearsAmount(value);
		return this;
	}
}

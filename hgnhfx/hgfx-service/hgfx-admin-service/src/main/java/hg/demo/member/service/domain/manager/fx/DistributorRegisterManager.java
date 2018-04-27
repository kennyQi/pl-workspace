package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.DistributorRegister.AduitDistributorRegisterCommand;
import hg.fx.command.DistributorRegister.CreateDistributorRegisterCommand;
import hg.fx.domain.DistributorRegister;

import java.util.Date;

public class DistributorRegisterManager extends BaseDomainManager<DistributorRegister>{

	public DistributorRegisterManager(DistributorRegister entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}
	
	
	public DistributorRegisterManager aduit(AduitDistributorRegisterCommand command)
	{
		if(command.getIsPass()!=null){
			if(command.getIsPass().booleanValue())
				entity.setStatus(DistributorRegister.DISTRIBUTOR_REGISTER_CHECK_SUCC);
			else
				entity.setStatus(DistributorRegister.DISTRIBUTOR_REGISTER_CHECK_FAIL);
		}
		return this;
	}

	public DistributorRegisterManager create(CreateDistributorRegisterCommand command) {
		
		entity.setCreateDate(new Date());
		entity.setId(UUIDGenerator.getUUID());
		entity.setLoginName(command.getLoginName());
		entity.setPhone(command.getPhone());
		entity.setWebSite(command.getWebSite());
		entity.setStatus(DistributorRegister.DISTRIBUTOR_REGISTER_UNCHECK);
		entity.setLinkMan(command.getLinkMan());
		entity.setPasswd(command.getPasswd());
		entity.setCompanyName(command.getCompanyName());
		return this;
	}

}

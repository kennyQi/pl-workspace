package hg.demo.member.service.domain.manager.fx;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;

/**
 * 
 * @author Caihuan
 * @date   2016年6月1日
 */
public class DistributorUserManager extends BaseDomainManager<DistributorUser> {

	public DistributorUserManager(DistributorUser entity) {
		super(entity);
	}

	public DistributorUserManager createDistributorUser(CreateDistributorUserCommand command,Distributor distributor)
	{
		entity.setId(UUIDGenerator.getUUID());
		entity.setLoginName(command.getAccount());
		entity.setName(command.getName());
		entity.setCreateDate(new Date());
		entity.setPasswd(command.getPassword());
		entity.setStatus(command.getType());
		entity.setDistributor(distributor);
		return this;
		
	}
	
	
	
	public DistributorUserManager modifyDistributorUser(ModifyDistributorUserCommand command)
	{
		if(StringUtils.isNotBlank(command.getPassword()))
		entity.setPasswd(command.getPassword());
		return this;
	}
	
	public DistributorUserManager delete()
	{
		entity.setRemoved(true);
		return this;
	}
}

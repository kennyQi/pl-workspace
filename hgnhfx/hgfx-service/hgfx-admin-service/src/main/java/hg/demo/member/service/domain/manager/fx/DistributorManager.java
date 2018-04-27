package hg.demo.member.service.domain.manager.fx;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveInfo;

public class DistributorManager extends BaseDomainManager<Distributor> {

	public DistributorManager(Distributor entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public DistributorManager modify(ModifyDistributorCommand command)
	{
		if(StringUtils.isNotBlank(command.getPhone()))
		entity.setPhone(command.getPhone());
		if(command.getStatus()!=null)
		entity.setStatus(command.getStatus());
		if(command.getCheckStatus()!=null)
		entity.setCheckStatus(command.getCheckStatus());
		if(command.getProdNum()!=null)
		entity.setProdNum(command.getProdNum());
		return this;
	}

	public DistributorManager create(CreateDistributorCommand command,ReserveInfo reserveInfo) {
		
		entity.setCreateDate(new Date());
		entity.setId(UUIDGenerator.getUUID());
		entity.setName(command.getCompanyName());
		entity.setPhone(command.getPhone());
		entity.setWebSite(command.getWebSite());
		entity.setCheckStatus(command.getCheckStatus());
		entity.setStatus(command.getStatus());
		entity.setFirstLetter(command.getFirstLetter());
		entity.setLinkMan(command.getLinkMan());
		entity.setSignKey(command.getSignKey());
		entity.setProdNum(0);//使用商品数初始为0
		entity.setCode(command.getCode());
		entity.setReserveInfo(reserveInfo);
		entity.setDiscountType(command.getDiscountType());
		return this;
	}
}

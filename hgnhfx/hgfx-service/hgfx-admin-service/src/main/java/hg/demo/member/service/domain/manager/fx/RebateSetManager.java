package hg.demo.member.service.domain.manager.fx;

import java.util.Date;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.rebate.AduitRebateSetCommand;
import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.command.rebate.ModifyRebateSetCommand;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.util.DateUtil;

public class RebateSetManager extends BaseDomainManager<RebateSet>{

	public RebateSetManager(RebateSet entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}
	
	public RebateSetManager createDefault(CreateRebateSetCommand cmd){	
		entity.setId(UUIDGenerator.getUUID());
		entity.setProduct(cmd.getProduct());
		entity.setProductName(cmd.getProductName());
		entity.setDistributor(cmd.getDistributor());
		entity.setDistributorName(cmd.getDistributorName());
		entity.setLoginName(cmd.getLoginName());
		entity.setApplyDate(cmd.getApplyDate());
		entity.setCheckStatus(cmd.getCheckStatus());
		entity.setIntervalStr(cmd.getIntervalStr());
		entity.setIsCheck(false);
		//创建商户默认规则
		entity.setImplementDate(DateUtil.parseDateTime1(DateUtil.forDateFirst()+" 00:00:00"));
		entity.setRunningSetId("-");
		System.out.println(entity.getImplementDate());
		entity.setIsImplement(cmd.getIsImplement());
		entity.setIsDefault(true);

		return this;
	}
	
	public RebateSetManager createApply(CreateRebateSetCommand cmd){	
		entity.setId(UUIDGenerator.getUUID());
		entity.setProduct(cmd.getProduct());
		entity.setProductName(cmd.getProductName());
		entity.setDistributor(cmd.getDistributor());
		entity.setDistributorName(cmd.getDistributorName());
		entity.setLoginName(cmd.getLoginName());
		entity.setApplyDate(cmd.getApplyDate());
		entity.setCheckStatus(cmd.getCheckStatus());
		entity.setIntervalStr(cmd.getIntervalStr());
		entity.setIsCheck(false);
		//创建申请
		entity.setRunningSetId(cmd.getRunningSetId());
		entity.setApplyUser(cmd.getApplyUser());
		entity.setApplyUserName(cmd.getApplyUserName());
		entity.setIsDefault(false);

		return this;
	}
	
	
	public RebateSetManager aduit(AduitRebateSetCommand cmd,String runningSetId){	
		if(cmd.getIsImplement()){
		//将生效时间设置为次月，例如本月是7月，则生效时间为2016-08-01 00:00:00 
			entity.setImplementDate(DateUtil.parseDateTime1(DateUtil.getNextFirstMonthdate()+" 00:00:00"));
			entity.setIsImplement(true);
			entity.setCheckStatus(RebateSet.CHECK_STATUS_PASS);
		}else{
			entity.setCheckStatus(RebateSet.CHECK_STATUS_REFUSE);
		}
		entity.setCheckDate(new Date());
		entity.setCheckUser(cmd.getCheckUser());
		entity.setCheckUserName(cmd.getCheckUserName());
		entity.setRunningSetId(runningSetId);
		return this;
	}
	
	public RebateSetManager modify(ModifyRebateSetCommand cmd){	
		if(cmd.getImplementDate()!=null)
			entity.setImplementDate(cmd.getImplementDate());
		if(cmd.getIsImplement()!=null)
		entity.setIsImplement(cmd.getIsImplement());
		if(cmd.getInvalidDate()!=null)
		entity.setInvalidDate(cmd.getInvalidDate());
		if(cmd.getIsCheck()!=null)
			entity.setIsCheck(cmd.getIsCheck());
		if(cmd.getRunningSetId()!=null)
			entity.setRunningSetId(cmd.getRunningSetId());
		if(cmd.getIsDefault()!=null)
			entity.setIsDefault(cmd.getIsDefault());
		if(cmd.getIntervalStr()!=null)
			entity.setIntervalStr(cmd.getIntervalStr());
		return this;
	}

}

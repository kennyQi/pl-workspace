package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.fx.command.abnormalRule.ModifyAbnormalRuleCommand;
import hg.fx.domain.AbnormalRule;


/**
 * @author yangkang
 * @date 2016-06-02
 * */
public class AbnormalRuleManager extends BaseDomainManager<AbnormalRule>{

	public AbnormalRuleManager(AbnormalRule entity) {
		super(entity);
	}

	/**
	 * 修改规则
	 * */
	public AbnormalRuleManager modifyAbnormalRuleManager(ModifyAbnormalRuleCommand command){
		if (command.getDayMax()!=null)
			entity.setDayMax(command.getDayMax());
		
		if (command.getMouthMax()!=null)
			entity.setMouthMax(command.getMouthMax());
		
		if (command.getOrderUnitMax()!=null)
			entity.setOrderUnitMax(command.getOrderUnitMax());
		
		return this;
	}
	
	
}

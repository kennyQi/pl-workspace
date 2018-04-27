package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.command.abnormalRule.CheckAbnormalRuleCommand;
import hg.fx.command.abnormalRule.ModifyAbnormalRuleCommand;
import hg.fx.domain.AbnormalRule;
import hg.fx.spi.qo.AbnormalRuleSQO;

public interface AbnormalRuleSPI extends BaseServiceProviderInterface{
	
	/**
	 * 条件查询
	 * */
	public List< AbnormalRule> queryList(AbnormalRuleSQO sqo);
	
	/**
	 * 条件查询某一实体
	 * */
	public AbnormalRule queryUnique(AbnormalRuleSQO sqo);
	
	/**
	 * 修改异常规则
	 * */
	public AbnormalRule modifyAbnormalRule(ModifyAbnormalRuleCommand command);
	
	/**
	 * 检查订单是否合规
	 * @return String null时候通过  否则返回错误信息
	 * */
	public String conformOrderToTheRule(CheckAbnormalRuleCommand command);
	
}

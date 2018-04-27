package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.AbnormalRuleSQO;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "abnormalRuleDAO")
public class AbnormalRuleQO extends BaseHibernateQO<String> {
	
	public AbnormalRuleQO(){}
	
	public AbnormalRuleQO(String id){
		this.setId(id);
	}
	
	
	
	public static AbnormalRuleQO bulid(AbnormalRuleSQO sqo){
		AbnormalRuleQO qo = new AbnormalRuleQO();
		return qo;
	}
	

}

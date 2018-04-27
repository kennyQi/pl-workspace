package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import hg.demo.member.service.qo.hibernate.fx.AbnormalRuleQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.AbnormalRule;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *@author yangkang
 *@date 2016-06-01
 */
@Repository("abnormalRuleDAO")
public class AbnormalRuleDAO extends BaseHibernateDAO<AbnormalRule, AbnormalRuleQO>{

	@Override
	protected Class<AbnormalRule> getEntityClass() {
		return AbnormalRule.class;
	}

	@Override
	protected void queryEntityComplete(AbnormalRuleQO qo,
			List<AbnormalRule> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, AbnormalRuleQO qo) {
		return criteria;
	}

}

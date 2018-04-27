package hg.demo.member.service.dao.hibernate.fx;

import hg.demo.member.service.qo.hibernate.fx.RebateIntervalQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.rebate.RebateInterval;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository("rebateIntervalDAO")
public class RebateIntervalDAO extends BaseHibernateDAO<RebateInterval, RebateIntervalQO> {

	@Override
	protected Class<RebateInterval> getEntityClass() {
		// TODO Auto-generated method stub
		return RebateInterval.class;
	}

	@Override
	protected void queryEntityComplete(RebateIntervalQO qo, List<RebateInterval> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, RebateIntervalQO qo) {
		// TODO Auto-generated method stub
		
		return criteria;
	}

}

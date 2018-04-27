package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.OldDistributorQo;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.Distributor;

@Repository
public class OldDistributorDao extends BaseHibernateDAO<Distributor, OldDistributorQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, OldDistributorQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.isNameLike()) {
					criteria.add(Restrictions.like("name", qo.getName(),MatchMode.ANYWHERE));
				}else {
					criteria.add(Restrictions.like("name", qo.getName()));
				}
			} 
			if (qo.getStatus() != null) {
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
		}
		criteria.addOrder(Order.desc("createDate"));

		return criteria;
	}

	@Override
	protected Class<Distributor> getEntityClass() {
		return Distributor.class;
	}

	protected void queryEntityComplete(OldDistributorQo qo, List<Distributor> list) {
		//  
		
	}

}

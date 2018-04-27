package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.distributor.Distributor;
import hg.common.component.BaseDao;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.DistributorQo;

@Repository
public class DistributorDao extends BaseDao<Distributor, DistributorQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DistributorQo qo) {
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

}

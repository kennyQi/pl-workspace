package jxc.app.dao.system;

import hg.common.component.BaseDao;
import hg.pojo.qo.UnitQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.system.Unit;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UnitDao extends BaseDao<Unit, UnitQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, UnitQO qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.isRemoved()));
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.getNameLike()) {
					criteria.add(Restrictions.ilike("unitName", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("unitName", qo.getName()));
				}
			}
		}

		criteria.addOrder(Order.desc("createDate"));

		return criteria;
	}

	@Override
	protected Class<Unit> getEntityClass() {
		return Unit.class;
	}

}

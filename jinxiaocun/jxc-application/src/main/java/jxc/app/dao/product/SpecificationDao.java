package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.pojo.qo.SpecificationQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.Specification;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SpecificationDao extends BaseDao<Specification, SpecificationQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SpecificationQO qo) {
		criteria.createCriteria("productCategory", JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getProductCategoryId())) {
				criteria.add(Restrictions.eq("productCategory.id", qo.getProductCategoryId()));
			}
			if (StringUtils.isNotBlank(qo.getSpecName())) {
				if (qo.getSpecNameLike() != null && qo.getSpecNameLike()) {
					criteria.add(Restrictions.like("specName", qo.getSpecName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("specName", qo.getSpecName()));
				}
			}
			if (qo.getUsing() != null) {
				criteria.add(Restrictions.eq("status.using", qo.getUsing()));
			}
		}

		criteria.addOrder(Order.asc("createDate"));

		return criteria;
	}

	@Override
	protected Class<Specification> getEntityClass() {
		return Specification.class;
	}

}

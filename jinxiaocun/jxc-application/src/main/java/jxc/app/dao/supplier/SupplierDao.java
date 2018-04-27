package jxc.app.dao.supplier;

import hg.common.component.BaseDao;
import hg.pojo.qo.SupplierQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.supplier.Supplier;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDao extends BaseDao<Supplier, SupplierQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SupplierQO qo) {

		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getSupplierName())) {

				if (qo.getSupplierNameLike() != null && qo.getSupplierNameLike()) {
					criteria.add(Restrictions.like("baseInfo.name", qo.getSupplierName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("baseInfo.name", qo.getSupplierName()));
				}
			}

			if (StringUtils.isNotBlank(qo.getSupplierCode())) {
				criteria.add(Restrictions.like("supplierCode", qo.getSupplierCode(), MatchMode.ANYWHERE));
			}

			if (qo.getSupplierStatus() != null) {
				criteria.add(Restrictions.eq("status.status", qo.getSupplierStatus()));
			}

			if (qo.getSupplierType() != null) {
				criteria.add(Restrictions.eq("baseInfo.type", qo.getSupplierType()));
			}

			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
		}

		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

	@Override
	protected Class<Supplier> getEntityClass() {
		return Supplier.class;
	}

}

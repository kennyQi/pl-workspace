package jxc.app.dao.warehousing;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.purchaseorder.PurchaseOrder;
import hg.common.component.BaseDao;
import hg.pojo.qo.PurchaseOrderQo;

@Repository
public class PurchaseOrderDao extends BaseDao<PurchaseOrder, PurchaseOrderQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PurchaseOrderQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
		}
		return criteria;
	}

	@Override
	protected Class<PurchaseOrder> getEntityClass() {
		return PurchaseOrder.class;
	}

}

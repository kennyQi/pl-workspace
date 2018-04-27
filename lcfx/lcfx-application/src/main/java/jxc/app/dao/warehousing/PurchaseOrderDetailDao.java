package jxc.app.dao.warehousing;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.purchaseorder.PurchaseOrderDetail;
import hg.common.component.BaseDao;
import hg.pojo.qo.PurchaseOrderDetailQo;

@Repository
public class PurchaseOrderDetailDao extends BaseDao<PurchaseOrderDetail, PurchaseOrderDetailQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PurchaseOrderDetailQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getPurchaseOrderId())) {
				criteria.add(Restrictions.eq("purchaseOrder.id", qo.getPurchaseOrderId()));
			}
			if (StringUtils.isNotBlank(qo.getSkuProductCode())) {
				criteria.add(Restrictions.eq("skuProduct.id", qo.getSkuProductCode()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<PurchaseOrderDetail> getEntityClass() {
		return PurchaseOrderDetail.class;
	}

}

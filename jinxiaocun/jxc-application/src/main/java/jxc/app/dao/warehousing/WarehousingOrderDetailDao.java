package jxc.app.dao.warehousing;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.warehouseing.order.WarehousingOrderDetail;
import hg.common.component.BaseDao;
import hg.pojo.qo.WarehousingOrderDetailQo;

@Repository
public class WarehousingOrderDetailDao extends BaseDao<WarehousingOrderDetail, WarehousingOrderDetailQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehousingOrderDetailQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getWarehousingOrderId())) {
				criteria.add(Restrictions.eq("purchaseOrder.id", qo.getWarehousingOrderId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<WarehousingOrderDetail> getEntityClass() {
		return WarehousingOrderDetail.class;
	}

}

package jxc.app.dao.warehousing;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.warehouseing.order.WarehousingOrder;
import hg.common.component.BaseDao;
import hg.pojo.qo.WarehousingOrderQo;

@Repository
public class WarehousingOrderDao extends BaseDao<WarehousingOrder, WarehousingOrderQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehousingOrderQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

			// 仓库
			if (qo.getWarehouseId() != null) {
				criteria.add(Restrictions.eq("warehouse", qo.getWarehouseId()));
			}
			// 入库单类型
			if (qo.getOrderType() != null) {
				criteria.add(Restrictions.eq("orderType", qo.getOrderType()));
			}
			// 项目
			if (qo.getProjectId() != null) {
				criteria.add(Restrictions.eq("project", qo.getProjectId()));
			}
			// 供应商
			if (qo.getSupplierId() != null) {
				criteria.add(Restrictions.eq("supplier", qo.getSupplierId()));
			}
			// 关闭
			if (qo.getStatusClosed() != null) {
				criteria.add(Restrictions.eq("statusClosed", qo.getStatusClosed()));
			}
			// 采购单编号
			if (StringUtils.isNotBlank(qo.getPurchaseOrderCode())) {
				criteria.add(Restrictions.like("purchaseOrderCode", qo.getPurchaseOrderCode(), MatchMode.ANYWHERE));
			}
			// 下单日期
			if (qo.getOrderDateBegin() != null) {
				criteria.add(Restrictions.ge("orderDate", qo.getOrderDateBegin()));
			}
			if (qo.getOrderDateEnd() != null) {
				criteria.add(Restrictions.le("orderDate", qo.getOrderDateEnd()));
			}
			// 是否质检
			if (qo.getStatusQualityChecking() != null) {
				criteria.add(Restrictions.eq("statusQualityChecking", qo.getStatusQualityChecking()));
			}
			return criteria;
		}

		return criteria;
	}

	@Override
	protected Class<WarehousingOrder> getEntityClass() {
		return WarehousingOrder.class;
	}

}

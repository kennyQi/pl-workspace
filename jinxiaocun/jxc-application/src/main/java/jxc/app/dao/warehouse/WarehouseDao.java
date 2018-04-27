package jxc.app.dao.warehouse;

import hg.common.component.BaseDao;
import hg.pojo.qo.WarehouseQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.warehouse.Warehouse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseDao extends BaseDao<Warehouse, WarehouseQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehouseQO qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getWarehouseTypeId())) {
				criteria.add(Restrictions.eq("type.id", qo.getWarehouseTypeId()));
			}
			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Warehouse> getEntityClass() {
		return Warehouse.class;
	}

}

package jxc.app.dao.warehouse;

import hg.common.component.BaseDao;
import hg.pojo.qo.WarehouseTypeQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.warehouse.WarehouseType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseTypeDao extends BaseDao<WarehouseType, WarehouseTypeQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehouseTypeQO qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.isRemoved()));
			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
			
			if(qo.getUsing()!=null){
				criteria.add(Restrictions.eq("using", qo.getUsing()));
			}

		}
		return criteria;
	}

	@Override
	protected Class<WarehouseType> getEntityClass() {
		return WarehouseType.class;
	}

}

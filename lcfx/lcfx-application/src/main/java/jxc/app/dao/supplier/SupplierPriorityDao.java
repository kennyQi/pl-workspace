package jxc.app.dao.supplier;

import hg.common.component.BaseDao;
import hg.pojo.qo.SupplierPriorityQO;
import jxc.domain.model.supplier.SupplierPriority;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class SupplierPriorityDao extends BaseDao<SupplierPriority,SupplierPriorityQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, SupplierPriorityQO qo) {

		if(qo != null){
			
			if(StringUtils.isNotBlank(qo.getSupplierPriorityPolicyId())){
				criteria.add(Restrictions.eq("policy.id",qo.getSupplierPriorityPolicyId()));
			}
			
			if(StringUtils.isNotBlank(qo.getSupplierId())){
				criteria.add(Restrictions.eq("supplier.id",qo.getSupplierId()));
			}
			
			if(StringUtils.isNotBlank(qo.getProjectId())){
				criteria.add(Restrictions.eq("project.id",qo.getProjectId()));
			}
		}
		criteria.addOrder(Order.asc("priority"));
		return criteria;
	}

	@Override
	protected Class<SupplierPriority> getEntityClass() {
		return SupplierPriority.class;
	}

}

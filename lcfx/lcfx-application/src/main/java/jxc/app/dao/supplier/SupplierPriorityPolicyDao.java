package jxc.app.dao.supplier;

import hg.common.component.BaseDao;
import hg.pojo.qo.SupplierPriorityPolicyQO;
import jxc.domain.model.supplier.SupplierPriorityPolicy;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class SupplierPriorityPolicyDao extends BaseDao<SupplierPriorityPolicy,SupplierPriorityPolicyQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, SupplierPriorityPolicyQO qo) {
		
		if(qo != null){
			
			if(StringUtils.isNotBlank(qo.getProjectName())){
				criteria.add(Restrictions.like("projectName"," "+qo.getProjectName()+" ", MatchMode.ANYWHERE));
			}
			
			if(StringUtils.isNotBlank(qo.getSupplierName())){
				criteria.add(Restrictions.like("supplierName"," "+qo.getSupplierName()+" ", MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<SupplierPriorityPolicy> getEntityClass() {
		return SupplierPriorityPolicy.class;
	}

}

package zzpl.app.dao.user;


import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.CostCenter;
import zzpl.pojo.qo.user.CostCenterQO;

@Repository
public class CostCenterDAO extends BaseDao<CostCenter, CostCenterQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CostCenterQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
			if(qo.getStatus()!=null){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<CostCenter> getEntityClass() {
		return CostCenter.class;
	}

	
}

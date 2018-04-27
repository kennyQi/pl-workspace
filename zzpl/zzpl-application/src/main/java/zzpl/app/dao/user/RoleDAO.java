package zzpl.app.dao.user;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Role;
import zzpl.pojo.qo.user.RoleQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;
@Repository
public class RoleDAO extends BaseDao<Role, RoleQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, RoleQO qo) {
		
		if(qo!=null){
			if(qo.getRoleName()!=null&&StringUtils.isNotBlank(qo.getRoleName())){
				criteria.add(Restrictions.like("roleName", qo.getRoleName(),MatchMode.ANYWHERE));
			}
			
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("company.id", qo.getCompanyID()));
			}
			
		}
		criteria.addOrder(Order.asc("sort"));
		return criteria;
	}

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

	public int maxProperty(String propertyName, RoleQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
}

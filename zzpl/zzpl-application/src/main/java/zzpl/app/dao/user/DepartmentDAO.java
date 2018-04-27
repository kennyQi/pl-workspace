package zzpl.app.dao.user;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Department;
import zzpl.pojo.dto.user.status.DepartmentStatus;
import zzpl.pojo.qo.user.DepartmentQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

@Repository
public class DepartmentDAO extends BaseDao<Department, DepartmentQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DepartmentQO qo) {
		criteria.add(Restrictions.eq("status", DepartmentStatus.NORMAL));
		
		if(qo!=null){
			
			if(qo.getDepartmentName()!=null&&StringUtils.isNotBlank(qo.getDepartmentName())){
				criteria.add(Restrictions.like("departmentName", qo.getDepartmentName(),MatchMode.ANYWHERE));
			}
			
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("company.id", qo.getCompanyID()));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}

	public int maxProperty(String propertyName, DepartmentQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
}

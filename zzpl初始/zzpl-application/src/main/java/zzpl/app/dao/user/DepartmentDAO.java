package zzpl.app.dao.user;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Department;
import zzpl.pojo.dto.user.status.DepartmentStatus;
import zzpl.pojo.qo.user.DepartmentQO;
import hg.common.component.BaseDao;

@Repository
public class DepartmentDAO extends BaseDao<Department, DepartmentQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DepartmentQO qo) {
		criteria.add(Restrictions.eq("status", DepartmentStatus.NORMAL));
		return criteria;
	}

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}

}

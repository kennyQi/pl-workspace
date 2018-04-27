package hg.demo.member.service.dao.hibernate;

import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.demo.member.common.domain.model.Department;
import hg.demo.member.service.qo.hibernate.DepartmentQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhurz
 */
@Repository("departmentDAO")
public class DepartmentDAO extends BaseHibernateDAO<Department, DepartmentQO> {

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}

	@Override
	protected void queryEntityComplete(DepartmentQO qo, List<Department> list) {

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, DepartmentQO qo) {
		return criteria;
	}
}

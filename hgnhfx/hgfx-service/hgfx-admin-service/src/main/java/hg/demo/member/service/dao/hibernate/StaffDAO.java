package hg.demo.member.service.dao.hibernate;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.service.qo.hibernate.StaffQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2016/5/20.
 */
@Repository("staffDAO")
public class StaffDAO extends BaseHibernateDAO<Staff, StaffQO> {
	@Override
	protected Class<Staff> getEntityClass() {
		return Staff.class;
	}

	@Override
	protected void queryEntityComplete(StaffQO qo, List<Staff> list) {
		if (qo != null) {
			if (qo.isAuthRoleSetFetch() && list != null && !list.isEmpty()) {
				AuthUser authUser = list.get(0).getAuthUser();
				if (authUser != null) {
					authUser.getAuthRoleSet();
					Hibernate.initialize(authUser.getAuthRoleSet());
				}
			}
		}
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, StaffQO qo) {
		return criteria;
	}
}

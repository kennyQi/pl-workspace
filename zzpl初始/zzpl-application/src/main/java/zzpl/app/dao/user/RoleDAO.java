package zzpl.app.dao.user;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Role;
import zzpl.pojo.qo.user.RoleQO;
import hg.common.component.BaseDao;
@Repository
public class RoleDAO extends BaseDao<Role, RoleQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, RoleQO qo) {
		return criteria;
	}

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

}

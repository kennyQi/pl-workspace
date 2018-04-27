package zzpl.app.dao.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.UserRole;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.qo.user.UserRoleQO;
import hg.common.component.BaseDao;

@Repository
public class UserRoleDAO extends BaseDao<UserRole, UserRoleQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, UserRoleQO qo) {
		if (qo != null) {
			Criteria userCriteria = criteria.createCriteria("user");
			if (qo.getRoleID() != null
					&& StringUtils.isNotBlank(qo.getRoleID())) {
				criteria.add(Restrictions.eq("role.id", qo.getRoleID()));
			}
			if (qo.getUserID() != null
					&& StringUtils.isNotBlank(qo.getUserID())) {
				criteria.add(Restrictions.eq("user.id", qo.getUserID()));
			}
			if(StringUtils.isNotBlank(qo.getCompanyIDLike())){
				userCriteria.add(Restrictions.like("companyID", qo.getCompanyIDLike(),MatchMode.ANYWHERE));
			}
			userCriteria.add(Restrictions.eq("status", UserStatus.NORMAL));
		}
		return criteria;
	}

	@Override
	protected Class<UserRole> getEntityClass() {
		return UserRole.class;
	}

	@Override
	public UserRole queryUnique(UserRoleQO qo) {
		UserRole userRole = super.queryUnique(qo);
		Hibernate.initialize(userRole.getRole());
		Hibernate.initialize(userRole.getUser());
		return userRole;
	}

	@Override
	public List<UserRole> queryList(UserRoleQO qo) {
		List<UserRole> userRoles = super.queryList(qo);
		for (UserRole userRole : userRoles) {
			Hibernate.initialize(userRole.getRole());
			Hibernate.initialize(userRole.getUser());
		}
		return userRoles;
	}
}

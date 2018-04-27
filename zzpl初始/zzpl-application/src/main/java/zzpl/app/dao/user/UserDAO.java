package zzpl.app.dao.user;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.User;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.qo.user.UserQO;
import hg.common.component.BaseDao;
@Repository
public class UserDAO extends BaseDao<User, UserQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, UserQO qo) {
		if(qo!=null){
			if(qo.getId()!=null&&StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(qo.getRoleID()!=null){
				Criteria userRoleCriteria=criteria.createCriteria("userRoles");
				userRoleCriteria.add(Restrictions.eq("role.id", qo.getRoleID()));
			}
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
		}
		criteria.add(Restrictions.eq("status", UserStatus.NORMAL));
		return criteria;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}

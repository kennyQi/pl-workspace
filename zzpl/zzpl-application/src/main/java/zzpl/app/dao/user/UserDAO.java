package zzpl.app.dao.user;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.User;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.qo.user.UserQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;
@Repository
public class UserDAO extends BaseDao<User, UserQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, UserQO qo) {
		if(qo!=null){
			if(qo.getId()!=null&&StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
				return criteria;
			}
			if(qo.getRoleID()!=null&&StringUtils.isNotBlank(qo.getRoleID())){
				Criteria userRoleCriteria=criteria.createCriteria("userRoles");
				userRoleCriteria.add(Restrictions.eq("role.id", qo.getRoleID()));
			}
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
			if(qo.getName()!=null&&StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(),MatchMode.ANYWHERE));
			}
			if(qo.getUserNo()!=null&&StringUtils.isNotBlank(qo.getUserNo())){
				criteria.add(Restrictions.eq("userNO", qo.getUserNo()));
			}
			if(qo.getDepartmentID()!=null&&StringUtils.isNotBlank(qo.getDepartmentID())){
				criteria.add(Restrictions.eq("departmentID", qo.getDepartmentID()));
			}
			if(qo.getLoginName()!=null&&StringUtils.isNotBlank(qo.getLoginName())){
				criteria.add(Restrictions.eq("loginName", qo.getLoginName()));
			}
			if(qo.getPassword()!=null&&StringUtils.isNotBlank(qo.getPassword())){
				criteria.add(Restrictions.eq("password", qo.getPassword()));
			}
			if(qo.getIdCardNO()!=null&&StringUtils.isNotBlank(qo.getIdCardNO())){
				criteria.add(Restrictions.eq("idCardNO", qo.getIdCardNO()));
			}
			
			if(qo.getShowLoginName()!=null&&StringUtils.isNotBlank(qo.getShowLoginName())){
				criteria.add(Restrictions.eq("showLoginName", qo.getShowLoginName()));
			}
			
		}
		criteria.add(Restrictions.eq("status", UserStatus.NORMAL));
		return criteria;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
	
	public String maxProperty(String propertyName, UserQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		String number = ((String) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? "0": number;
	}

}

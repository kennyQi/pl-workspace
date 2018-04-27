package hg.system.dao;

import java.util.List;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.system.model.staff.Staff;
import hg.system.qo.StaffQo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository("staffDao")
public class StaffDao extends BaseDao<Staff, StaffQo> {

	@Override
	protected Class<Staff> getEntityClass() {
		return Staff.class;
	}

	@Override
	public Criteria buildCriteria(Criteria criteria, StaffQo qo) {
		Criteria auserCri = criteria.createCriteria("authUser", JoinType.INNER_JOIN);
		if (qo != null) {
			// 登录名
			if (StringUtils.isNotBlank(qo.getLoginName())) {
				if (qo.getIsLoginNameLike()) {
					auserCri.add(Restrictions.like("loginName", qo.getLoginName(), MatchMode.ANYWHERE));
				} else {
					auserCri.add(Restrictions.eq("loginName", qo.getLoginName()));
				}
			}
			// 真实姓名
			if (StringUtils.isNotBlank(qo.getRealName())) {
				if (qo.getIsRealNameLike()) {
					criteria.add(Restrictions.like("info.realName", qo.getRealName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("info.realName", qo.getRealName()));
				}
			}
			// 电子邮箱
			if (StringUtils.isNotBlank(qo.getEmail())) {
				if (qo.isEmailLike())
					criteria.add(Restrictions.like("info.email", qo.getEmail(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("info.email", qo.getEmail()));
			}
			// 手机号
			if (StringUtils.isNotBlank(qo.getMobile())) {
				if(qo.getIsMobileLike())
					criteria.add(Restrictions.like("info.mobile", qo.getMobile(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("info.mobile", qo.getMobile()));
			}
			// 创建时间开始
			if (qo.getCreateDateBegin() != null) {
				criteria.add(Restrictions.ge("createDate", qo.getCreateDateBegin()));
			}
			// 创建时间截止
			if (qo.getCreateDateEnd() != null) {
				criteria.add(Restrictions.le("createDate", qo.getCreateDateEnd()));
			}
			// 是否可用
			if (qo.getEnable() != null) {
				auserCri.add(Restrictions.eq("enable", qo.getEnable()));
			}
			// 员工角色
			if (StringUtils.isNotBlank(qo.getRoleId())) {
				Criteria roleCriteria = auserCri.createCriteria("authRoleSet");
				roleCriteria.add(Restrictions.eq("id", qo.getRoleId()));
			}
		}
		return criteria;
	}

	@Override
	public List<Staff> queryList(StaffQo qo, Integer offset, Integer maxSize) {
		List<Staff> list = super.queryList(qo, offset, maxSize);
		if (qo != null && qo.isQueryAuthRole()) {
			for (Staff staff : list) {
				Hibernate.initialize(staff.getAuthUser().getAuthRoleSet());
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		StaffQo qo = null;
		if (pagination.getCondition() instanceof StaffQo)
			qo = (StaffQo) pagination.getCondition();
		List<Staff> list = (List<Staff>) resultPagination.getList();
		if (qo != null && qo.isQueryAuthRole()) {
			for (Staff staff : list) {
				Hibernate.initialize(staff.getAuthUser().getAuthRoleSet());
			}
		}
		return resultPagination;
	}
	
	
}


















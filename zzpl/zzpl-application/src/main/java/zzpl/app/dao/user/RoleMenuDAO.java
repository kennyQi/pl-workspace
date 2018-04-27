package zzpl.app.dao.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.RoleMenu;
import zzpl.pojo.qo.user.RoleMenuQO;
import hg.common.component.BaseDao;
@Repository
public class RoleMenuDAO extends BaseDao<RoleMenu, RoleMenuQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, RoleMenuQO qo) {
		if(qo!=null){
			if(qo.getRoleID()!=null&&StringUtils.isNotBlank(qo.getRoleID())){
				criteria.add(Restrictions.eq("role.id", qo.getRoleID()));
			}
			if(qo.getMenuID()!=null&&StringUtils.isNotBlank(qo.getMenuID())){
				criteria.add(Restrictions.eq("menu.id", qo.getMenuID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<RoleMenu> getEntityClass() {
		return RoleMenu.class;
	}

	@Override
	public RoleMenu queryUnique(RoleMenuQO qo) {
		RoleMenu roleMenu = super.queryUnique(qo);
		Hibernate.initialize(roleMenu.getRole());
		Hibernate.initialize(roleMenu.getMenu());
		return roleMenu;
	}

	@Override
	public List<RoleMenu> queryList(RoleMenuQO qo) {
		List<RoleMenu> roleMenus = super.queryList(qo);
		for (RoleMenu roleMenu : roleMenus) {
			Hibernate.initialize(roleMenu.getRole());
			Hibernate.initialize(roleMenu.getMenu());
		}
		return roleMenus;
	}
}

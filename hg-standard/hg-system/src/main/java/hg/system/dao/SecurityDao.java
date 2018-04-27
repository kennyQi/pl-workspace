package hg.system.dao;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.model.auth.AuthUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("securityDao")
@SuppressWarnings("unchecked")
public class SecurityDao extends HibernateSimpleDao implements ISecurityDao {

	public AuthUser findUserByLoginName(String loginName) {
		String hql="from AuthUser where loginName=?";
		return (AuthUser) super.findUnique(hql, loginName);
	}

	public List<AuthPerm> findUserPerms(String loginName) {
		String hql="from AuthUser where loginName=?";
		AuthUser usr=(AuthUser) super.findUnique(hql, loginName);
		Set<AuthPerm> permSet=new HashSet<AuthPerm>();
		if(usr!=null){
			Set<AuthRole> roleSet=usr.getAuthRoleSet();
			for(AuthRole role:roleSet){
				for(AuthPerm perm:role.getAuthPermSet()){
					permSet.add(perm);
				}
			}
		}
		List<?> list=Arrays.asList(permSet.toArray());
		return (List<AuthPerm>) list;
	}

	public List<AuthPerm> findAllPerms() {
		String hql="from AuthPerm order by id asc";
		return super.find(hql);
	}

	public List<AuthRole> findUserRoles(String loginName) {
		String hql = "from AuthUser where loginName=?";
		AuthUser usr = (AuthUser) super.findUnique(hql, loginName);
		Set<AuthRole> roleSet = Collections.EMPTY_SET;
		if (usr != null) {
			roleSet = usr.getAuthRoleSet();
		}
		List<?> list = Arrays.asList(roleSet.toArray());
		return (List<AuthRole>) list;
	}

	public Pagination findPermPagination(Pagination pagination) {
		Criteria criteria=super.getSession().createCriteria(AuthPerm.class);
		if(pagination.getCondition() != null && pagination.getCondition() instanceof Map){
			Map<String, Object> condition = (Map<String, Object>) pagination.getCondition();
			if(condition.get("permType") != null){
				criteria.add(Restrictions.eq("permType", condition.get("permType")));
			}
			if(condition.get("urlLike") != null){
				criteria.add(Restrictions.like("url", condition.get("urlLike").toString(), MatchMode.ANYWHERE));
			}
		}
		criteria.addOrder(Order.asc("id"));
		return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
	}

	public List<AuthRole> findAllRoles() {
		String hql = "from AuthRole order by id asc";
		return super.find(hql);
	}

	public List<AuthPerm> findPermsByRoleId(String id) {
		List<AuthPerm> permList = new ArrayList<AuthPerm>();
		AuthRole role=super.get(AuthRole.class, id);
		if(role != null){
			permList.addAll(role.getAuthPermSet());
		}
		return permList;
	}

	public List<AuthRole> findRolesByUserId(String id) {
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		AuthUser usr=super.get(AuthUser.class, id);
		if(usr != null){
			roleList.addAll(usr.getAuthRoleSet());
		}
		return roleList;
	}

	public AuthRole findRoleByRoleName(String roleName) {
		String hql="from AuthRole where roleName=?";
		return (AuthRole) super.findUnique(hql, roleName);
	}

	public AuthRole findRoleById(String id) {
		return super.get(AuthRole.class, id);
	}

	public AuthPerm findPermById(String id) {
		return super.get(AuthPerm.class, id);
	}

	public AuthUser findUserById(String id) {
		return super.get(AuthUser.class, id);
	}

	public Pagination findRolePagination(Pagination pagination) {
		Criteria criteria=super.getSession().createCriteria(AuthRole.class);
		criteria.addOrder(Order.asc("id"));
		// 角色名条件
		Object object = pagination.getCondition();
		if (object != null && object instanceof String && object.toString().trim().length() > 0) {
			criteria.add(
						Restrictions.or(
							Restrictions.like("roleName", object.toString(), MatchMode.ANYWHERE), 
							Restrictions.like("displayName", object.toString(), MatchMode.ANYWHERE)
						)
					);
		}
		Pagination pa = super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
		for(Object ro: pa.getList()){
		    AuthRole r = (AuthRole) ro;
		    //load lazy
		    r.getAuthUserSet().size();
		}
		return pa;
	}

	public AuthRole insertRole(AuthRole role) {
		super.save(role);
		return role;
	}

	public AuthRole updateRole(AuthRole role) {
		super.update(role);
		return role;
	}

	public void deleteRoleById(String id) {
		AuthRole ar=super.get(AuthRole.class, id);
		if(ar != null){
			super.delete(ar);
		}
	}

	public AuthPerm insertPerm(AuthPerm perm) {
		super.save(perm);
		return perm;
	}

	public AuthPerm updatePerm(AuthPerm perm) {
		super.update(perm);
		return perm;
	}

	public void deletePermById(String id) {
		AuthPerm ap = super.get(AuthPerm.class, id);
		if(ap != null){
			super.delete(ap);
		}
	}

	public void deletePermByIds(List<String> ids) {
		for(String id:ids){
			deletePermById(id);
		}
	}

	public AuthUser insertUser(AuthUser usr) {
		super.save(usr);
		return usr;
	}

	public AuthUser updateUser(AuthUser usr) {
		super.update(usr);
		return usr;
	}

	public void deleteUserById(String id) {
		AuthUser au = super.get(AuthUser.class, id);
		if(au != null){
			super.delete(au);
		}
	}

}

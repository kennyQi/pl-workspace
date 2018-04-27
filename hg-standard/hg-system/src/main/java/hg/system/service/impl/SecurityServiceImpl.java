package hg.system.service.impl;

import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.dao.ISecurityDao;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.model.auth.AuthUser;
import hg.system.service.SecurityService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Resource
	private ISecurityDao securityDao;

	@Override
	public List<AuthPerm> findAllPerms() {
		return securityDao.findAllPerms();
	}

	@Override
	public List<String> findUserPerms(String loginName) {
		List<String> permTagList = new ArrayList<String>();
		List<AuthPerm> permList = securityDao.findUserPerms(loginName);
		for (AuthPerm perm : permList) {
			if (perm.getPermType().equals(SecurityConstants.PERM_TYPE_NAME)) {
				permTagList.add(perm.getUrl());
			} else {
				permTagList.add(MD5HashUtil.toMD5(perm.getUrl()));
			}
		}
		return permTagList;
	}

	@Override
	public List<AuthPerm> findUserPerms2(String loginName) {
		return securityDao.findUserPerms(loginName);
	}

	@Override
	public List<AuthRole> findAllRoles() {
		return securityDao.findAllRoles();
	}

	@Override
	public List<String> findUserRoles(String loginName) {
		List<String> roleNameList = new ArrayList<String>();
		List<AuthRole> roleList = securityDao.findUserRoles(loginName);
		for (AuthRole role : roleList) {
			roleNameList.add(role.getRoleName());
		}
		return roleNameList;
	}

	@Override
	public AuthUser findUserByLoginName(String loginName) {
		return securityDao.findUserByLoginName(loginName);
	}

	@Override
	public Pagination findPermPagination(Pagination pagination) {
		return securityDao.findPermPagination(pagination);
	}

	@Override
	public Pagination findRolePagination(Pagination pagination) {
		return securityDao.findRolePagination(pagination);
	}

	@Override
	public List<AuthPerm> findPermsByRoleId(String id) {
		return securityDao.findPermsByRoleId(id);
	}

	@Override
	public List<AuthRole> findRolesByUserId(String userId) {
		return securityDao.findRolesByUserId(userId);
	}

	@Override
	public void deletePermByIds(List<String> ids) {
		securityDao.deletePermByIds(ids);
	}

	@Override
	public AuthRole insertRole(AuthRole role) {
		if (role.getId() == null) {
			role.setId(UUIDGenerator.getUUID());
		}
		securityDao.insertRole(role);
		return role;
	}

	@Override
	public AuthRole insertRole2(AuthRole role, List<String> permIds) {
		if (role.getId() == null) {
			role.setId(UUIDGenerator.getUUID());
		}
		Set<AuthPerm> permSet = new LinkedHashSet<AuthPerm>();
		if (permIds != null) {
			for (String permId : permIds) {
				AuthPerm perm = securityDao.findPermById(permId);
				if (perm != null) {
					permSet.add(perm);
				}
			}
		}
		role.setAuthPermSet(permSet);
		securityDao.insertRole(role);
		return role;
	}

	@Override
	public AuthRole updateRole(AuthRole role) {
		securityDao.updateRole(role);
		return role;
	}

	@Override
	public AuthRole updateRole2(AuthRole role, List<String> permIds) {
		Set<AuthPerm> permSet = new LinkedHashSet<AuthPerm>();
		if (permIds != null) {
			for (String permId : permIds) {
				AuthPerm perm = securityDao.findPermById(permId);
				if (perm != null) {
					permSet.add(perm);
				}
			}
		}
		role.setAuthPermSet(permSet);
		securityDao.updateRole(role);
		return role;
	}

	@Override
	public void deleteRoleById(String id) {
		securityDao.deleteRoleById(id);
	}

	@Override
	public AuthRole findRoleByRoleName(String roleName) {
		return securityDao.findRoleByRoleName(roleName);
	}

	@Override
	public AuthPerm insertPerm(AuthPerm perm) {
		if (perm.getId() == null) {
			perm.setId(UUIDGenerator.getUUID());
		}
		securityDao.insertPerm(perm);
		return perm;
	}

	@Override
	public AuthPerm updatePerm(AuthPerm perm) {
		securityDao.updatePerm(perm);
		return perm;
	}

	@Override
	public void deletePermById(String id) {
		securityDao.deletePermById(id);
	}

	@Override
	public AuthRole findRoleById(String id) {
		return securityDao.findRoleById(id);
	}

	@Override
	public AuthPerm findPermById(String id) {
		return securityDao.findPermById(id);
	}

	@Override
	public AuthUser findUserById(String id) {
		return securityDao.findUserById(id);
	}

	@Override
	public AuthUser insertUser(AuthUser usr) {
		if (usr.getId() == null) {
			usr.setId(UUIDGenerator.getUUID());
		}
		securityDao.insertUser(usr);
		return usr;
	}

	@Override
	public AuthUser updateUser(AuthUser usr) {
		securityDao.updateUser(usr);
		return usr;
	}

	@Override
	public void deleteUserById(String id) {
		securityDao.deleteUserById(id);
	}

	@Override
	public AuthUser insertUser2(AuthUser usr, List<String> roleIds) {
		if (usr.getId() == null) {
			usr.setId(UUIDGenerator.getUUID());
		}
		Set<AuthRole> roleSet = new LinkedHashSet<AuthRole>();
		if (roleIds != null) {
			for (String roleId : roleIds) {
				AuthRole role = securityDao.findRoleById(roleId);
				if (role != null) {
					roleSet.add(role);
				}
			}
		}
		usr.setAuthRoleSet(roleSet);
		securityDao.insertUser(usr);
		return usr;
	}

	@Override
	public AuthUser updateUser2(AuthUser usr, List<String> roleIds) {
		Set<AuthRole> roleSet = new LinkedHashSet<AuthRole>();
		if (roleIds != null) {
			for (String roleId : roleIds) {
				AuthRole role = securityDao.findRoleById(roleId);
				if (role != null) {
					roleSet.add(role);
				}
			}
		}
		usr.setAuthRoleSet(roleSet);
		securityDao.updateUser(usr);
		return usr;
	}

	@Override
	public void updateUserPassword(String userId, String oldpwd, String newpwd) {
		AuthUser authUser = findUserById(userId);
		if (authUser != null && oldpwd != null && newpwd != null) {
			if (StringUtils.equalsIgnoreCase(authUser.getPasswd(),
					MD5HashUtil.toMD5(oldpwd))) {
				authUser.setPasswd(MD5HashUtil.toMD5(newpwd));
				securityDao.updateUser(authUser);
				return;
			} else {
				throw new RuntimeException("原始密码错误");
			}
		}

		throw new RuntimeException("修改密码失败");
	}

	@Override
	public AuthUser findUserByLoginName(String loginName, boolean fetchRole) {
		AuthUser authUser = findUserByLoginName(loginName);
		if (authUser != null && fetchRole)
			Hibernate.initialize(authUser.getAuthRoleSet());
		return authUser;
	}
}

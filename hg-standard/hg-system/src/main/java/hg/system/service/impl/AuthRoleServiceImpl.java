package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.role.CreateAuthRoleCommand;
import hg.system.command.role.ModifyAuthRoleCommand;
import hg.system.command.role.RemoveAuthRoleCommand;
import hg.system.dao.AuthPermDao;
import hg.system.dao.AuthRoleDao;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.qo.AuthRoleQo;
import hg.system.service.AuthRoleService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：权限_角色service实现
 * @类修改者：zzb
 * @修改日期：2014年11月3日下午3:51:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月3日下午3:51:04
 */
@Service
@Transactional
public class AuthRoleServiceImpl extends
		BaseServiceImpl<AuthRole, AuthRoleQo, AuthRoleDao> implements
		AuthRoleService {

	/**
	 * 角色dao
	 */
	@Autowired
	private AuthRoleDao authRoleDao;

	/**
	 * 资源dao
	 */
	@Autowired
	private AuthPermDao authPermDao;

	@Override
	public void createAuthRole(CreateAuthRoleCommand command)
			throws HGException {

		if (command == null)
			throw new HGException(HGException.AUTH_ROLE_COMMAND_NULL, "添加异常！");

		AuthRole authRole = new AuthRole();
		authRole.createAuthRole(command);

		// 添加资源关系
		String permIds = command.getPermIds();
		if (StringUtils.isNotBlank(permIds)) {
			Set<AuthPerm> permSet = new LinkedHashSet<AuthPerm>();
			for (String permId : permIds.split(",")) {
				AuthPerm perm = authPermDao.get(permId);
				if (perm != null) {
					permSet.add(perm);
				}
			}
			authRole.setAuthPermSet(permSet);
		}
		getDao().save(authRole);
	}

	@Override
	public List<AuthPerm> queryAuthPermByAuthRole(AuthRoleQo roleQo)
			throws HGException {

		if (roleQo == null || StringUtils.isBlank(roleQo.getId()))
			throw new HGException(HGException.AUTH_ROLE_QO_NULL, "编辑异常！");

		AuthRole authRole = get(roleQo.getId());
		List<AuthPerm> permList = new ArrayList<AuthPerm>();
		Set<AuthPerm> permSet = authRole.getAuthPermSet();
		if (authRole != null) {
			permList.addAll(permSet);
		}
		return permList;
	}

	@Override
	public void modifyAuthPerm(ModifyAuthRoleCommand command)
			throws HGException {

		if (command == null || StringUtils.isBlank(command.getRoleId()))
			throw new HGException(HGException.AUTH_ROLE_COMMAND_NULL, "编辑异常！");

		AuthRole authRole = get(command.getRoleId());
		if (authRole == null)
			throw new HGException(HGException.AUTH_ROLE_COMMAND_NULL, "编辑异常！");

		authRole.modifyAuthRole(command);

		// 添加资源关系
		String permIds = command.getPermIds();
		if (StringUtils.isNotBlank(permIds)) {
			Set<AuthPerm> permSet = new LinkedHashSet<AuthPerm>();
			for (String permId : permIds.split(",")) {
				AuthPerm perm = authPermDao.get(permId);
				if (perm != null) {
					permSet.add(perm);
				}
			}
			authRole.setAuthPermSet(permSet);
		}

		getDao().update(authRole);
	}

	@Override
	public void removeAuthRole(RemoveAuthRoleCommand command) {
		if (StringUtils.isNotBlank(command.getRoleIds())) {
			AuthRoleQo qo = new AuthRoleQo();
			qo.setIds(command.getRoleIdList());
			List<AuthRole> list = getDao().queryList(qo);
			for (AuthRole authPerm : list) {
				authPermDao.delete(authPerm);
			}
		}
	}
	
	@Override
	protected AuthRoleDao getDao() {
		return authRoleDao;
	}



}

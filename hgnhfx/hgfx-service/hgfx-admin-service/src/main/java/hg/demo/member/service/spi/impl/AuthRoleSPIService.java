/**
 * @AuthRoleSPIService.java Create on 2016-5-24上午10:15:37
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.spi.impl;

import java.util.List;
import java.util.Set;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.spi.AuthRoleSPI;
import hg.demo.member.common.spi.command.authRole.CreateAuthRoleCommand;
import hg.demo.member.common.spi.command.authRole.DeleteAuthRoleCommand;
import hg.demo.member.common.spi.command.authRole.ModifyAuthRoleCommand;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.demo.member.service.dao.hibernate.AuthRoleDao;
import hg.demo.member.service.domain.manager.AuthRoleManager;
import hg.demo.member.service.qo.hibernate.AuthRoleQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-24上午10:15:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-24上午10:15:37
 * @version：
 */
@Transactional
@Service("authRoleSPIService")
public class AuthRoleSPIService extends BaseService implements AuthRoleSPI{
	@Autowired
	private AuthRoleDao authRoleDao;
	@Override
	public Pagination<AuthRole> queryAuthPermPagination(AuthRoleSQO sqo) {
		Pagination<AuthRole> pagination = authRoleDao.queryPagination(AuthRoleQO.build(sqo));
		for (AuthRole authRole : pagination.getList()) {
			Hibernate.initialize(authRole.getAuthUserSet());
		}
		return pagination;
	}
	@Override
	public List<AuthRole> queryAuthRoles(AuthRoleSQO sqo) {
		List<AuthRole> authRoles = authRoleDao.queryList(AuthRoleQO.build(sqo));
		for (AuthRole authRole : authRoles) {
			Hibernate.initialize(authRole.getAuthUserSet());
		}
		return authRoles;
	}
	@Override
	public AuthRole queryAuthRole(AuthRoleSQO sqo) {
		AuthRole authRole = authRoleDao.queryUnique(AuthRoleQO.build(sqo));
		Hibernate.initialize(authRole.getAuthPermSet());
		Hibernate.initialize(authRole.getAuthUserSet());
		return authRole;
	}
	@Override
	public AuthRole createAuthRole(CreateAuthRoleCommand command,Set<AuthPerm> authPerms) {
		AuthRole authRole = new AuthRole();
		return authRoleDao.save(new AuthRoleManager(authRole).create(command,authPerms).get());
	}
	@Override
	public AuthRole modifyAuthRole(ModifyAuthRoleCommand command,Set<AuthPerm> authPerms) {
		AuthRole authRole = authRoleDao.get(command.getId());
		return authRoleDao.update(new AuthRoleManager(authRole).modify(command,authPerms).get());
	}
	@Override
	public void deleteAuthRole(DeleteAuthRoleCommand command) {
		AuthRole authRole = authRoleDao.get(command.getId());
		authRoleDao.delete(authRole);;
	}

}

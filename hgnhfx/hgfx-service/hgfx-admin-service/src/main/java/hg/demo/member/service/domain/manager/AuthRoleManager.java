/**
 * @AuthRoleManager.java Create on 2016-5-23下午2:43:46
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.domain.manager;

import java.util.Date;
import java.util.Set;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.spi.command.authRole.CreateAuthRoleCommand;
import hg.demo.member.common.spi.command.authRole.ModifyAuthRoleCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:43:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:43:46
 * @version：
 */
public class AuthRoleManager extends BaseDomainManager<AuthRole>{

	/**
	 * @类名：AuthRoleManager.java Created on 2016-5-23下午2:46:31
	 * 
	 * @Copyright (c) 2012 by www.hg365.com。
	 */
	public AuthRoleManager(AuthRole entity) {
		super(entity);
	}
	public AuthRoleManager create(CreateAuthRoleCommand command) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setCreateTime(new Date());
		entity.setRoleName(command.getRoleName());
		entity.setDisplayName(command.getDisplayName());
		return this;
	}
	
	public AuthRoleManager create(CreateAuthRoleCommand command,Set<AuthPerm> authPerms) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setCreateTime(new Date());
		entity.setRoleName(command.getRoleName());
		entity.setDisplayName(command.getDisplayName());
		if (authPerms !=null ) {
			entity.setAuthPermSet(authPerms);
		}
		return this;
	}
	
	public AuthRoleManager modify(ModifyAuthRoleCommand command,Set<AuthPerm> authPerms) {
		entity.setRoleName(command.getRoleName());
		entity.setDisplayName(command.getDisplayName());
		if (authPerms !=null ) {
			entity.setAuthPermSet(authPerms);
		}
		return this;
	}

}

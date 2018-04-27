/**
 * @AuthPermManager.java Create on 2016-5-23下午2:43:28
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.domain.manager;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.spi.command.authPerm.CreateOrModifyAuthPermCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:43:28
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:43:28
 * @version：
 */
public class AuthPermManager extends BaseDomainManager<AuthPerm>{


	public AuthPermManager(AuthPerm entity) {
		super(entity);
	}
	public AuthPermManager create(CreateOrModifyAuthPermCommand command, AuthPerm authPerm) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setDisplayName(command.getPerm().getDisplayName());
		entity.setUrl(command.getPerm().getUrl());
		entity.setPermType(command.getPerm().getPermType());
		entity.setPermRole(command.getPerm().getPermRole());
		entity.setParentId(command.getPerm().getParentId());
		entity.setRel(command.getPerm().getRel());
		entity.setAuthRoleSet(command.getPerm().getAuthRoleSet());
		return this;
	}
	public AuthPermManager modify(CreateOrModifyAuthPermCommand command, AuthPerm authPerm) {
		entity.setId(command.getPerm().getId());
		entity.setDisplayName(command.getPerm().getDisplayName());
		entity.setUrl(command.getPerm().getUrl());
		entity.setPermType(command.getPerm().getPermType());
		entity.setPermRole(command.getPerm().getPermRole());
		entity.setParentId(command.getPerm().getParentId());
		entity.setRel(command.getPerm().getRel());
		/*entity.setAuthRoleSet(command.getPerm().getAuthRoleSet());*/
		return this;
	}

}

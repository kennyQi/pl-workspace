/**
 * @AuthRoleSPI.java Create on 2016-5-23下午3:01:50
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.spi;

import java.util.List;
import java.util.Set;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.spi.command.authRole.CreateAuthRoleCommand;
import hg.demo.member.common.spi.command.authRole.DeleteAuthRoleCommand;
import hg.demo.member.common.spi.command.authRole.ModifyAuthRoleCommand;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午3:01:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午3:01:50
 * @version：
 */
public interface AuthRoleSPI extends BaseServiceProviderInterface{
	/**
	 * @Title: queryAuthPermPagination 
	 * @author gaoce
	 * @Description: 查询
	 * @Time 2016年5月26日下午5:43:32
	 * @param sqo
	 * @return Pagination<AuthRole> 设定文件
	 * @throws
	 */
	/**
	 * 分页查询角色
	 */
	Pagination<AuthRole> queryAuthPermPagination(AuthRoleSQO sqo);
	/**
	 * 查询角色列表
	 */
	List<AuthRole> queryAuthRoles(AuthRoleSQO sqo);
	/**
	 * 查询单个角色
	 */
	AuthRole queryAuthRole(AuthRoleSQO sqo);
	/**
	 * @Title: createAuthRole 
	 * @author guok
	 * @Description: 创建角色
	 * @Time 2016年5月27日上午10:56:52
	 * @param command
	 * @return AuthRole 设定文件
	 * @throws
	 */
	/**
	 * 创建角色
	 */
	AuthRole createAuthRole(CreateAuthRoleCommand command,Set<AuthPerm> authPerms);
	/**
	 * @Title: modifyAuthRole 
	 * @author guok
	 * @Description: 修改角色
	 * @Time 2016年5月27日上午11:01:32
	 * @param command
	 * @return AuthRole 设定文件
	 * @throws
	 */
	/**
	 * 修改角色
	 */
	AuthRole modifyAuthRole(ModifyAuthRoleCommand command,Set<AuthPerm> authPerms);
	/**
	 * @Title: deleteAuthRole 
	 * @author guok
	 * @Description: 删除角色
	 * @Time 2016年5月27日上午11:01:47
	 * @param command void 设定文件
	 * @throws
	 */
	/**
	 * 删除角色
	 */
	void deleteAuthRole(DeleteAuthRoleCommand command);
	
}

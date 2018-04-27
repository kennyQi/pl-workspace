package hg.system.service;

import hg.common.page.Pagination;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.model.auth.AuthUser;
import java.util.List;

/**
 * @类功能说明: 安全服务接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public interface SecurityService {
	/**
	 * ------------------------------ 角色 --------------------------------
	 */
	/** 添加角色 */
	public AuthRole insertRole(AuthRole role);
	public AuthRole insertRole2(AuthRole role, List<String> permIds);
	
	/** 删除角色 */
	public void deleteRoleById(String id);
	
	/** 更新角色 */
	public AuthRole updateRole(AuthRole role);
	public AuthRole updateRole2(AuthRole role, List<String> permIds);
	
	/** 根据id查询角色 */
	public AuthRole findRoleById(String id);
	
	/** 获取所有角色 */
	public List<AuthRole> findAllRoles();
	
	/** 根据用户ID获取角色 */
	public List<AuthRole> findRolesByUserId(String userId);
	
	/** 根据角色名称查询角色 */
	public AuthRole findRoleByRoleName(String roleName);
	
	/** 根据角色ID查询所具有的权限 */
	public List<AuthPerm> findPermsByRoleId(String id);
	
	/** 查询用户所具有的角色名列表 */
	public List<String> findUserRoles(String loginName);
	
	/** 获取ROLE分页 */
	public Pagination findRolePagination(Pagination pagination);
	
	/**
	 * ------------------------------ 权限 --------------------------------
	 */
	/** 添加权限 */
	public AuthPerm insertPerm(AuthPerm perm);
	
	/** 删除权限 */
	public void deletePermById(String id);
	public void deletePermByIds(List<String> ids);
	
	/** 更新权限 */
	public AuthPerm updatePerm(AuthPerm perm);
	
	/** 根据id查询权限 */
	public AuthPerm findPermById(String id);
	
	/** 查询所有资源权限（排序） */
	public List<AuthPerm> findAllPerms();

	/** 取得所有资源许可 */
	public List<String> findUserPerms(String loginName);

	/** 取得所有资源许可 */
	public List<AuthPerm> findUserPerms2(String loginName);

	/** 获取PERM分页 */
	public Pagination findPermPagination(Pagination pagination);
	
	/**
	 * ------------------------------ 用户 --------------------------------
	 */
	/** 添加用户 */
	public AuthUser insertUser(AuthUser usr);
	public AuthUser insertUser2(AuthUser usr, List<String> roleIds);
	
	/** 删除用户 */
	public void deleteUserById(String id);
	
	/** 更新用户 */
	public AuthUser updateUser(AuthUser usr);
	public AuthUser updateUser2(AuthUser usr, List<String> roleIds);
	
	/** 更新用户密码 */
	public void updateUserPassword(String userId, String oldpwd, String newpwd);
	
	/** 根据id查询用户 */
	public AuthUser findUserById(String id);
	
	/** 根据用户名查找用户 */
	public AuthUser findUserByLoginName(String loginName);
	
	/** 根据用户名查找用户及其角色 */
	public AuthUser findUserByLoginName(String loginName, boolean fetchRole);
}
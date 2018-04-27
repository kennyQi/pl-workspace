package hg.system.dao;

import hg.common.page.Pagination;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.model.auth.AuthUser;

import java.util.List;

public interface ISecurityDao {

	/** 根据登录名查找用户 */
	public AuthUser findUserByLoginName(String loginName);
	
	/** 查询用户所有允许访问的资源 */
	public List<AuthPerm> findUserPerms(String loginName);
	
	/** 取得所有资源许可 */
	public List<AuthPerm> findAllPerms();
	
	/** 查询用户所具有的角色名列表 */
	public List<AuthRole> findUserRoles(String loginName);
	
	/** 查询资源地址（分页） */
//	public List<AuthPerm> findPermPaginationList(Pagination pagination);
//	public Integer findPermPaginationListCount(Pagination pagination);
	public Pagination findPermPagination(Pagination pagination);
	
	/** 查询所有角色 */
	public List<AuthRole> findAllRoles();
	
	/** 根据角色ID查询所具有的权限 */
	public List<AuthPerm> findPermsByRoleId(String id);
	
	/** 根据用户ID获取角色 */
	public List<AuthRole> findRolesByUserId(String id);
	
	/** 根据角色名查询角色 */
	public AuthRole findRoleByRoleName(String roleName);

	public AuthRole findRoleById(String id);
	public AuthPerm findPermById(String id);
	public AuthUser findUserById(String id);

//	public List<AuthRole> findRolePaginationList(Pagination pagination);
//	public Integer findRolePaginationListCount(Pagination pagination);
	public Pagination findRolePagination(Pagination pagination);
	
	public AuthRole insertRole(AuthRole role);
	public AuthRole updateRole(AuthRole role);
	public void deleteRoleById(String id);
	
	public AuthPerm insertPerm(AuthPerm perm);
	public AuthPerm updatePerm(AuthPerm perm);
	public void deletePermById(String id);
	public void deletePermByIds(List<String> ids);
	
	public AuthUser insertUser(AuthUser usr);
	public AuthUser updateUser(AuthUser usr);
	public void deleteUserById(String id);
	
}

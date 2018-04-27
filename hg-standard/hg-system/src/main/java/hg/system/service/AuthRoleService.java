package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.role.CreateAuthRoleCommand;
import hg.system.command.role.ModifyAuthRoleCommand;
import hg.system.command.role.RemoveAuthRoleCommand;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.qo.AuthRoleQo;

import java.util.List;

/**
 * 
 * @类功能说明：权限_角色service接口
 * @类修改者：zzb
 * @修改日期：2014年11月3日下午3:48:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月3日下午3:48:46
 *
 */
public interface AuthRoleService extends BaseService<AuthRole, AuthRoleQo> {

	/**
	 * @方法功能说明：角色创建
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日下午2:50:38
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void createAuthRole(CreateAuthRoleCommand command) throws HGException;
	
	/**
	 * @方法功能说明：根据角色查询资源
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日下午2:58:11
	 * @修改内容：
	 * @参数：@param roleQo
	 * @参数：@return
	 * @return:AuthRole
	 * @throws
	 */
	List<AuthPerm> queryAuthPermByAuthRole(AuthRoleQo roleQo) throws HGException;

	/**
	 * @方法功能说明：角色更新
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日下午2:50:55
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void modifyAuthPerm(ModifyAuthRoleCommand command) throws HGException;

	/**
	 * @方法功能说明：删除角色
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日上午9:22:36
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void removeAuthRole(RemoveAuthRoleCommand command);
}

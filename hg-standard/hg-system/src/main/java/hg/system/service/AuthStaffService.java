package hg.system.service;

import java.util.List;

import hg.common.component.BaseService;
import hg.system.command.staff.CreateAuthStaffCommand;
import hg.system.command.staff.ModifyAuthStaffCommand;
import hg.system.command.staff.RemoveAuthStaffCommand;
import hg.system.command.staff.ResetAuthStaffPwdCommand;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthRole;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;

/**
 * @类功能说明：员工service
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:01:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:01:24
 */
public interface AuthStaffService extends BaseService<Staff, AuthStaffQo> {

	/**
	 * @方法功能说明：创建员工
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午2:42:03
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void createAuthStaff(CreateAuthStaffCommand command) throws HGException;

	/**
	 * @throws HGException 
	 * @方法功能说明：根据员工查询所属角色
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午3:21:46
	 * @修改内容：
	 * @参数：@param staffQo
	 * @参数：@return
	 * @return:List<AuthRole>
	 * @throws
	 */
	List<AuthRole> queryAuthRoleByStaff(AuthStaffQo staffQo) throws HGException;
	
	/**
	 * @方法功能说明：更新员工
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午2:42:16
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void modifyAuthPerm(ModifyAuthStaffCommand command) throws HGException;

	
	/**
	 * @方法功能说明：删除员工
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午2:42:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void removeAuthStaff(RemoveAuthStaffCommand command) throws HGException;

	/**
	 * @方法功能说明：重置员工密码
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午3:50:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void resetAuthStaffPwd(ResetAuthStaffPwdCommand command);


}

package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.staff.CreateAuthStaffCommand;
import hg.system.command.staff.ModifyAuthStaffCommand;
import hg.system.command.staff.RemoveAuthStaffCommand;
import hg.system.command.staff.ResetAuthStaffPwdCommand;
import hg.system.dao.AuthRoleDao;
import hg.system.dao.AuthStaffDao;
import hg.system.dao.AuthUserDao;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthRole;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;
import hg.system.service.AuthStaffService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：权限_角色service
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:38:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:38:04
 */
@Service
@Transactional
public class AuthStaffServiceImpl extends
		BaseServiceImpl<Staff, AuthStaffQo, AuthStaffDao> implements
		AuthStaffService {

	/**
	 * 员工dao
	 */
	@Autowired
	private AuthStaffDao 	authStaffDao;
	
	/**
	 * 用户dao
	 */
	@Autowired
	private AuthUserDao 	authUserDao;
	
	/**
	 * 角色dao
	 */
	@Autowired
	private AuthRoleDao 	authRoleDao;
	
	@Override
	public void createAuthStaff(CreateAuthStaffCommand command)
			throws HGException {
		
		if (command == null || StringUtils.isBlank(command.getLoginName()))
			throw new HGException(HGException.STAFF_COMMAND_NULL, "添加异常！");

		// 1. 判断登录名是否唯一
		AuthStaffQo staffQo = new AuthStaffQo();
		staffQo.setLoginName(command.getLoginName());
		Staff nameTestStaff = getDao().queryUnique(staffQo);
		if(nameTestStaff != null)
			throw new HGException(HGException.STAFF_LOGIN_NAME_EXISTS, "登录名已存在！");
		
		Staff staff = new Staff();
		staff.createStaff(command);

		// 2. 添加资源关系
		String roleIds = command.getRoleIds();
		if (StringUtils.isNotBlank(roleIds)) {
			Set<AuthRole> roleSet = new LinkedHashSet<AuthRole>();
			for (String roleId : roleIds.split(",")) {
				AuthRole role = authRoleDao.get(roleId);
				if (role != null) {
					roleSet.add(role);
				}
			}
			staff.getAuthUser().setAuthRoleSet(roleSet);
		}
		
		authUserDao.save(staff.getAuthUser());
		getDao().save(staff);
	}

	@Override
	public void modifyAuthPerm(ModifyAuthStaffCommand command)
			throws HGException {
		
		if (command == null || StringUtils.isBlank(command.getStaffId()))
			throw new HGException(HGException.STAFF_COMMAND_NULL, "编辑异常！");

		Staff staff = get(command.getStaffId());
		if (staff == null)
			throw new HGException(HGException.STAFF_COMMAND_NULL, "编辑异常！");

		staff.modifyStaff(command);

		// 添加资源关系
		String roleIds = command.getRoleIds();
		if (StringUtils.isNotBlank(roleIds)) {
			Set<AuthRole> roleSet = new LinkedHashSet<AuthRole>();
			for (String roleId : roleIds.split(",")) {
				AuthRole role = authRoleDao.get(roleId);
				if (role != null) {
					roleSet.add(role);
				}
			}
			staff.getAuthUser().setAuthRoleSet(roleSet);
		}
		
		if (staff.getAuthUser() != null)
			authUserDao.update(staff.getAuthUser());
		getDao().update(staff);
		
	}
	
	@Override
	public List<AuthRole> queryAuthRoleByStaff(AuthStaffQo staffQo) throws HGException {
		
		if (staffQo == null || StringUtils.isBlank(staffQo.getId()))
			throw new HGException(HGException.STAFF_QO_NULL, "编辑异常！");

		Staff staff = get(staffQo.getId());
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		Set<AuthRole> roleSet = staff.getAuthUser().getAuthRoleSet();
		if (roleSet != null) {
			roleList.addAll(roleSet);
		}
		return roleList;
	}

	@Override
	public void removeAuthStaff(RemoveAuthStaffCommand command)
			throws HGException {
		if (StringUtils.isNotBlank(command.getStaffIds())) {
			AuthStaffQo qo = new AuthStaffQo();
			qo.setIds(command.getStaffIdList());
			List<Staff> list = getDao().queryList(qo);
			for (Staff staff : list) {
				getDao().delete(staff);
			}
		}
	}

	@Override
	public void resetAuthStaffPwd(ResetAuthStaffPwdCommand command) {
		if (StringUtils.isNotBlank(command.getStaffIds())) {
			AuthStaffQo qo = new AuthStaffQo();
			qo.setIds(command.getStaffIdList());
			List<Staff> list = getDao().queryList(qo);
			for (Staff staff : list) {
				staff.resetPwd();
				getDao().update(staff);
			}
		}
	}
	
	@Override
	protected AuthStaffDao getDao() {
		return authStaffDao;
	}

}

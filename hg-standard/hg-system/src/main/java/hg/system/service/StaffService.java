package hg.system.service;

import hg.common.component.BaseService;
import hg.system.model.staff.Staff;
import hg.system.qo.StaffQo;

/**
 * 员工Service
 * 
 * @author zhurz
 */
public interface StaffService extends BaseService<Staff, StaffQo> {
	
	/**
	 * 检查登录名是否已存在
	 * @param qo
	 * @return
	 */
	public Boolean isExistLoginName(String loginName);
	
	
	/**
	 * 添加操作员
	 * @param staff
	 */
	public void savaOperAndRole(Staff staff, String[] roleIds);
	
	
	/**
	 * 更新操作员
	 * @param staff
	 * @param roleIds
	 */
	public void updateOperAndRole(Staff staff, String[] roleIds);
}

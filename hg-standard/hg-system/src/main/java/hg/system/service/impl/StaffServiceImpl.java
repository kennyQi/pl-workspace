package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.ISecurityDao;
import hg.system.dao.StaffDao;
import hg.system.model.auth.AuthRole;
import hg.system.model.staff.Staff;
import hg.system.qo.StaffQo;
import hg.system.service.StaffService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("staffService")
public class StaffServiceImpl extends BaseServiceImpl<Staff, StaffQo, StaffDao> implements StaffService {
	
	@Resource
	private StaffDao staffDao; 
	
	@Resource
	private ISecurityDao securityDao;

	@Override
	protected StaffDao getDao() {
		return staffDao;
	}

	@Override
	public Boolean isExistLoginName(String loginName) {
		StaffQo qo = new StaffQo();
		qo.setLoginName(loginName);
		List<Staff> list = staffDao.queryList(qo);
		if(list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void savaOperAndRole(Staff staff, String[] roleIds) {
		
		Set<AuthRole> roleSet=new LinkedHashSet<AuthRole>();
		if(roleIds!=null){
			for(String id : roleIds){
				AuthRole ar = securityDao.findRoleById(id);
				roleSet.add(ar);
			}
		}
		
		staff.getAuthUser().setAuthRoleSet(roleSet);
		
		securityDao.insertUser(staff.getAuthUser());
		staffDao.save(staff);
	}

	@Override
	public void updateOperAndRole(Staff staff, String[] roleIds) {
		
		Set<AuthRole> roleSet=new LinkedHashSet<AuthRole>();
		if(roleIds!=null){
			for(String id : roleIds){
				AuthRole ar = securityDao.findRoleById(id);
				roleSet.add(ar);
			}
		}
		
		staff.getAuthUser().setAuthRoleSet(roleSet);
		staff.getAuthUser().setId(staff.getId());
		
		securityDao.updateUser(staff.getAuthUser());
		staffDao.update(staff);
	}
	
}

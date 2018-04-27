package zzpl.app.service.local.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.DepartmentDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddDepartmentCommand;
import zzpl.pojo.command.user.DeleteDepartmentCommand;
import zzpl.pojo.command.user.ModifyDepartmentCommand;
import zzpl.pojo.dto.user.status.DepartmentStatus;
import zzpl.pojo.qo.user.DepartmentQO;
import zzpl.pojo.qo.user.UserQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

@Service
@Transactional
public class DepartmentService extends
		BaseServiceImpl<Department, DepartmentQO, DepartmentDAO> {

	@Autowired
	private DepartmentDAO departmentDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * @Title: deleteDepartment 
	 * @author guok
	 * @Description: 删除
	 * @time 2015年6月25日 15:36:42
	 * @param command void 设定文件
	 * @throws
	 */
	public void deleteDepartment(DeleteDepartmentCommand command) {
		Department department=departmentDAO.get(command.getDepartmentID());
		department.setStatus(command.getStauts());
		departmentDAO.update(department);
	}
	
	/**
	 * @throws Exception 
	 * @Title: addDepartment 
	 * @author guok
	 * @Description: 添加
	 * @Time 2015年6月25日 15:37:02
	 * @param command void 设定文件
	 * @throws
	 */
	public void addDepartment(AddDepartmentCommand command) throws Exception {
		HgLogger.getInstance().info("cs", "【DepartmentService】【addDepartment】"+JSON.toJSONString(command));
		//查询用户
		UserQO userQo=new UserQO();
		userQo.setId(command.getUserID());
		User user =userDAO.queryUnique(userQo);
		
		//根据用户表中公司ID查询公司是否存在
		Company company=companyDAO.get(user.getCompanyID());
		if (company==null) {
			throw new Exception("公司不存在");
		}
		if(company!=null){
			//设置值
			Department department=new Department();
			department.setId(UUIDGenerator.getUUID());
			department.setDepartmentName(command.getDepartmentName());
			department.setDescription(command.getDescription());
			department.setCreateTime(new Date());
			department.setStatus(DepartmentStatus.NORMAL);
			department.setSort(departmentDAO.maxProperty("sort", new DepartmentQO())+1);
			department.setCompany(company);
			departmentDAO.save(department);
		}
		
	}
	
	/**
	 * @Title: modfiyDepartment 
	 * @author guok
	 * @Description: 修改
	 * @Time 2015年6月25日 15:37:23
	 * @param command void 设定文件
	 * @throws
	 */
	public void modfiyDepartment(ModifyDepartmentCommand command) {
		HgLogger.getInstance().info("cs", "【DepartmentService】【modfiyDepartment】"+JSON.toJSONString(command));
		Department department=departmentDAO.get(command.getId());
		department.setDepartmentName(command.getDepartmentName());
		department.setDescription(command.getDescription());
		departmentDAO.update(department);
	}

	@Override
	protected DepartmentDAO getDao() {
		return departmentDAO;
	}

}

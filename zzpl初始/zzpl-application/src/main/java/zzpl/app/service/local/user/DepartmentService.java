package zzpl.app.service.local.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.DepartmentDAO;
import zzpl.domain.model.user.Department;
import zzpl.pojo.qo.user.DepartmentQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class DepartmentService extends
		BaseServiceImpl<Department, DepartmentQO, DepartmentDAO> {

	@Autowired
	private DepartmentDAO departmentDAO;

	@Override
	protected DepartmentDAO getDao() {
		return departmentDAO;
	}

}

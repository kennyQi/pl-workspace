package hg.demo.member.service.spi.impl;

import hg.demo.member.common.domain.vo.DepartmentVO;
import hg.demo.member.service.dao.hibernate.DepartmentDAO;
import hg.demo.member.service.dao.hibernate.MemberDAO;
import hg.demo.member.service.dao.mybatis.DepartmentMybatisDAO;
import hg.demo.member.service.domain.manager.DeparmentManager;
import hg.demo.member.service.qo.hibernate.DepartmentQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.command.department.DeleteDepartmentCommand;
import hg.demo.member.common.spi.command.department.ModifyDepartmentCommand;
import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.domain.model.Member;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import hg.demo.member.common.spi.DepartmentSPI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhurz
 */
@Transactional
@Service("departmentSPIService")
public class DepartmentSPIService extends BaseService implements DepartmentSPI {

	@Autowired
	private DepartmentDAO dao;
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private DepartmentMybatisDAO departmentMybatisDAO;

	@Override
	public Department create(CreateDepartmentCommand command) {
		Department department = new Department();
		Member manager = null;
		if (StringUtils.isNotBlank(command.getManagerMemberId())) {
			manager = memberDAO.get(command.getManagerMemberId());
		}
		return dao.save(new DeparmentManager(department).create(command, manager).get());
	}

	@Override
	public Department modify(ModifyDepartmentCommand command) {
		return null;
	}

	@Override
	public void delete(DeleteDepartmentCommand command) {

	}

	@Override
	public Pagination<Department> queryDepartmentPagination(DepartmentSQO sqo) {
		return dao.queryPagination(DepartmentQO.build(sqo));
	}

	@Override
	public List<Department> queryDepartmentList(DepartmentSQO sqo) {
		return null;
	}

	@Override
	public Department queryDepartment(DepartmentSQO sqo) {
		return null;
	}

	public List<DepartmentVO> queryListTop20() {
		return departmentMybatisDAO.queryListTop20();
	}

}

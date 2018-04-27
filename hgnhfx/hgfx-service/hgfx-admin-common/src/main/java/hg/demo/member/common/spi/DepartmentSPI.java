package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.command.department.ModifyDepartmentCommand;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.demo.member.common.spi.command.department.DeleteDepartmentCommand;
import hg.demo.member.common.spi.qo.DepartmentSQO;

import java.util.List;

/**
 * @author zhurz
 */
public interface DepartmentSPI extends BaseServiceProviderInterface {

	/**
	 * 创建部门
	 *
	 * @param command 命令
	 * @return
	 */
	Department create(CreateDepartmentCommand command);

	Department modify(ModifyDepartmentCommand command);

	void delete(DeleteDepartmentCommand command);

	/**
	 * 分页查询部门
	 *
	 * @param sqo 查询对象
	 * @return
	 */
	Pagination<Department> queryDepartmentPagination(DepartmentSQO sqo);

	List<Department> queryDepartmentList(DepartmentSQO sqo);

	Department queryDepartment(DepartmentSQO sqo);

}

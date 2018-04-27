package hg.demo.member.service.domain.manager;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.domain.model.Member;

/**
 * @author zhurz
 */
public class DeparmentManager extends BaseDomainManager<Department> {

	public DeparmentManager(Department entity) {
		super(entity);
	}

	/**
	 * 创建部门
	 *
	 * @param command 创建命令
	 * @param manager 部门主管
	 * @return
	 */
	public DeparmentManager create(CreateDepartmentCommand command, Member manager) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setName(command.getName());
		entity.setManager(manager);
		return this;
	}

}

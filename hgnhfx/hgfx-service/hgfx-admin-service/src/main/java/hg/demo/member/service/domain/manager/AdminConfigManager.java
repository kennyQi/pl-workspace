package hg.demo.member.service.domain.manager;

//-------------------------------------
//AdminConfigManager

import hg.demo.member.common.domain.model.adminconfig.AdminConfig;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.DeleteAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.ModifyAdminConfigCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

public class AdminConfigManager extends BaseDomainManager<AdminConfig> {

	public AdminConfigManager(AdminConfig entity) {
		super(entity);
	}

	/**
	 * 修改
	 * 
	 * @param command
	 *            修改命令
	 * @return
	 */
	public AdminConfigManager modify(ModifyAdminConfigCommand command) {
		entity.setDataKey(command.getDataKey());
		entity.setValue(command.getValue());
		return this;
	}

	/**
	 * 删除
	 * 
	 * @param command
	 *            删除命令
	 * @return
	 */
	public AdminConfigManager delete(DeleteAdminConfigCommand command) {
		entity.setId(command.getId());
		return this;
	}

	/**
	 * 创建
	 * 
	 * @param command
	 *            创建命令
	 * @return
	 */
	public AdminConfigManager create(CreateAdminConfigCommand command) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setDataKey(command.getDataKey());
		entity.setValue(command.getValue());
		return this;
	}

}

package hg.demo.member.service.domain.manager;

import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.common.spi.command.parameter.CreateParameterCommand;
import hg.demo.member.common.spi.command.parameter.ModifyParameterCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

public class ParameterManager extends BaseDomainManager<Parameter> {

	public ParameterManager(Parameter entity) {
		super(entity);
	}
	
	/**
	 * @Title: create 
	 * @author guok
	 * @Description: 创建商城参数
	 * @Time 2016年5月24日上午11:18:58
	 * @param command
	 * @return ParameterManager 设定文件
	 * @throws
	 */
	public ParameterManager create(CreateParameterCommand command) {
		entity.setId(UUIDGenerator.getUUID());
		entity.setName(command.getName());
		entity.setValue(command.getValue());
		return this;
	}
	
	public ParameterManager modity(ModifyParameterCommand command) {
		entity.setId(command.getId());
		entity.setName(command.getName());
		entity.setValue(command.getValue());
		return this;
	}

}

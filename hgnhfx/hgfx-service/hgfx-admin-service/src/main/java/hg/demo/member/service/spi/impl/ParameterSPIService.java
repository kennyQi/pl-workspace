package hg.demo.member.service.spi.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.common.spi.ParameterSPI;
import hg.demo.member.common.spi.command.parameter.CreateParameterCommand;
import hg.demo.member.common.spi.command.parameter.DeleteParameterCommand;
import hg.demo.member.common.spi.command.parameter.ModifyParameterCommand;
import hg.demo.member.common.spi.qo.ParameterSQO;
import hg.demo.member.service.dao.hibernate.ParameterDAO;
import hg.demo.member.service.domain.manager.ParameterManager;
import hg.demo.member.service.qo.hibernate.ParameterQO;
import hg.framework.service.component.base.BaseService;

@Transactional
@Service("parameterSPIService")
public class ParameterSPIService extends BaseService implements ParameterSPI {

	@Autowired
	private ParameterDAO dao;
	
	/**
	 * 添加参数
	 */
	@Override
	public Parameter create(CreateParameterCommand command) {
		Parameter parameter = new Parameter();
		return dao.save(new ParameterManager(parameter).create(command).get());
	}

	/**
	 * 修改参数
	 */
	@Override
	public Parameter modify(ModifyParameterCommand command) {
		Parameter parameter = new Parameter();
		return dao.update(new ParameterManager(parameter).modity(command).get());
	}

	@Override
	public void delete(DeleteParameterCommand command) {
		dao.deleteById(command.getId());
	}

	/**
	 * 查询参数
	 */
	@Override
	public List<Parameter> queryParameters(ParameterSQO qo) {
		return dao.queryList(ParameterQO.build(qo));
	}
	
	@Override
	public Parameter queryParameter(ParameterSQO qo) {
		Parameter parameter = dao.queryFirst(ParameterQO.build(qo));
		return parameter;
	}

}

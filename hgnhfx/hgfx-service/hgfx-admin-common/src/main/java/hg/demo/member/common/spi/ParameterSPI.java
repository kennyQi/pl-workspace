package hg.demo.member.common.spi;

import java.util.List;

import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.common.spi.command.parameter.CreateParameterCommand;
import hg.demo.member.common.spi.command.parameter.DeleteParameterCommand;
import hg.demo.member.common.spi.command.parameter.ModifyParameterCommand;
import hg.demo.member.common.spi.qo.ParameterSQO;
import hg.framework.common.base.BaseServiceProviderInterface;

public interface ParameterSPI extends BaseServiceProviderInterface {

	/**
	 * @Title: create 
	 * @author guok
	 * @Description: 保存商城参数
	 * @Time 2016年5月24日上午10:48:23
	 * @param command
	 * @return Parameter 设定文件
	 * @throws
	 */
	/**
	 * 保存商城参数
	 */
	Parameter create(CreateParameterCommand command);
	
	/**
	 * @Title: modify 
	 * @author guok
	 * @Description: 修改商城参数
	 * @Time 2016年5月24日上午10:48:52
	 * @param command
	 * @return Parameter 设定文件
	 * @throws
	 */
	/**
	 * 修改商城参数
	 */
	Parameter modify(ModifyParameterCommand command);
	
	/**
	 * @Title: delete 
	 * @author guok
	 * @Description: 删除商城参数
	 * @Time 2016年5月24日上午10:50:35
	 * @param command void 设定文件
	 * @throws
	 */
	/**
	 * 删除商城参数
	 */
	void delete(DeleteParameterCommand command);
	
	/**
	 * 查询商城参数
	 * @Title: queryParameter 
	 * @author guok
	 * @Description: 查询商城参数
	 * @Time 2016年5月24日上午11:02:38
	 * @param qo
	 * @return Parameter 设定文件
	 * @throws
	 */
	/**
	 * 查询商城参数
	 */
	Parameter queryParameter(ParameterSQO qo);
	/**
	 * 查询商城参数列表
	 */
	List<Parameter> queryParameters(ParameterSQO qo);
}

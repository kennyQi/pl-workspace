package hg.demo.member.common.spi.command.parameter;

import hg.framework.common.base.BaseSPICommand;

/**
 * 创建参数数据
 */
@SuppressWarnings("serial")
public class CreateParameterCommand extends BaseSPICommand {

	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数值
	 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

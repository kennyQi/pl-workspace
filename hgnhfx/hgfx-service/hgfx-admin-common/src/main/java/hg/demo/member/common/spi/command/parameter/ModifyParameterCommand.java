package hg.demo.member.common.spi.command.parameter;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
public class ModifyParameterCommand extends BaseSPICommand {
	
	/**
	 * 参数ID
	 */
	private String id;
	
	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数值
	 */
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

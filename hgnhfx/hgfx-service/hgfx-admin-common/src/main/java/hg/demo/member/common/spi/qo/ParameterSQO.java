package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

@SuppressWarnings("serial")
public class ParameterSQO extends BaseSPIQO {
	
	/**
	 * 名称
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

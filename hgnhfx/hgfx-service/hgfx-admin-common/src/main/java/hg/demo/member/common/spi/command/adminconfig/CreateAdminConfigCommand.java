package hg.demo.member.common.spi.command.adminconfig;

import hg.framework.common.base.BaseSPICommand;

import javax.persistence.Column;

public class CreateAdminConfigCommand extends BaseSPICommand{
	/**
	*配置值
	*/
	private String value;

	/**
	*键
	*/
	private String dataKey;

	public String getValue() {
		return value;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	
	
	
}
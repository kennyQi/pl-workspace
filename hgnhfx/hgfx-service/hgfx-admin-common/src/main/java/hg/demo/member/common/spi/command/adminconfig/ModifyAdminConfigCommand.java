package hg.demo.member.common.spi.command.adminconfig;

import hg.framework.common.base.BaseSPICommand;

import javax.persistence.Column;
/**
 * 修改命令
 * @author Administrator
 * @date 2016-5-30
 * @since
 */
public class ModifyAdminConfigCommand extends BaseSPICommand {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	/**
	 * 配置值
	 */
	private String value;

	/**
	 * 键
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
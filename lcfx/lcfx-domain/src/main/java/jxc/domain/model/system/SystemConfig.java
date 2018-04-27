package jxc.domain.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.M;

import hg.common.component.BaseModel;
import hg.pojo.command.ModifySystemConfigCommand;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "SYSTEM_CONFIG")
public class SystemConfig extends BaseModel {
	@Column(name = "CONFIG_VALUE", columnDefinition = M.TEXT_COLUM)
	private String configValue;

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public void modifySystemConfig(String key, String configValue) {
		setId(key);
		setConfigValue(configValue);
	}

}

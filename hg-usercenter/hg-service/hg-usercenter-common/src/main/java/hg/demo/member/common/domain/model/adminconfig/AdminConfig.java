package hg.demo.member.common.domain.model.adminconfig;

import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@SuppressWarnings("serial")
@Table(name = "HG_ADMIN_ADMIN_CONFIG")
public class AdminConfig extends BaseStringIdModel {
	/**
	*配置值
	*/
	@Column(name = "VALUE", length = 200)
	private String value;

	/**
	*键
	*/
	@Column(name = "DATA_KEY", length = 100)
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
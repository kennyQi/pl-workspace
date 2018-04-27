package hg.system.model.config;

import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 系统key value数据配置
 * 
 * @author yxx
 */
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX_SYS + "KVCONFIG")
public class KVConfig extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "DATA_KEY", length = 64)
	private String dataKey;

	@Column(name = "DATA_VALUE", length = 4000)
	private String dataValue;

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

}

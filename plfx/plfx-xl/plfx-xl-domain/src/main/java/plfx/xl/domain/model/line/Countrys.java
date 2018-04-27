package plfx.xl.domain.model.line;


import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = M.TABLE_PREFIX_SYS + "COUNTRY")
@SuppressWarnings("serial")
public class Countrys extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 国家名称
	 */
	@Column(name = "NAME", length = 32)
	private String name;
	
	/**
	 * 国家代码
	 */
	@Column(name = "CODE", length = 8)
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}

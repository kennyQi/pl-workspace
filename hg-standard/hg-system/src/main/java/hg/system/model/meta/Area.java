package hg.system.model.meta;

import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = M.TABLE_PREFIX_SYS + "AREA")
public class Area extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 32)
	private String name;

	@Column(name = "CODE", length = 8)
	private String code;

	@Column(name = "PARENT", length = 8)
	private String parentCode;
	
	public Area() {
	}
	
	public Area(String code, String name, String parentCode) {
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
	}

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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}

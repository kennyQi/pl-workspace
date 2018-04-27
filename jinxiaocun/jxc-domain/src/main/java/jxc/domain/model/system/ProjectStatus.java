package jxc.domain.model.system;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class ProjectStatus {
	
	/**
	 * 项目是否启用
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_USING")
	private Boolean using;

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}
	
	

}

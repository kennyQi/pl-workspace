package jxc.domain.model.product;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class CategoryStatus {

	/**
	 * 是否启用
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_USING")
	private  Boolean using;

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}
	
}

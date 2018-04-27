package jxc.domain.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@MappedSuperclass
public class JxcBaseModel extends BaseModel {
	public static final String PROPERTY_NAME_STATUS_REMOVED = "statusRemoved";

	@Type(type = "yes_no")
	@Column(name = "STATUS_REMOVED", columnDefinition = "char(1) NOT NULL DEFAULT 'N'")
	private Boolean statusRemoved;

	public Boolean getStatusRemoved() {
		return statusRemoved;
	}

	public void setStatusRemoved(Boolean statusRemoved) {
		this.statusRemoved = statusRemoved;
	}

}

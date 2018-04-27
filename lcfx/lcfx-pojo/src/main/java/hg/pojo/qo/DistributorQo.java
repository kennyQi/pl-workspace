package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class DistributorQo extends BaseQo{
	private boolean nameLike = true;
	
	private String name;

	private Integer status;

	public String getName() {
		return name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}
}

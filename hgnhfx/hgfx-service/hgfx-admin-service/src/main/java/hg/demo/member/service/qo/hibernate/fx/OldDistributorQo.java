package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.domain.Distributor;

@SuppressWarnings("serial")
public class OldDistributorQo extends BaseHibernateQO<Distributor>{
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

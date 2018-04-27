package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.domain.ReserveInfo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId="reserveInfoDao")
public class ReserveInfoQO extends BaseHibernateQO<ReserveInfo>{
	private String distributorId;

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
}

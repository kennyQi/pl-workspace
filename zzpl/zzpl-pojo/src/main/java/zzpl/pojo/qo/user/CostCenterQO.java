package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class CostCenterQO extends BaseQo {

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 状态
	 */
	private Integer status;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

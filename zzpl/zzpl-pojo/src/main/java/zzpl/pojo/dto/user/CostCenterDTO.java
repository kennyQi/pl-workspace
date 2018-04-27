package zzpl.pojo.dto.user;

import zzpl.pojo.dto.BaseDTO;

/**
 * 
 * @类功能说明：成本中心
 * @类修改者：
 * @修改日期：2015年8月6日下午1:51:19
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年8月6日下午1:51:19
 */
@SuppressWarnings("serial")
public class CostCenterDTO extends BaseDTO {

	/**
	 * 成本中心名称
	 */
	private String costCenterName;

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 公司名称
	 */
	private String companyName;

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

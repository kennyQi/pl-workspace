package zzpl.domain.model.user;

import hg.common.component.BaseModel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;

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
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "COST_CENTER")
public class CostCenter extends BaseModel{

	/**
	 * 成本中心名称
	 */
	@Column(name = "COST_CENTER_NAME", length = 512)
	private String costCenterName;
	
	/**
	 * 公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;
	
	/**
	 * 公司名称
	 */
	@Column(name = "COMPANY_NAME", length = 512)
	private String companyName;
	
	/**
	 * 状态 0:正常 1:删除
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	/**
	 * 关联成本中心订单表
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="costCenter")
	private List<CostCenterOrder> costCenterOrders;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CostCenterOrder> getCostCenterOrders() {
		return costCenterOrders;
	}

	public void setCostCenterOrders(List<CostCenterOrder> costCenterOrders) {
		this.costCenterOrders = costCenterOrders;
	}
	
		
}

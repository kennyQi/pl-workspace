package hsl.pojo.dto.line.order;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;

@SuppressWarnings("serial")
public class SalePolicySnapshotDTO extends BaseDTO{

	/**
	 * 价格政策名称
	 */
	private String salePolicyName;

	/**
	 * 价格政策类型
	 */
	private Integer priceType;
	
	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 所有快照信息，SalePolicy对象的JSON
	 */
	private String allInfoSalePolicyJSON;

	public String getSalePolicyName() {
		return salePolicyName;
	}

	public void setSalePolicyName(String salePolicyName) {
		this.salePolicyName = salePolicyName;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAllInfoSalePolicyJSON() {
		return allInfoSalePolicyJSON;
	}

	public void setAllInfoSalePolicyJSON(String allInfoSalePolicyJSON) {
		this.allInfoSalePolicyJSON = allInfoSalePolicyJSON;
	}
	
	

}

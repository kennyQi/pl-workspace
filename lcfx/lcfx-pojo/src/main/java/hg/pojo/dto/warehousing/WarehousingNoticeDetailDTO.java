package hg.pojo.dto.warehousing;

import hg.pojo.dto.product.SkuProductDTO;

public class WarehousingNoticeDetailDTO extends SkuProductDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 含税总价
	 */
	private Double totalPriceInclTax;

	/**
	 * 不含税总价
	 */
	private Double totalPriceExclTax;

	/**
	 * 含税单价
	 */
	private Double unitPriceInclTax;

	/**
	 * 不含税单价
	 */
	private Double unitPriceExclTax;

	/**
	 * 物流费
	 */
	private Double logisticCost;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 税率
	 */
	private Double taxRate;

	/**
	 * 完成数
	 */
	private Integer finishedQuantity;

	public Integer getFinishedQuantity() {
		return finishedQuantity;
	}

	public void setFinishedQuantity(Integer finishedQuantity) {
		this.finishedQuantity = finishedQuantity;
	}

	public Double getTotalPriceInclTax() {
		return totalPriceInclTax;
	}

	public void setTotalPriceInclTax(Double totalPriceInclTax) {
		this.totalPriceInclTax = totalPriceInclTax;
	}

	public Double getTotalPriceExclTax() {
		return totalPriceExclTax;
	}

	public void setTotalPriceExclTax(Double totalPriceExclTax) {
		this.totalPriceExclTax = totalPriceExclTax;
	}

	public Double getUnitPriceInclTax() {
		return unitPriceInclTax;
	}

	public void setUnitPriceInclTax(Double unitPriceInclTax) {
		this.unitPriceInclTax = unitPriceInclTax;
	}

	public Double getUnitPriceExclTax() {
		return unitPriceExclTax;
	}

	public void setUnitPriceExclTax(Double unitPriceExclTax) {
		this.unitPriceExclTax = unitPriceExclTax;
	}

	public Double getLogisticCost() {
		return logisticCost;
	}

	public void setLogisticCost(Double logisticCost) {
		this.logisticCost = logisticCost;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

}

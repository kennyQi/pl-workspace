package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SupplierQO extends JxcBaseQo {

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 供应商类别
	 */
	private Integer supplierType;

	/**
	 * 是否按照供应商名称模糊查询
	 */
	private Boolean supplierNameLike;

	/**
	 * 供应商状态
	 */
	private Integer supplierStatus;
	private String supplierCode;

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}

	public Boolean getSupplierNameLike() {
		return supplierNameLike;
	}

	public void setSupplierNameLike(Boolean supplierNameLike) {
		this.supplierNameLike = supplierNameLike;
	}

	public Integer getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(Integer supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

}

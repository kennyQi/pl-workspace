package hg.pojo.command;

import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ModifyPurchaseOrderCommand extends JxcCommand {
	
	private String id;
	
	/**
	 * 编号
	 */

	private String purchaseOrderCode;

	/**
	 * 创建日期
	 */

	private Date createDate;

	/**
	 * 供应商
	 */

	private String supplierName;

	private String supplierId;

	/**
	 * 仓库
	 */

	private String warehouseName;

	private String warehouseId;

	/**
	 * 入库日期
	 */

	private Date storageDate;

	/**
	 * 结款方式
	 */

	private String paymentMethodName;

	private String paymentMethodId;

	/**
	 * 项目
	 */

	private String projectName;

	private String projectId;

	/**
	 * 订单创建人
	 */

	private String orderFounder;

	/**
	 * 注意事项
	 */

	private String attentionId;

	private String attentionDetail;

	/**
	 * 备注
	 */

	private String remark;

	/**
	 * 合计数量
	 */

	private Integer totalQuantity;

	/**
	 * 总价格
	 */

	private Double totalPriceTax;
	/**
	 * 不含税总价格
	 */

	private Double totalPriceNoTax;

	private Integer status;

	private List<PurchaseOrderDetailDTO> detailList;

	public List<PurchaseOrderDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseOrderDetailDTO> detailList) {
		this.detailList = detailList;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Date getStorageDate() {
		return storageDate;
	}

	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOrderFounder() {
		return orderFounder;
	}

	public void setOrderFounder(String orderFounder) {
		this.orderFounder = orderFounder;
	}

	public String getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public String getAttentionDetail() {
		return attentionDetail;
	}

	public void setAttentionDetail(String attentionDetail) {
		this.attentionDetail = attentionDetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getTotalPriceTax() {
		return totalPriceTax;
	}

	public void setTotalPriceTax(Double totalPriceTax) {
		this.totalPriceTax = totalPriceTax;
	}

	public Double getTotalPriceNoTax() {
		return totalPriceNoTax;
	}

	public void setTotalPriceNoTax(Double totalPriceNoTax) {
		this.totalPriceNoTax = totalPriceNoTax;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

package hg.pojo.command;


import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;

import java.util.Date;
import java.util.List;


public class CreatePurchaseOrderCommand extends JxcCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 项目
	 */
	private String projectId;

	/**
	 * 供应商
	 */
	private String supplierId;

	/**
	 * 仓库
	 */
	private String warehouseId;

	/**
	 * 结款方式
	 */
	private String paymentMethodId;

	/**
	 * 注意事项
	 */
	private String attentionId;

	/**
	 * 预计入库日期
	 */
	private Date warehousingDate;

	private List<PurchaseOrderDetailDTO> detailList;
	/**
	 * 制单日期
	 */
	private Date createDate;

	/**
	 * 制单人
	 */
	private String createUser;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 含税总价
	 */
	private Double totalPriceInclTax;

	/**
	 * 不含税总价
	 */
	private Double totalPriceExclTax;

	/**
	 * 物流费
	 */
	private Double totalLogisticCost;

	/**
	 * 总数量
	 */
	private Integer totalQuantity;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}


	public Date getWarehousingDate() {
		return warehousingDate;
	}

	public void setWarehousingDate(Date warehousingDate) {
		this.warehousingDate = warehousingDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Double getTotalLogisticCost() {
		return totalLogisticCost;
	}

	public void setTotalLogisticCost(Double totalLogisticCost) {
		this.totalLogisticCost = totalLogisticCost;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public List<PurchaseOrderDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseOrderDetailDTO> detailList) {
		this.detailList = detailList;
	}

}

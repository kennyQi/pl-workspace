package hg.pojo.qo;

import java.util.Date;

@SuppressWarnings("serial")
public class PurchaseOrderQo extends JxcBaseQo {

	private String createDateStartStr;
	private String createDateEndStr;
	private Date createDateStart;
	private Date createDateEnd;
	private Integer purchaseOrderStatus;
	private String projectId;
	private String supplierId;
	private String warehouseId;
	private String purchaseOrderCode;

	public Integer getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

	public void setPurchaseOrderStatus(Integer purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

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

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getCreateDateStartStr() {
		return createDateStartStr;
	}

	public void setCreateDateStartStr(String createDateStartStr) {
		this.createDateStartStr = createDateStartStr;
	}

	public String getCreateDateEndStr() {
		return createDateEndStr;
	}

	public void setCreateDateEndStr(String createDateEndStr) {
		this.createDateEndStr = createDateEndStr;
	}

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

}

package hg.pojo.qo;

import hg.common.component.BaseQo;
import java.util.Date;

/**
 * @类功能说明：入库单
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-18 13:58:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-18 13:58:16
 */
public class WarehousingOrderQo extends JxcBaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 仓库
	 */
	private String warehouseId;
	/**
	 * 入库单类型
	 */
	private Integer orderType;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * 项目
	 */
	private String projectId;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 关闭
	 */
	private Boolean statusClosed;
	/**
	 * 采购单编号
	 */
	private String purchaseOrderCode;
	/**
	 * 下单日期
	 */
	private Date orderDateBegin;
	private Date orderDateEnd;
	/**
	 * 是否质检
	 */
	private Boolean statusQualityChecking;

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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

	public Boolean getStatusClosed() {
		return statusClosed;
	}

	public void setStatusClosed(Boolean statusClosed) {
		this.statusClosed = statusClosed;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Date getOrderDateBegin() {
		return orderDateBegin;
	}

	public void setOrderDateBegin(Date orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}

	public Date getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	public Boolean getStatusQualityChecking() {
		return statusQualityChecking;
	}

	public void setStatusQualityChecking(Boolean statusQualityChecking) {
		this.statusQualityChecking = statusQualityChecking;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

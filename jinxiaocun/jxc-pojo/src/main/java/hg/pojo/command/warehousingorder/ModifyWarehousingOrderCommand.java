package hg.pojo.command.warehousingorder;

import hg.common.component.BaseCommand;
import hg.pojo.dto.warehousing.WarehousingOrderDetailDTO;

import java.util.Date;
import java.util.List;

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
public class ModifyWarehousingOrderCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 仓库
	 */
	private String warehouseId;
	/**
	 * 入库单类型
	 */
	private Integer orderType;

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public String getWarehousingUser() {
		return warehousingUser;
	}

	public void setWarehousingUser(String warehousingUser) {
		this.warehousingUser = warehousingUser;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Boolean getStatusQualityChecking() {
		return statusQualityChecking;
	}

	public void setStatusQualityChecking(Boolean statusQualityChecking) {
		this.statusQualityChecking = statusQualityChecking;
	}

	public String getWarehousingOrderCode() {
		return warehousingOrderCode;
	}

	public void setWarehousingOrderCode(String warehousingOrderCode) {
		this.warehousingOrderCode = warehousingOrderCode;
	}

	public Date getWarehouseingDate() {
		return warehouseingDate;
	}

	public void setWarehouseingDate(Date warehouseingDate) {
		this.warehouseingDate = warehouseingDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 入库人
	 */
	private String warehousingUser;
	/**
	 * 采购单编号
	 */
	private String purchaseOrderCode;
	/**
	 * 下单日期
	 */
	private Date orderDate;
	/**
	 * 是否质检
	 */
	private Boolean statusQualityChecking;
	/**
	 * 入库单编号
	 */
	private String warehousingOrderCode;
	/**
	 * 入库日期
	 */
	private Date warehouseingDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<WarehousingOrderDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<WarehousingOrderDetailDTO> detailList) {
		this.detailList = detailList;
	}

	private List<WarehousingOrderDetailDTO> detailList;

}

package jxc.domain.model.warehouseing.order;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.warehousingorder.CreateWarehousingOrderCommand;
import hg.pojo.command.warehousingorder.ModifyWarehousingOrderCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.Project;
import jxc.domain.model.warehouse.Warehouse;

/**
 * 结算方式
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX_WAREHOUSING + "WAREHOUSING_ORDER")
public class WarehousingOrder extends JxcBaseModel {
	/**
	 * 入库单编号
	 */
	@Column(name = "WAREHOUSING_ORDER_CODE", length = 32)
	private String warehousingOrderCode;

	/**
	 * 采购单编号
	 */
	@Column(name = "PURCHASE_ORDER_CODE", length = 32)
	private String purchaseOrderCode;

	/**
	 * 仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Warehouse warehouse;

	/**
	 * 供应商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	/**
	 * 项目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	/**
	 * 是否质检
	 */
	@Type(type = "yes_no")
	@Column(name = "STATUS_QUALITY_CHECKING")
	private Boolean statusQualityChecking;

	/**
	 * 入库单关闭
	 */
	@Type(type = "yes_no")
	@Column(name = "STATUS_CLOSED")
	private Boolean statusClosed;

	/**
	 * 入库人
	 */
	@Column(name = "WAREHOUSING_USER", length = 16)
	private String warehousingUser;

	/**
	 * 入库日期
	 */
	@Column(name = "WAREHOUSEING_DATE", columnDefinition = M.DATE_COLUM)
	private Date warehouseingDate;

	/**
	 * 下单日期
	 */
	@Column(name = "ORDER_DATE", columnDefinition = M.DATE_COLUM)
	private Date orderDate;

	/**
	 * 入库单类别
	 */
	@Column(name = "ORDER_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer orderType;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getWarehousingOrderCode() {
		return warehousingOrderCode;
	}

	public void setWarehousingOrderCode(String warehousingOrderCode) {
		this.warehousingOrderCode = warehousingOrderCode;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Boolean getStatusQualityChecking() {
		return statusQualityChecking;
	}

	public void setStatusQualityChecking(Boolean statusQualityChecking) {
		this.statusQualityChecking = statusQualityChecking;
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

	public Date getWarehouseingDate() {
		return warehouseingDate;
	}

	public void setWarehouseingDate(Date warehouseingDate) {
		this.warehouseingDate = warehouseingDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void create(CreateWarehousingOrderCommand command) {
		setId(UUIDGenerator.getUUID());
//		setWarehouse(command.getWarehouse());
		setOrderType(command.getOrderType());
	//	setProject(command.getProject());
		//setSupplier(command.getSupplier());
		setStatusClosed(command.getStatusClosed());
		setWarehousingUser(command.getWarehousingUser());
		setPurchaseOrderCode(command.getPurchaseOrderCode());
		setOrderDate(command.getOrderDate());
		setStatusQualityChecking(command.getStatusQualityChecking());
		setWarehousingOrderCode(command.getWarehousingOrderCode());
		setWarehouseingDate(command.getWarehouseingDate());
	}

	public void modify(ModifyWarehousingOrderCommand command) {
//		setWarehouse(command.getWarehouse());
		setOrderType(command.getOrderType());
	//	setProject(command.getProject());
		setStatusClosed(command.getStatusClosed());
		setWarehousingUser(command.getWarehousingUser());
		setPurchaseOrderCode(command.getPurchaseOrderCode());
		setOrderDate(command.getOrderDate());
		setStatusQualityChecking(command.getStatusQualityChecking());
		setWarehousingOrderCode(command.getWarehousingOrderCode());
		setWarehouseingDate(command.getWarehouseingDate());
	}

}

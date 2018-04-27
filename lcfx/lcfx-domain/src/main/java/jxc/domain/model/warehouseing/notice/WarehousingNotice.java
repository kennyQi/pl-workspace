package jxc.domain.model.warehouseing.notice;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.warehousingnotice.CreateWarehousingNoticeCommand;
import hg.pojo.command.warehousingnotice.ModifyWarehousingNoticeCommand;

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


@Entity
@Table(name = M.TABLE_PREFIX_WAREHOUSING + "WAREHOUSING_NOTICE")
public class WarehousingNotice extends JxcBaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 入库单号
	 */
	@Column(name = "WAREHOUSING_NOTICE_CODE", length = 32)
	private String warehousingNoticeCode;

	/**
	 * 业务单号
	 */
	@Column(name = "PURCHASE_ORDER_CODE", length = 32)
	private String purchaseOrderCode;

	/**
	 * 通知时间
	 */
	@Column(name = "NOTICE_DATE", columnDefinition = M.DATE_COLUM)
	private Date noticeDate;

	/**
	 * 通知人
	 */
	@Column(name = "NOTICE_USER", length = 16)
	private String noticeUser;

	/**
	 * 入库单类型
	 */
	@Column(name = "NOTICE_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer noticeType;

	/**
	 * 是否生成入库单
	 */
	@Type(type = "yes_no")
	@Column(name = "GENERATE_WAREHOUSING_ORDER")
	private Boolean generateWarehousingOrder;

	/**
	 * 供应商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	/**
	 * 项目名称
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	/**
	 * 入库仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Warehouse warehouse;

	/**
	 * 总数量
	 */
	@Column(name = "TOTAL_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer totalQuantity;

	/**
	 * 不含税总金额
	 */
	@Column(name = "TOTAL_PRICE_EXCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceExclTax;

	/**
	 * 总物流费
	 */
	@Column(name = "TOTAL_LOGISTIC_COST", columnDefinition = M.DOUBLE_COLUM)
	private Double totalLogisticCost;

	public String getWarehousingNoticeCode() {
		return warehousingNoticeCode;
	}

	public void setWarehousingNoticeCode(String warehousingNoticeCode) {
		this.warehousingNoticeCode = warehousingNoticeCode;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getNoticeUser() {
		return noticeUser;
	}

	public void setNoticeUser(String noticeUser) {
		this.noticeUser = noticeUser;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Boolean getGenerateWarehousingOrder() {
		return generateWarehousingOrder;
	}

	public void setGenerateWarehousingOrder(Boolean generateWarehousingOrder) {
		this.generateWarehousingOrder = generateWarehousingOrder;
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

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
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

	
	
	public void create(CreateWarehousingNoticeCommand command) {
		setId(UUIDGenerator.getUUID());
		setGenerateWarehousingOrder(command.getGenerateWarehousingOrder());
//		warehouse = new Warehouse();
//		warehouse.setId(command.getWarehouseId());
		setWarehousingNoticeCode(command.getWarehousingNoticeCode());
		setTotalPriceExclTax(command.getTotalPriceExclTax());
//		setProject(command.getProject());
		
		
		setStatusRemoved(false);
		
		
//		project = 
		setTotalLogisticCost(command.getTotalLogisticCost());
		setNoticeType(command.getNoticeType());
		setNoticeUser(command.getNoticeUser());
//		setSupplier(command.getSupplier());
		setNoticeDate(command.getNoticeDate());
		setPurchaseOrderCode(command.getPurchaseOrderCode());
		setTotalQuantity(command.getTotalQuantity());
	}

	public void modify(ModifyWarehousingNoticeCommand command) {
		setGenerateWarehousingOrder(command.getGenerateWarehousingOrder());
//		setWarehouse(command.getWarehouse());
		setWarehousingNoticeCode(command.getWarehousingNoticeCode());
		setTotalPriceExclTax(command.getTotalPriceExclTax());
//		setProject(command.getProject());
		setTotalLogisticCost(command.getTotalLogisticCost());
		setNoticeType(command.getNoticeType());
		setNoticeUser(command.getNoticeUser());
//		setSupplier(command.getSupplier());
		setNoticeDate(command.getNoticeDate());
		setPurchaseOrderCode(command.getPurchaseOrderCode());
		setTotalQuantity(command.getTotalQuantity());
	}
}

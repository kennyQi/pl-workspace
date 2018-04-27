package jxc.domain.model.purchaseorder;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import jxc.domain.model.Constants;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.Attention;
import jxc.domain.model.system.PaymentMethod;
import jxc.domain.model.system.Project;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.util.CodeUtil;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.CheckPurchaseOrderCommand;
import hg.pojo.command.CreatePurchaseOrderCommand;
import hg.pojo.command.ModifyPurchaseOrderCommand;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_PURCHASE_ORDER + "PURCHASE_ORDER")
public class PurchaseOrder extends JxcBaseModel {

	/**
	 * 采购单号
	 */
	@Column(name = "PURCHASE_ORDER_CODE", length = 32)
	private String purchaseOrderCode;

	/**
	 * 项目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	/**
	 * 供应商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	/**
	 * 仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Warehouse warehouse;

	/**
	 * 结款方式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENT_METHOD_ID")
	private PaymentMethod paymentMethod;

	/**
	 * 注意事项
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTENTION_ID")
	private Attention attention;

	/**
	 * 预计入库日期
	 */
	@Column(name = "WAREHOUSING_DATE", columnDefinition = M.DATE_COLUM)
	private Date warehousingDate;

	/**
	 * 制单日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 制单人
	 */
	@Column(name = "CREATE_USER", length = 16)
	private String createUser;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 255)
	private String remark;

	/**
	 * 含税总价
	 */
	@Column(name = "TOTAL_PRICE_INCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceInclTax;

	/**
	 * 不含税总价
	 */
	@Column(name = "TOTAL_PRICE_EXCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceExclTax;

	/**
	 * 物流费
	 */
	@Column(name = "TOTAL_LOGISTIC_COST", columnDefinition = M.DOUBLE_COLUM)
	private Double totalLogisticCost;

	/**
	 * 总数量
	 */
	@Column(name = "TOTAL_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer totalQuantity;

	/**
	 * 状态
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	public void createPurchaseOrder(CreatePurchaseOrderCommand command) {

		setId(UUIDGenerator.getUUID());
		purchaseOrderCode = CodeUtil.createPurchaseOrderCode();
		setStatusRemoved(false);
		Date date = new Date();
		setCreateDate(date);

		supplier = new Supplier();
		supplier.setId(command.getSupplierId());

		if (StringUtils.isNotBlank(command.getAttentionId())) {
			attention = new Attention();
			attention.setId(command.getAttentionId());
		}

		if (StringUtils.isNotBlank(command.getPaymentMethodId())) {
			paymentMethod = new PaymentMethod();
			paymentMethod.setId(command.getPaymentMethodId());
		}

		warehouse = new Warehouse();
		warehouse.setId(command.getWarehouseId());

		if (StringUtils.isNotBlank(command.getProjectId())) {
			project = new Project();
			project.setId(command.getProjectId());
		}

		setRemark(command.getRemark());

		setWarehousingDate(date);
		setCreateUser(command.getOperatorName());
		setTotalPriceInclTax(command.getTotalPriceInclTax());
		setTotalPriceExclTax(command.getTotalPriceExclTax());
		setTotalLogisticCost(command.getTotalLogisticCost());
		setTotalQuantity(command.getTotalQuantity());
		setStatus(Constants.PURCHASE_ORDER_NO_CHECK);

		setStatusRemoved(false);

	}

	public void modify(ModifyPurchaseOrderCommand command) {

		supplier = new Supplier();
		supplier.setId(command.getSupplierId());

		attention = new Attention();
		attention.setId(command.getAttentionId());

		paymentMethod = new PaymentMethod();
		paymentMethod.setId(command.getPaymentMethodId());

		warehouse = new Warehouse();
		warehouse.setId(command.getWarehouseId());

		project = new Project();
		project.setId(command.getProjectId());

		setRemark(command.getRemark());
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Attention getAttention() {
		return attention;
	}

	public void setAttention(Attention attention) {
		this.attention = attention;
	}

	public void check(CheckPurchaseOrderCommand command) {
		setStatus(command.getStatus());

	}

}
